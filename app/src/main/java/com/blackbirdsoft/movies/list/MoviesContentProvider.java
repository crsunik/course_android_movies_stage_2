package com.blackbirdsoft.movies.list;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blackbirdsoft.movies.domain.db.MovieContract;
import com.blackbirdsoft.movies.domain.db.MoviesDbHelper;

public class MoviesContentProvider extends ContentProvider {
    public static final String TAG = MoviesContentProvider.class.getSimpleName();
    public static final int CODE_FAVOURITES = 100;
    public static final int CODE_FAVOURITES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES, CODE_FAVOURITES);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITES + "/#", CODE_FAVOURITES_WITH_ID);
        return matcher;
    }

    private MoviesDbHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CODE_FAVOURITES_WITH_ID:
                cursor = getFavouriteById(projection, sortOrder, uri.getLastPathSegment());
                break;
            case CODE_FAVOURITES:
                cursor = getFavourites(projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private Cursor getFavourites(@Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(MovieContract.FavouriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor getFavouriteById(@Nullable String[] projection, @Nullable String sortOrder, String movieId) {
        String[] selectionArguments = new String[]{movieId};
        return getFavourites(projection, BaseColumns._ID + " = ? ", selectionArguments, sortOrder);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri inserted;

        switch (match) {
            case CODE_FAVOURITES:
                if (values == null || !values.containsKey(BaseColumns._ID)) {
                    throw new UnsupportedOperationException("Need movie id to properly save record into db");
                }
                long id = db.insertWithOnConflict(MovieContract.FavouriteEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                if (id > 0) {
                    inserted = ContentUris.withAppendedId(MovieContract.FavouriteEntry.CONTENT_URI, id);
                } else {
                    throw new SQLiteException("error while inserting data");
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (inserted != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return inserted;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        if (selection == null || "".equals(selection))
            selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_FAVOURITES:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        MovieContract.FavouriteEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case CODE_FAVOURITES_WITH_ID:
                String movieId = uri.getLastPathSegment();
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        MovieContract.FavouriteEntry.TABLE_NAME,
                        MovieContract.FavouriteEntry._ID + " = ? ",
                        new String[]{movieId});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int updated;
        switch (match) {
            case CODE_FAVOURITES_WITH_ID:
                String movieId = uri.getLastPathSegment();
                if (values != null && values.containsKey(MovieContract.FavouriteEntry._ID)) {
                    values.remove(MovieContract.FavouriteEntry._ID);
                }
                updated = db.update(
                        MovieContract.FavouriteEntry.TABLE_NAME,
                        values,
                        MovieContract.FavouriteEntry._ID + " = ? ",
                        new String[]{movieId}
                );
                break;
            case CODE_FAVOURITES:
                updated = db.update(
                        MovieContract.FavouriteEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
