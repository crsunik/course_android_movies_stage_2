package com.blackbirdsoft.movies.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jaroslawchmiel on 20/04/2017.
 */

public class MovieDetailsVideoList implements Parcelable {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("results")
    @Expose
    List<MovieDetailsVideo> results = null;

    @Override
    public String toString() {
        return "MovieDetailsVideoList{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeTypedList(results);
    }

    public MovieDetailsVideoList() {
    }

    protected MovieDetailsVideoList(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(MovieDetailsVideo.CREATOR);
    }

    public static final Parcelable.Creator<MovieDetailsVideoList> CREATOR = new Parcelable.Creator<MovieDetailsVideoList>() {
        public MovieDetailsVideoList createFromParcel(Parcel source) {
            return new MovieDetailsVideoList(source);
        }

        public MovieDetailsVideoList[] newArray(int size) {
            return new MovieDetailsVideoList[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDetailsVideoList)) return false;

        MovieDetailsVideoList that = (MovieDetailsVideoList) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return results != null ? results.equals(that.results) : that.results == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieDetailsVideo> getResults() {
        return results;
    }

    public void setResults(List<MovieDetailsVideo> results) {
        this.results = results;
    }
}
