package com.blackbirdsoft.movies.details;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blackbirdsoft.movies.utils.ListItemClickListener;
import com.blackbirdsoft.movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieDetailsReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.cv_review)
    CardView cvReview;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_review)
    TextView tvReview;

    private ListItemClickListener<MovieDetailsReview> mOnClickListener;
    private MovieDetailsReview mReview;

    MovieDetailsReviewViewHolder(View itemView, ListItemClickListener<MovieDetailsReview> onClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mOnClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mOnClickListener.onListItemClick(mReview);
    }

    public void bind(MovieDetailsReview review) {
        this.mReview = review;
        tvAuthor.setText(review.getAuthor());
        tvReview.setText(review.getContent());
    }
}
