package com.blackbirdsoft.movies.domain.tmdb;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blackbirdsoft.movies.BuildConfig;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.details.MovieDetailsReviewList;
import com.blackbirdsoft.movies.details.MovieDetailsVideoList;
import com.blackbirdsoft.movies.domain.MoviesList;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TMDbMoviesApi {

    private static final String TAG = TMDbMoviesApi.class.getName();
    static final String BASE_URL = "http://api.themoviedb.org";
    static final String MOST_POPULAR_URL = "/3/movie/popular";
    static final String TOP_RATED_URL = "/3/movie/top_rated";
    static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p";
    static final String POSTER_WIDTH_185 = "w185";
    static final String POSTER_WIDTH_780 = "w780";
    static final String MOVIE_DETAILS_URL = "/3/movie/{movie_id}";
    static final String REVIEWS_URL = MOVIE_DETAILS_URL + "/reviews";
    static final String VIDEOS_URL = MOVIE_DETAILS_URL + "/videos";

    private final TMDbMoviesService mService;

    public TMDbMoviesApi() {
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        this.mService = mRetrofit.create(TMDbMoviesService.class);
    }

    public MoviesList popular() {
        return popular(Locale.getDefault().toString(), 1);
    }

    MoviesList popular(String language, int page) {
        Response<MoviesList> response = null;
        try {
            response = mService.popular(BuildConfig.TMDB_API_KEY, page, language).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resolve("popular movies", response);
    }

    public MoviesList topRated() {
        return topRated(Locale.getDefault().toString(), 1);
    }

    MoviesList topRated(String language, int page) {
        Response<MoviesList> response = null;
        try {
            response = mService.topRated(BuildConfig.TMDB_API_KEY, page, language).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resolve("topRated movies", response);
    }

    private <T> T resolve(String operation, Response<T> response) {
        if (response == null) {
            Log.d(TAG, operation + " response is null");
            return null;
        }
        if (response.isSuccessful()) {
            return response.body();
        }
        Log.d(TAG, operation + " response is unsuccessful, error code: " + response.code());
        try {
            Log.d(TAG, operation + " response is unsuccessful, error body: " + response.errorBody().string());
        } catch (IOException e) {
            Log.d(TAG, operation + " response is unsuccessful, IOException: ");
            e.printStackTrace();
        }
        return null;
    }

    public static String createPosterUrl(Movie movie) {
        return createImageUrl(POSTER_WIDTH_185, movie.getPosterPath());
    }

    public static String createBackdropUrl(Movie movie) {
        return createImageUrl(POSTER_WIDTH_780, movie.getBackdropPath());
    }

    @Nullable
    private static String createImageUrl(String size, String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return Uri.parse(POSTER_BASE_URL)
                .buildUpon()
                .appendPath(size)
                .appendPath(path)
                .build()
                .toString();
    }

    public MovieDetailsVideoList videos(Movie movie) {
        Log.i(TAG, "retrieving videos for movie : " + movie.getTitle());
        Response<MovieDetailsVideoList> response = null;
        try {
            response = mService.videos(movie.getId(), BuildConfig.TMDB_API_KEY, Locale.getDefault().toString()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resolve("movie videos", response);
    }

    public MovieDetailsReviewList reviews(Movie movie) {
        Log.i(TAG, "retrieving reviews for movie : " + movie.getTitle());
        Response<MovieDetailsReviewList> response = null;
        try {
            response = mService.reviews(movie.getId(), BuildConfig.TMDB_API_KEY, Locale.getDefault().toString()).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resolve("movie reviews", response);
    }
}
