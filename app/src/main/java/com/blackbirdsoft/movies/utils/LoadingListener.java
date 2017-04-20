package com.blackbirdsoft.movies.utils;

public interface LoadingListener<T1, T2> {

    void onLoadingBegin(T1 input);

    void onLoadingEnd(T1 input, T2 result);
}
