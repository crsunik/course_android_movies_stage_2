package com.blackbirdsoft.movies.list;

enum MoviesListType {
    TOP_RATED("top_rated"), MOST_POPULAR("most_popular"), FAVOURITES("favourites");

    private final String mCode;

    MoviesListType(String code) {
        mCode = code;
    }

    @Override
    public String toString() {
        return mCode;
    }

    public String code() {
        return mCode;
    }

    public static MoviesListType byCode(String code) {
        for(MoviesListType type : values())
            if (type.code().equals(code))
                return type;
        return null;
    }
}
