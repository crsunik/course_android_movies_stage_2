package com.blackbirdsoft.movies.details;

import com.blackbirdsoft.movies.utils.LoadingListener;
import com.blackbirdsoft.movies.utils.LoadingTaskListener;
import com.blackbirdsoft.movies.domain.Movie;

import java.util.ArrayList;
import java.util.List;

class MovieDetailsVideoManager implements LoadingTaskListener<MovieDetailsVideoList> {

    private List<LoadingListener<Movie, MovieDetailsVideoList>> mListeners;
    private Movie mMovie;

    MovieDetailsVideoManager() {
        this.mListeners = new ArrayList<>();
    }

    void addMovieLoadingListener(LoadingListener<Movie, MovieDetailsVideoList> loadingListener) {
        mListeners.add(loadingListener);
    }

    void load(Movie movie, MovieDetailsVideoList videoList) {
        mMovie = movie;
        if (videoList == null)
            new MovieDetailsVideoLoadingTask(this).execute(movie);
        else
            onPostExecute(videoList);
    }

    @Override
    public void onPreExecute() {
        for (LoadingListener<Movie, MovieDetailsVideoList> listener : mListeners) {
            listener.onMovieLoadingBegin(mMovie);
        }
    }

    @Override
    public void onPostExecute(MovieDetailsVideoList moviesList) {
        for (LoadingListener<Movie, MovieDetailsVideoList> listener : mListeners) {
            listener.onMovieLoadingEnd(mMovie, moviesList);
        }
    }
}