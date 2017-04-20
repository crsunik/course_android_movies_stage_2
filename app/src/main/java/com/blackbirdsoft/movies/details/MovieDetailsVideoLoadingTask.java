package com.blackbirdsoft.movies.details;

import android.os.AsyncTask;

import com.blackbirdsoft.movies.utils.LoadingTaskListener;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.domain.tmdb.TMDbMoviesApi;

class MovieDetailsVideoLoadingTask extends AsyncTask<Movie, Void, MovieDetailsVideoList> {
    private LoadingTaskListener<MovieDetailsVideoList> mMovieDetailsVideoListener;

    public MovieDetailsVideoLoadingTask(LoadingTaskListener<MovieDetailsVideoList> movieDetailsVideoListener) {
        mMovieDetailsVideoListener = movieDetailsVideoListener;
    }

    protected void onPreExecute() {
        if (mMovieDetailsVideoListener != null)
            mMovieDetailsVideoListener.onPreExecute();
    }

    @Override
    protected void onPostExecute(MovieDetailsVideoList videoList) {
        if (mMovieDetailsVideoListener != null)
            mMovieDetailsVideoListener.onPostExecute(videoList);
    }

    @Override
    protected MovieDetailsVideoList doInBackground(Movie... params) {
        return new TMDbMoviesApi().videos(params[0]);
    }
}
