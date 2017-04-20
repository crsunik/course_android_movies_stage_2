package com.blackbirdsoft.movies.details;

import android.os.AsyncTask;

import com.blackbirdsoft.movies.utils.LoadingTaskListener;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.domain.tmdb.TMDbMoviesApi;

class MovieDetailsReviewLoadingTask extends AsyncTask<Movie, Void, MovieDetailsReviewList> {
    private LoadingTaskListener<MovieDetailsReviewList> mMovieDetailsReviewListener;

    public MovieDetailsReviewLoadingTask(LoadingTaskListener<MovieDetailsReviewList> movieDetailsReviewListener) {
        mMovieDetailsReviewListener = movieDetailsReviewListener;
    }

    protected void onPreExecute() {
        if (mMovieDetailsReviewListener != null)
            mMovieDetailsReviewListener.onPreExecute();
    }

    @Override
    protected void onPostExecute(MovieDetailsReviewList videoList) {
        if (mMovieDetailsReviewListener != null)
            mMovieDetailsReviewListener.onPostExecute(videoList);
    }

    @Override
    protected MovieDetailsReviewList doInBackground(Movie... params) {
        return new TMDbMoviesApi().reviews(params[0]);
    }
}
