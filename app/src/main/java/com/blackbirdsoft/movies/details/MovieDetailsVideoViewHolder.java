package com.blackbirdsoft.movies.details;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blackbirdsoft.movies.utils.ListItemClickListener;
import com.blackbirdsoft.movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieDetailsVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.cv_video)
    CardView cvVideo;
    @BindView(R.id.tv_video_name)
    TextView tvName;
    @BindView(R.id.tv_video_size)
    TextView tvSize;

    private ListItemClickListener<MovieDetailsVideo> mOnClickListener;
    private MovieDetailsVideo mVideo;

    MovieDetailsVideoViewHolder(View itemView, ListItemClickListener<MovieDetailsVideo> onClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mOnClickListener = onClickListener;
        itemView.setOnClickListener(this);
    }

    void bind(MovieDetailsVideo video) {
        this.mVideo = video;
        tvName.setText(video.getName());
        tvSize.setText(String.valueOf(video.getSize()));
    }

    @Override
    public void onClick(View v) {
        mOnClickListener.onListItemClick(mVideo);
    }
}
