package com.blackbirdsoft.movies.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackbirdsoft.movies.utils.ListItemClickListener;
import com.blackbirdsoft.movies.utils.LoadingListener;
import com.blackbirdsoft.movies.R;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.domain.MoviesList;

class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> implements LoadingListener<MoviesListType, MoviesList> {

    private ListItemClickListener<Movie> mOnClickListener;
    private MoviesList mMoviesList;

    MovieAdapter(ListItemClickListener<Movie> onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onMovieLoadingBegin(MoviesListType type) {
        this.mMoviesList = null;
        notifyDataSetChanged();
    }

    @Override
    public void onMovieLoadingEnd(MoviesListType type, MoviesList moviesList) {
        this.mMoviesList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_movie, parent, false);
        return new MovieViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mMoviesList.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        return mMoviesList == null || mMoviesList.getResults() == null ? 0 : mMoviesList.getResults().size();
    }
}
