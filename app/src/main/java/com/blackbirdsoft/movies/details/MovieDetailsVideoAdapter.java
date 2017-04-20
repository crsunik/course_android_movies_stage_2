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

class MovieDetailsVideoAdapter extends RecyclerView.Adapter<MovieDetailsVideoViewHolder> implements LoadingListener<Movie, MovieDetailsVideoList> {

    private ListItemClickListener<MovieDetailsVideo> mOnClickListener;
    private MovieDetailsVideoList mVideoList;

    MovieDetailsVideoAdapter(ListItemClickListener<MovieDetailsVideo> onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onMovieLoadingBegin(Movie type) {
        this.mVideoList = null;
        notifyDataSetChanged();
    }

    @Override
    public void onMovieLoadingEnd(Movie type, MovieDetailsVideoList videoList) {
        this.mVideoList = videoList;
        notifyDataSetChanged();
    }

    @Override
    public MovieDetailsVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_movie_video, parent, false);
        return new MovieDetailsVideoViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(MovieDetailsVideoViewHolder holder, int position) {
        holder.bind(mVideoList.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        return mVideoList == null || mVideoList.getResults() == null ? 0 : mVideoList.getResults().size();
    }
}
