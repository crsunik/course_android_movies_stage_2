package com.blackbirdsoft.movies.domain.db;

import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import com.blackbirdsoft.movies.App;
import com.blackbirdsoft.movies.domain.Movie;
import com.blackbirdsoft.movies.domain.MoviesList;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMoviesManager {

    private static final int INSERT = 1001;
    private static final int UPDATE = 1002;

    private final Context mContext;

    public FavouriteMoviesManager() {
        this(App.context());
    }

    public FavouriteMoviesManager(Context context) {
        mContext = context;
    }

    public MoviesList queryFavourites() {
        List<Movie> results = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContext
                    .getContentResolver()
                    .query(MovieContract.FavouriteEntry.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0)
                while (cursor.moveToNext()) {
                    Movie movie = MovieContract.fromCursor(cursor);
                    movie.setFavourite(Movie.FavouriteState.FAVOURITE);
                    results.add(movie);
                }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        MoviesList list = new MoviesList();
        list.setPage(1);
        list.setResults(results);
        list.setTotalPages(1);
        list.setTotalResults(results.size());
        return list;
    }

    public Movie queryFavourite(int id) {
        Uri movieUri = MovieContract.FavouriteEntry.entryUri(id);
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(movieUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                Movie movie = MovieContract.fromCursor(cursor);
                movie.setFavourite(Movie.FavouriteState.FAVOURITE);
                return movie;
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public int deleteFavourite(Movie movie) {
        Uri movieUri = MovieContract.FavouriteEntry.entryUri(movie.getId());
        return mContext.getContentResolver().delete(movieUri, null, null);
    }

    public void insertOrUpdateFavourite(Movie movie) {
        ContentValues contentValues = MovieContract.toContent(movie);
        try {
            new AsyncQueryHandler(mContext.getContentResolver()) {
            }.startInsert(INSERT, null, MovieContract.FavouriteEntry.CONTENT_URI, contentValues);
        } catch (SQLiteException ex) {
            new AsyncQueryHandler(mContext.getContentResolver()) {
            }.startUpdate(UPDATE, null, MovieContract.FavouriteEntry.entryUri(movie.getId()), contentValues, null, null);
        }
    }
}
