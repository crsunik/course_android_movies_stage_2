package com.blackbirdsoft.movies.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blackbirdsoft.movies.App;
import com.blackbirdsoft.movies.BuildConfig;
import com.blackbirdsoft.movies.utils.ListItemClickListener;
import com.blackbirdsoft.movies.utils.LoadingListener;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsVideoFragment extends Fragment implements ListItemClickListener<MovieDetailsVideo>, LoadingListener<Movie, MovieDetailsVideoList> {
    private static final String TAG = MovieDetailsVideoFragment.class.getSimpleName();
    private static final String VIDEOS_LIST = BuildConfig.APPLICATION_ID + ".videosList";

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Movie mMovie;
    private MovieDetailsVideoList mVideoList;
    private MovieDetailsVideoManager mVideoManager;
    private MovieDetailsVideoAdapter mAdapter;

    public MovieDetailsVideoFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsVideoFragment newInstance(Movie movie) {
        MovieDetailsVideoFragment fragment = new MovieDetailsVideoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MovieDetailsActivity.MOVIE_DETAILS, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details_videos, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new MovieDetailsVideoAdapter(this);

        mVideoManager = new MovieDetailsVideoManager();
        mVideoManager.addMovieLoadingListener(mAdapter);

        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(mAdapter);

        recoverList(savedInstanceState);
        return view;
    }

    private void recoverList(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(MovieDetailsActivity.MOVIE_DETAILS)) {
            mMovie = getArguments().getParcelable(MovieDetailsActivity.MOVIE_DETAILS);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(VIDEOS_LIST)) {
            mVideoList = savedInstanceState.getParcelable(VIDEOS_LIST);
        }
        mVideoManager.load(mMovie, mVideoList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mVideoList != null) {
            outState.putParcelable(VIDEOS_LIST, mVideoList);
        }
    }

    @Override
    public void onListItemClick(MovieDetailsVideo listItem) {
        Uri video = Uri.parse("http://www.youtube.com/watch").buildUpon().appendQueryParameter("v", listItem.getKey()).build();
        Intent intent = new Intent(Intent.ACTION_VIEW, video);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            Log.d(TAG, "openVideo: video can be played");
            startActivity(intent);
        } else {
            Log.d(TAG, "openVideo: could not resolve activity to play video");
        }
    }

    @Override
    public void onMovieLoadingBegin(Movie input) {
        showLoading();
    }

    @Override
    public void onMovieLoadingEnd(Movie input, MovieDetailsVideoList result) {
        mVideoList = result;
        if (result == null || result.getResults() == null || result.getResults().size() == 0) {
            showError(App.isInternetAvailable() ? R.string.error_no_results : R.string.error_no_connection);
        } else {
            showVideos();
        }
    }

    void showError(int stringResource) {
        rvList.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
        tvError.setText(stringResource);
        tvError.setVisibility(View.VISIBLE);
    }

    void showLoading() {
        rvList.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.INVISIBLE);
    }

    void showVideos() {
        rvList.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.INVISIBLE);
    }
}