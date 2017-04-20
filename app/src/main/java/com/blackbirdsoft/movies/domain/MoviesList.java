package com.blackbirdsoft.movies.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesList implements Parcelable {

    public static final Parcelable.Creator<MoviesList> CREATOR = new Parcelable.Creator<MoviesList>() {
        public MoviesList createFromParcel(Parcel source) {
            return new MoviesList(source);
        }

        public MoviesList[] newArray(int size) {
            return new MoviesList[size];
        }
    };

    @Expose
    @SerializedName("page")
    private Integer page;
    @Expose
    @SerializedName("results")
    private List<Movie> results = null;
    @Expose
    @SerializedName("total_results")
    private Integer totalResults;
    @Expose
    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public MoviesList() {
    }

    private MoviesList(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeTypedList(results);
        dest.writeValue(this.totalResults);
        dest.writeValue(this.totalPages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoviesList moviesList = (MoviesList) o;

        if (page != null ? !page.equals(moviesList.page) : moviesList.page != null) return false;
        if (results != null ? !results.equals(moviesList.results) : moviesList.results != null)
            return false;
        if (totalResults != null ? !totalResults.equals(moviesList.totalResults) : moviesList.totalResults != null)
            return false;
        return totalPages != null ? totalPages.equals(moviesList.totalPages) : moviesList.totalPages == null;
    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + (totalResults != null ? totalResults.hashCode() : 0);
        result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
        return result;
    }
}