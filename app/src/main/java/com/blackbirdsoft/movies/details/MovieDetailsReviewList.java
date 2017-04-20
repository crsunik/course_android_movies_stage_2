package com.blackbirdsoft.movies.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailsReviewList implements Parcelable {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("page")
    @Expose
    Integer page;
    @SerializedName("results")
    @Expose
    List<MovieDetailsReview> results = null;
    @SerializedName("total_pages")
    @Expose
    Integer totalPages;
    @SerializedName("total_results")
    @Expose
    Integer totalResults;

    @Override
    public String toString() {
        return "MovieDetailsReviewList{" +
                "id=" + id +
                ", page=" + page +
                ", results=" + results +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.page);
        dest.writeTypedList(results);
        dest.writeValue(this.totalPages);
        dest.writeValue(this.totalResults);
    }

    public MovieDetailsReviewList() {
    }

    protected MovieDetailsReviewList(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(MovieDetailsReview.CREATOR);
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieDetailsReviewList> CREATOR = new Parcelable.Creator<MovieDetailsReviewList>() {
        public MovieDetailsReviewList createFromParcel(Parcel source) {
            return new MovieDetailsReviewList(source);
        }

        public MovieDetailsReviewList[] newArray(int size) {
            return new MovieDetailsReviewList[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDetailsReviewList)) return false;

        MovieDetailsReviewList that = (MovieDetailsReviewList) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        if (results != null ? !results.equals(that.results) : that.results != null) return false;
        if (totalPages != null ? !totalPages.equals(that.totalPages) : that.totalPages != null)
            return false;
        return totalResults != null ? totalResults.equals(that.totalResults) : that.totalResults == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
        result = 31 * result + (totalResults != null ? totalResults.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieDetailsReview> getResults() {
        return results;
    }

    public void setResults(List<MovieDetailsReview> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}