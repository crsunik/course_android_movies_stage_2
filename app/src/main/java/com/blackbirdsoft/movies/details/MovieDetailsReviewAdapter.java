package com.blackbirdsoft.movies.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackbirdsoft.movies.utils.ListItemClickListener;
import com.blackbirdsoft.movies.utils.LoadingListener;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.R;

class MovieDetailsReviewAdapter extends RecyclerView.Adapter<MovieDetailsReviewViewHolder> implements LoadingListener<Movie, MovieDetailsReviewList> {

    private ListItemClickListener<MovieDetailsReview> mOnClickListener;
    private MovieDetailsReviewList mMovieDetailsReviewList;

    MovieDetailsReviewAdapter(ListItemClickListener<MovieDetailsReview> onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onLoadingBegin(Movie movie) {
    }

    @Override
    public void onLoadingEnd(Movie movie, MovieDetailsReviewList reviewList) {
        if (mMovieDetailsReviewList != null && mMovieDetailsReviewList.equals(reviewList))
            return;
        this.mMovieDetailsReviewList = reviewList;
        notifyDataSetChanged();
    }

    @Override
    public MovieDetailsReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_movie_review, parent, false);
        return new MovieDetailsReviewViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(MovieDetailsReviewViewHolder holder, int position) {
        holder.bind(mMovieDetailsReviewList.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieDetailsReviewList == null || mMovieDetailsReviewList.getResults() == null ? 0 : mMovieDetailsReviewList.getResults().size();
    }
}
