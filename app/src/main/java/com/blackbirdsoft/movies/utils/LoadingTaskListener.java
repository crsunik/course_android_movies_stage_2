package com.blackbirdsoft.movies.utils;

public interface LoadingTaskListener<T> {

    void onPreExecute();

    void onPostExecute(T result);

}