package com.blackbirdsoft.movies.domain.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.blackbirdsoft.movies.domain.db.MovieContract;

public class MoviesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + MovieContract.FavouriteEntry.TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + MovieContract.FavouriteEntry._TITLE + " TEXT,"
                + MovieContract.FavouriteEntry._POSTER_PATH + " TEXT,"
                + MovieContract.FavouriteEntry._ADULT + " INTEGER,"
                + MovieContract.FavouriteEntry._OVERVIEW + " TEXT,"
                + MovieContract.FavouriteEntry._RELEASE_DATE + " TEXT,"
                + MovieContract.FavouriteEntry._ORIGINAL_TITLE + " TEXT,"
                + MovieContract.FavouriteEntry._ORIGINAL_LANGUAGE + " TEXT,"
                + MovieContract.FavouriteEntry._BACKDROP_PATH + " TEXT,"
                + MovieContract.FavouriteEntry._POPULARITY + " REAL,"
                + MovieContract.FavouriteEntry._VOTE_COUNT + " INTEGER,"
                + MovieContract.FavouriteEntry._VOTE_AVERAGE + " REAL,"
                + MovieContract.FavouriteEntry._VIDEO + " INTEGER)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
