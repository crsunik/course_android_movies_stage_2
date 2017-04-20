package com.blackbirdsoft.movies.details;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blackbirdsoft.movies.BuildConfig;
import com.blackbirdsoft.movies.domain.db.FavouriteMoviesManager;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    public static final String MOVIE_DETAILS = BuildConfig.APPLICATION_ID + ".movieDetails";

    private static final String TAG = MovieDetailsActivity.class.getName();

    private AsyncTask mFavouriteChecker;
    private Movie mMovie;
    private FavouriteMoviesManager mFavouriteMoviesManager;

    @BindView(R.id.vp_movie_details)
    ViewPager mViewPager;
    @BindView(R.id.tl_movie_details)
    TabLayout mTabLayout;
    @BindView(R.id.fab_favourite)
    FloatingActionButton mFavouriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavouriteMoviesManager = new FavouriteMoviesManager(this);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MOVIE_DETAILS)) {
            mMovie = intent.getParcelableExtra(MOVIE_DETAILS);
            setTitle(mMovie.getOriginalTitle());
            switchFavouriteBtnState(mMovie.getFavourite());
            initTabs();
            mFavouriteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavourite();
                }
            });
        }
    }

    private void switchFavouriteBtnState(Movie.FavouriteState favourite) {
        switch (favourite) {
            case FAVOURITE:
                mFavouriteBtn.setImageResource(R.drawable.ic_heart_outline_white_48dp);
                cancelFavouriteCheck();
                break;
            case UNKNOWN:
                mFavouriteBtn.setImageResource(R.drawable.ic_heart_white_48dp);
                mFavouriteChecker = new AsyncTask<Movie, Void, Movie.FavouriteState>() {
                    @Override
                    protected Movie.FavouriteState doInBackground(Movie... params) {
                        Movie movie = params[0];
                        Movie favourite = mFavouriteMoviesManager.queryFavourite(movie.getId());
                        return favourite == null ? Movie.FavouriteState.NOT_FAVOURITE : Movie.FavouriteState.FAVOURITE;
                    }

                    @Override
                    protected void onPostExecute(Movie.FavouriteState favouriteState) {
                        mMovie.setFavourite(favouriteState);
                        switchFavouriteBtnState(favouriteState);
                    }
                }.execute(mMovie);
                break;
            case NOT_FAVOURITE:
                mFavouriteBtn.setImageResource(R.drawable.ic_heart_white_48dp);
                cancelFavouriteCheck();
                break;
        }
    }

    private void cancelFavouriteCheck() {
        if (mFavouriteChecker != null)
            mFavouriteChecker.cancel(true);
        mFavouriteChecker = null;
    }

    private void toggleFavourite() {
        Log.d(TAG, "toggleFavourite:" + mMovie.getOriginalTitle() + " - " + mMovie.getFavourite());
        switch (mMovie.getFavourite()) {
            case FAVOURITE:
                cancelFavouriteCheck();
                mMovie.setFavourite(Movie.FavouriteState.NOT_FAVOURITE);
                mFavouriteMoviesManager.deleteFavourite(mMovie);
                break;
            case UNKNOWN:
            case NOT_FAVOURITE:
                cancelFavouriteCheck();
                mMovie.setFavourite(Movie.FavouriteState.FAVOURITE);
                mFavouriteMoviesManager.insertOrUpdateFavourite(mMovie);
                break;
        }
        switchFavouriteBtnState(mMovie.getFavourite());
    }

    private void initTabs() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_label_details));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_label_videos));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_label_reviews));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.setAdapter(new SectionsStatePagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout)
        );
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.i(TAG, "onTabSelected: " + tab.getPosition() + " - " + tab.getText());
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_DETAILS, mMovie);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.i(TAG, "onAttachFragment: " + childFragment.getClass().getName());
    }

    private class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {

        private static final int OVERVIEW = 0;
        private static final int VIDEOS = 1;
        private static final int REVIEWS = 2;

        private Map<Integer, Fragment> mFragmentsMap;

        SectionsStatePagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentsMap = new ArrayMap<>();
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragmentsMap.containsKey(position))
                return mFragmentsMap.get(position);
            switch (position) {
                case OVERVIEW:
                    mFragmentsMap.put(OVERVIEW, MovieDetailsOverviewFragment.newInstance(mMovie));
                    break;
                case VIDEOS:
                    mFragmentsMap.put(VIDEOS, MovieDetailsVideoFragment.newInstance(mMovie));
                    break;
                case REVIEWS:
                    mFragmentsMap.put(REVIEWS, MovieDetailsReviewFragment.newInstance(mMovie));
                    break;
                default:
                    return null;
            }
            return mFragmentsMap.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mFragmentsMap.remove(position);
        }
    }
}
