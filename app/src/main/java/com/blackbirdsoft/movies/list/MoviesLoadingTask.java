package com.blackbirdsoft.movies.list;

import android.os.AsyncTask;

import com.blackbirdsoft.movies.domain.db.FavouriteMoviesManager;
import com.blackbirdsoft.movies.domain.tmdb.TMDbMoviesApi;
import com.blackbirdsoft.movies.utils.LoadingTaskListener;
import com.blackbirdsoft.movies.domain.MoviesList;

class MoviesLoadingTask extends AsyncTask<MoviesListType, Void, MoviesList> {

    private static final String TAG = MoviesLoadingTask.class.getName();

    private final LoadingTaskListener<MoviesList> mLoadingTaskListener;

    MoviesLoadingTask(LoadingTaskListener<MoviesList> loadingTaskListener) {
        this.mLoadingTaskListener = loadingTaskListener;
    }

    @Override
    protected void onPreExecute() {
        if (mLoadingTaskListener != null)
            mLoadingTaskListener.onPreExecute();
    }

    @Override
    protected void onPostExecute(MoviesList moviesList) {
        if (mLoadingTaskListener != null)
            mLoadingTaskListener.onPostExecute(moviesList);
    }

    @Override
    protected MoviesList doInBackground(MoviesListType... params) {
        switch (params[0]) {
            case FAVOURITES:
                return new FavouriteMoviesManager().queryFavourites();
            case TOP_RATED:
                return new TMDbMoviesApi().topRated();
            case MOST_POPULAR:
            default:
                return new TMDbMoviesApi().popular();
        }
    }
}
