package com.blackbirdsoft.movies.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MovieDetailsReviewFragment extends Fragment implements ListItemClickListener<MovieDetailsReview>, LoadingListener<Movie, MovieDetailsReviewList> {
    private static final String REVIEWS_LIST = BuildConfig.APPLICATION_ID + ".reviewList";
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Movie mMovie;
    private MovieDetailsReviewList mReviewList;
    private MovieDetailsReviewManager mReviewManager;
    private MovieDetailsReviewAdapter mAdapter;

    public MovieDetailsReviewFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsReviewFragment newInstance(Movie movie) {
        MovieDetailsReviewFragment fragment = new MovieDetailsReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(MovieDetailsActivity.MOVIE_DETAILS, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details_reviews, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new MovieDetailsReviewAdapter(this);

        mReviewManager = new MovieDetailsReviewManager();
        mReviewManager.addMovieLoadingListener(mAdapter);

        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(mAdapter);

        recoverList(savedInstanceState);
        return view;
    }

    private void recoverList(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(MovieDetailsActivity.MOVIE_DETAILS)) {
            mMovie = getArguments().getParcelable(MovieDetailsActivity.MOVIE_DETAILS);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(REVIEWS_LIST)) {
            mReviewList = savedInstanceState.getParcelable(REVIEWS_LIST);
        }
        mReviewManager.load(mMovie, mReviewList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mReviewList != null) {
            outState.putParcelable(REVIEWS_LIST, mReviewList);
        }
    }

    @Override
    public void onListItemClick(MovieDetailsReview listItem) {

    }

    @Override
    public void onLoadingBegin(Movie input) {
        showLoading();
    }

    @Override
    public void onLoadingEnd(Movie input, MovieDetailsReviewList result) {
        mReviewList = result;
        if (result == null || result.getResults() == null || result.getResults().size() == 0) {
            showError(App.isInternetAvailable() ? R.string.error_no_results : R.string.error_no_connection);
        } else {
            showReviews();
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

    void showReviews() {
        rvList.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.INVISIBLE);
    }
}