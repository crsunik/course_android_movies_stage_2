package com.blackbirdsoft.movies.domain.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.blackbirdsoft.movies.domain.Movie;

import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._ADULT;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._BACKDROP_PATH;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._ORIGINAL_LANGUAGE;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._ORIGINAL_TITLE;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._OVERVIEW;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._POPULARITY;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._POSTER_PATH;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._RELEASE_DATE;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._TITLE;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._VIDEO;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._VOTE_AVERAGE;
import static com.blackbirdsoft.movies.domain.db.MovieContract.FavouriteEntry._VOTE_COUNT;

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.blackbirdsoft.movies.contentprovider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVOURITES = "favourites";

    static Movie fromCursor(Cursor cursor) {
        Movie movie = new Movie();
        if (cursor.getColumnIndex(FavouriteEntry._ID) != -1) {
            movie.setId(cursor.getInt(cursor.getColumnIndex(FavouriteEntry._ID)));
        }
        if (cursor.getColumnIndex(_TITLE) != -1) {
            movie.setTitle(cursor.getString(cursor.getColumnIndex(_TITLE)));
        }
        if (cursor.getColumnIndex(_POSTER_PATH) != -1) {
            movie.setPosterPath(cursor.getString(cursor.getColumnIndex(_POSTER_PATH)));
        }
        if (cursor.getColumnIndex(_ADULT) != -1) {
            movie.setAdult(cursor.getInt(cursor.getColumnIndex(_ADULT)) == 1);
        }
        if (cursor.getColumnIndex(_OVERVIEW) != -1) {
            movie.setOverview(cursor.getString(cursor.getColumnIndex(_OVERVIEW)));
        }
        if (cursor.getColumnIndex(_RELEASE_DATE) != -1) {
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(_RELEASE_DATE)));
        }
        if (cursor.getColumnIndex(_ORIGINAL_TITLE) != -1) {
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(_ORIGINAL_TITLE)));
        }
        if (cursor.getColumnIndex(_ORIGINAL_LANGUAGE) != -1) {
            movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(_ORIGINAL_LANGUAGE)));
        }
        if (cursor.getColumnIndex(_BACKDROP_PATH) != -1) {
            movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(_BACKDROP_PATH)));
        }
        if (cursor.getColumnIndex(_POPULARITY) != -1) {
            movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(_POPULARITY)));
        }
        if (cursor.getColumnIndex(_VOTE_COUNT) != -1) {
            movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(_VOTE_COUNT)));
        }
        if (cursor.getColumnIndex(_VOTE_AVERAGE) != -1) {
            movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(_VOTE_AVERAGE)));
        }
        if (cursor.getColumnIndex(_VIDEO) != -1) {
            movie.setVideo(cursor.getInt(cursor.getColumnIndex(_VIDEO)) == 1);
        }
        return movie;
    }

    public static ContentValues toContent(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.FavouriteEntry._ID, movie.getId());
        cv.put(MovieContract.FavouriteEntry._TITLE, movie.getTitle());
        cv.put(MovieContract.FavouriteEntry._POSTER_PATH, movie.getPosterPath());
        cv.put(MovieContract.FavouriteEntry._ADULT, movie.getAdult() ? 1 : 0);
        cv.put(MovieContract.FavouriteEntry._OVERVIEW, movie.getOverview());
        cv.put(MovieContract.FavouriteEntry._RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieContract.FavouriteEntry._ORIGINAL_TITLE, movie.getOriginalTitle());
        cv.put(MovieContract.FavouriteEntry._ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        cv.put(MovieContract.FavouriteEntry._BACKDROP_PATH, movie.getBackdropPath());
        cv.put(MovieContract.FavouriteEntry._POPULARITY, movie.getPopularity());
        cv.put(MovieContract.FavouriteEntry._VOTE_COUNT, movie.getVoteCount());
        cv.put(MovieContract.FavouriteEntry._VOTE_AVERAGE, movie.getVoteAverage());
        cv.put(MovieContract.FavouriteEntry._VIDEO, movie.getVideo() ? 1 : 0);
        return cv;
    }

    public static class FavouriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI
                        .buildUpon()
                        .appendPath(PATH_FAVOURITES)
                        .build();

        public static final String TABLE_NAME = "favourites";
        static final String _POSTER_PATH = "posterPath";
        static final String _BACKDROP_PATH = "backdropPath";
        static final String _ADULT = "adult";
        static final String _OVERVIEW = "overview";
        static final String _RELEASE_DATE = "releaseDate";
        static final String _ORIGINAL_TITLE = "originalTitle";
        static final String _ORIGINAL_LANGUAGE = "originalLanguage";
        static final String _TITLE = "title";
        static final String _POPULARITY = "popularity";
        static final String _VOTE_COUNT = "voteCount";
        static final String _VIDEO = "video";
        static final String _VOTE_AVERAGE = "voteAverage";

        static Uri entryUri(int id) {
            return CONTENT_URI
                    .buildUpon()
                    .appendPath(String.valueOf(id))
                    .build();
        }
    }
}
