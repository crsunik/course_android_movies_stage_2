package com.blackbirdsoft.movies.details;

import com.blackbirdsoft.movies.utils.LoadingListener;
import com.blackbirdsoft.movies.utils.LoadingTaskListener;
import com.blackbirdsoft.movies.domain.Movie;

import java.util.ArrayList;
import java.util.List;

class MovieDetailsReviewManager implements LoadingTaskListener<MovieDetailsReviewList> {

    private List<LoadingListener<Movie, MovieDetailsReviewList>> mListeners;
    private Movie mMovie;

    MovieDetailsReviewManager() {
        this.mListeners = new ArrayList<>();
    }

    void addMovieLoadingListener(LoadingListener<Movie, MovieDetailsReviewList> loadingListener) {
        mListeners.add(loadingListener);
    }

    void load(Movie movie, MovieDetailsReviewList reviewList) {
        mMovie = movie;
        if (reviewList == null)
            new MovieDetailsReviewLoadingTask(this).execute(movie);
        else
            onPostExecute(reviewList);
    }

    @Override
    public void onPreExecute() {
        for (LoadingListener<Movie, MovieDetailsReviewList> listener : mListeners) {
            listener.onMovieLoadingBegin(mMovie);
        }
    }

    @Override
    public void onPostExecute(MovieDetailsReviewList moviesList) {
        for (LoadingListener<Movie, MovieDetailsReviewList> listener : mListeners) {
            listener.onMovieLoadingEnd(mMovie, moviesList);
        }
    }
}