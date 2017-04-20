package com.blackbirdsoft.movies.list;

import com.blackbirdsoft.movies.utils.LoadingListener;
import com.blackbirdsoft.movies.utils.LoadingTaskListener;
import com.blackbirdsoft.movies.domain.MoviesList;

import java.util.ArrayList;
import java.util.List;

class MoviesManager implements LoadingTaskListener<MoviesList> {

    private List<LoadingListener<MoviesListType, MoviesList>> mListeners;
    private MoviesListType mListType;

    MoviesManager() {
        this.mListeners = new ArrayList<>();
    }

    void addMovieLoadingListener(LoadingListener<MoviesListType, MoviesList> loadingListener) {
        mListeners.add(loadingListener);
    }

    void loadMovies(MoviesListType type, MoviesList moviesList) {
        mListType = type;
        if (moviesList == null)
            new MoviesLoadingTask(this).execute(type);
        else
            onPostExecute(moviesList);
    }

    @Override
    public void onPreExecute() {
        for (LoadingListener<MoviesListType, MoviesList> listener : mListeners) {
            listener.onLoadingBegin(mListType);
        }
    }

    @Override
    public void onPostExecute(MoviesList moviesList) {
        for (LoadingListener<MoviesListType, MoviesList> listener : mListeners) {
            listener.onLoadingEnd(mListType, moviesList);
        }
    }
}
