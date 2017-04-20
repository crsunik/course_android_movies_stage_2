package com.blackbirdsoft.movies.utils;

public interface LoadingListener<T1, T2> {

    void onMovieLoadingBegin(T1 input);

    void onMovieLoadingEnd(T1 input, T2 result);
}
