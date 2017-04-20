package com.blackbirdsoft.movies.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.R;
import com.blackbirdsoft.movies.domain.tmdb.TMDbMoviesApi;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailsOverviewFragment extends Fragment {

    @BindView(R.id.tv_details_title)
    TextView mTitle;
    @BindView(R.id.tv_details_overview)
    TextView mOverview;
    @BindView(R.id.tv_details_release_date)
    TextView mReleaseDate;
    @BindView(R.id.tv_details_user_rating)
    TextView mRating;
    @BindView(R.id.iv_details_poster)
    ImageView mPoster;
    @BindView(R.id.iv_details_backdrop)
    ImageView mBackdrop;

    private Movie mMovie;

    public MovieDetailsOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movie Movie to be displayed.
     * @return A new instance of fragment MovieDetailsOverviewFragment.
     */
    public static MovieDetailsOverviewFragment newInstance(Movie movie) {
        MovieDetailsOverviewFragment fragment = new MovieDetailsOverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(MovieDetailsActivity.MOVIE_DETAILS, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details_overview, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null && getArguments().containsKey(MovieDetailsActivity.MOVIE_DETAILS)) {
            mMovie = getArguments().getParcelable(MovieDetailsActivity.MOVIE_DETAILS);
            if (mMovie != null) {
                loadMovie(mMovie);
            }
        }
        return view;
    }

    private void loadMovie(Movie movie) {
        mTitle.setText(movie.getOriginalTitle());
        mOverview.setText(movie.getOverview());
        mReleaseDate.setText(movie.getReleaseDate());
        mRating.setText(String.valueOf(movie.getVoteAverage()));
        //Glide is caching images based on url automatically - no need to change anything
        if (movie.getPosterPath() != null) {
            Glide.with(this)
                    .load(TMDbMoviesApi.createPosterUrl(movie))
                    .centerCrop()
                    .error(R.drawable.ic_link_error)
                    .placeholder(R.drawable.ic_photo)
                    .crossFade()
                    .into(mPoster);
        }
        if (movie.getBackdropPath() != null) {
            Glide.with(this)
                    .load(TMDbMoviesApi.createBackdropUrl(movie))
                    .centerCrop()
                    .error(R.drawable.ic_link_error)
                    .placeholder(R.drawable.ic_photo)
                    .crossFade()
                    .into(mBackdrop);
        }
    }
}
