package com.blackbirdsoft.movies.domain.tmdb;

import com.blackbirdsoft.movies.domain.MovieDetails;
import com.blackbirdsoft.movies.details.MovieDetailsReviewList;
import com.blackbirdsoft.movies.details.MovieDetailsVideoList;
import com.blackbirdsoft.movies.domain.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface TMDbMoviesService {
    @Headers("Accept: application/json")
    @GET(TMDbMoviesApi.MOST_POPULAR_URL)
    Call<MoviesList> popular(@Query("api_key") String apiKey, @Query("page") int page, @Query("language") String language);

    @Headers("Accept: application/json")
    @GET(TMDbMoviesApi.TOP_RATED_URL)
    Call<MoviesList> topRated(@Query("api_key") String apiKey, @Query("page") int page, @Query("language") String language);

    @Headers("Accept: application/json")
    @GET(TMDbMoviesApi.MOVIE_DETAILS_URL)
    Call<MovieDetails> details(@Path("movie_id") Integer movieId, @Query("api_key") String apiKey, @Query("language") String language);

    @Headers("Accept: application/json")
    @GET(TMDbMoviesApi.REVIEWS_URL)
    Call<MovieDetailsReviewList> reviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String language);

    @Headers("Accept: application/json")
    @GET(TMDbMoviesApi.VIDEOS_URL)
    Call<MovieDetailsVideoList> videos(@Path("movie_id") int movieId, @Query("api_key") String apiKey, @Query("language") String language);

}