package com.blackbirdsoft.movies.list;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blackbirdsoft.movies.App;
import com.blackbirdsoft.movies.BuildConfig;
import com.blackbirdsoft.movies.domain.db.MovieContract;
import com.blackbirdsoft.movies.utils.ListItemClickListener;
import com.blackbirdsoft.movies.utils.LoadingListener;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.details.MovieDetailsActivity;
import com.blackbirdsoft.movies.domain.MoviesList;
import com.blackbirdsoft.movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity implements ListItemClickListener<Movie>, LoadingListener<MoviesListType, MoviesList> {

    public static final String MOVIE_LIST = BuildConfig.APPLICATION_ID + ".movieList";

    private static final String TAG = MovieActivity.class.getName();

    @BindView(R.id.tv_error_messsage)
    TextView mErrorMsg;
    @BindView(R.id.pb_movies_loading)
    ProgressBar mLoadingProgressBar;
    @BindView(R.id.rc_movie_list)
    RecyclerView mMoviesRecyclerView;

    private MoviesManager mMoviesManager;
    private LayoutManager mMoviesLayoutManager;
    private RecyclerView.Adapter mMoviesAdapter;
    private MoviesList mMoviesList;
    private ContentObserver mFavouritesMovieObserver;
    private Handler mFavouritesMovieHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        mFavouritesMovieHandler = new Handler();
        mMoviesManager = createProvider();
        mMoviesLayoutManager = createLayoutManager();
        mMoviesAdapter = createAdapter();
        initView();
        restore(savedInstanceState);
    }

    @NonNull
    private MoviesManager createProvider() {
        MoviesManager movieProvider = new MoviesManager();
        movieProvider.addMovieLoadingListener(this);
        return movieProvider;
    }

    @NonNull
    private LayoutManager createLayoutManager() {
        return new GridLayoutManager(MovieActivity.this, calculateNoOfColumns(getBaseContext()));
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    @NonNull
    private RecyclerView.Adapter createAdapter() {
        MovieAdapter adapter = new MovieAdapter(this);
        mMoviesManager.addMovieLoadingListener(adapter);
        return adapter;
    }

    private void initView() {
        updateTitle(MoviesPreferencesUtils.getType(this));
        mMoviesRecyclerView.setHasFixedSize(true);
        mMoviesRecyclerView.setLayoutManager(mMoviesLayoutManager);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    private void updateTitle(MoviesListType type) {
        switch (type) {
            case TOP_RATED:
                setTitle(R.string.top_rated);
                break;
            case MOST_POPULAR:
                setTitle(R.string.most_popular);
                break;
            case FAVOURITES:
                setTitle(R.string.favourites);
                break;
        }
    }

    private void restore(Bundle state) {
        mMoviesManager.loadMovies(MoviesPreferencesUtils.getType(this), state == null ? null : (MoviesList) state.getParcelable(MOVIE_LIST));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (R.id.action_show_most_popular == itemId) {
            loadMovies(MoviesListType.MOST_POPULAR);
            return true;
        } else if (R.id.action_show_top_rated == itemId) {
            loadMovies(MoviesListType.TOP_RATED);
            return true;
        } else if (R.id.action_favorites == itemId) {
            loadMovies(MoviesListType.FAVOURITES);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void loadMovies(MoviesListType listType) {
        MoviesPreferencesUtils.setType(this, listType);
        updateTitle(listType);
        mMoviesManager.loadMovies(MoviesPreferencesUtils.getType(this), null);
        if (listType == MoviesListType.FAVOURITES)
            registerFavouriteContentObserver();
        else
            unregisterFavouriteContentObserver();
    }

    private void registerFavouriteContentObserver() {
        if (mFavouritesMovieObserver != null)
            return;
        mFavouritesMovieObserver = new ContentObserver(mFavouritesMovieHandler) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.i(TAG, "mFavouritesMovieObserver.OnChange");
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.i(TAG, "mFavouritesMovieObserver.OnChange: " + uri.getPath());
                loadMovies(MoviesListType.FAVOURITES);
            }
        };
        getContentResolver().registerContentObserver(MovieContract.FavouriteEntry.CONTENT_URI, true, mFavouritesMovieObserver);
    }

    private void unregisterFavouriteContentObserver() {
        if (mFavouritesMovieObserver == null)
            return;
        getContentResolver().unregisterContentObserver(mFavouritesMovieObserver);
        mFavouritesMovieHandler = null;

    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_DETAILS, movie);
        startActivity(intent);
    }

    @Override
    public void onLoadingBegin(MoviesListType type) {
        showLoading();
    }

    private void showLoading() {
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMsg.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingEnd(MoviesListType type, MoviesList moviesList) {
        if (moviesList == null || moviesList.getResults() == null || moviesList.getResults().size() == 0) {
            Log.d(TAG, "got empty result");
            showError(type == MoviesListType.FAVOURITES || App.isInternetAvailable() ? R.string.error_no_results : R.string.error_no_connection);
        } else {
            Log.d(TAG, "got response with " + moviesList.getResults().size() + " movies");
            showMovies();
        }
        mMoviesList = moviesList;
    }

    private void showError(int msgId) {
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mErrorMsg.setText(msgId);
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    private void showMovies() {
        mErrorMsg.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_LIST, mMoviesList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MoviesPreferencesUtils.getType(this).equals(MoviesListType.FAVOURITES)) {
            Log.d(TAG, "onResume: registering favourite observer");
            //since favourites list could change when observer was unregistered we load favourites movies again
            loadMovies(MoviesListType.FAVOURITES);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFavouritesMovieObserver != null) {
            Log.d(TAG, "onPause: unregistering favourite observer");
            //unregistering favourites list observer - while the list is not visible we don't need to constantly update it
            getContentResolver().unregisterContentObserver(mFavouritesMovieObserver);
        }
    }

}
