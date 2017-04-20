package com.blackbirdsoft.movies.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blackbirdsoft.movies.utils.ListItemClickListener;
import com.blackbirdsoft.movies.R;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.domain.tmdb.TMDbMoviesApi;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_movie_poster)
    ImageView mPosterImageView;
    private ListItemClickListener<Movie> mOnClickListener;
    private Movie mMovie;

    MovieViewHolder(View itemView, ListItemClickListener<Movie> onClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mOnClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    void bind(Movie movie) {
        this.mMovie = movie;
        Glide.with(mPosterImageView.getContext())
                .load(TMDbMoviesApi.createPosterUrl(movie))
                .centerCrop()
                .error(R.drawable.ic_link_error)
                .placeholder(R.drawable.ic_photo)
                .crossFade()
                .into(mPosterImageView);
    }

    @Override
    public void onClick(View v) {
        mOnClickListener.onListItemClick(mMovie);
    }
}
