package com.blackbirdsoft.movies.list;

import android.content.Context;
import android.content.SharedPreferences;

import com.blackbirdsoft.movies.list.MoviesListType;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.blackbirdsoft.movies.list.MoviesListType.MOST_POPULAR;
import static com.blackbirdsoft.movies.list.MoviesListType.byCode;

class MoviesPreferencesUtils {
    private static final String SORTING = "sorting";

    private MoviesPreferencesUtils() {
    }

    static void setType(Context context, MoviesListType type) {
        SharedPreferences pref = getDefaultSharedPreferences(context);
        pref.edit().putString(SORTING, type.code()).apply();
    }

    static MoviesListType getType(Context context) {
        return byCode(getDefaultSharedPreferences(context).getString(SORTING, MOST_POPULAR.code()));
    }
}
