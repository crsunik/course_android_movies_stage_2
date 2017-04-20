package com.blackbirdsoft.movies.details;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MovieDetailsVideo implements Parcelable {
    public static final String TAG = MovieDetailsVideo.class.getSimpleName();

    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("iso_639_1")
    @Expose
    String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    String iso31661;
    @SerializedName("key")
    @Expose
    String key;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("site")
    @Expose
    String site;
    @SerializedName("size")
    @Expose
    Integer size;
    @SerializedName("type")
    @Expose
    String type;

    @Override
    public String toString() {
        return "MovieDetailsVideo{" +
                "id='" + id + '\'' +
                ", iso6391='" + iso6391 + '\'' +
                ", iso31661='" + iso31661 + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso6391);
        dest.writeString(this.iso31661);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeValue(this.size);
        dest.writeString(this.type);
    }

    public MovieDetailsVideo() {
    }

    protected MovieDetailsVideo(Parcel in) {
        this.id = in.readString();
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Parcelable.Creator<MovieDetailsVideo> CREATOR = new Parcelable.Creator<MovieDetailsVideo>() {
        public MovieDetailsVideo createFromParcel(Parcel source) {
            return new MovieDetailsVideo(source);
        }

        public MovieDetailsVideo[] newArray(int size) {
            return new MovieDetailsVideo[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDetailsVideo)) return false;

        MovieDetailsVideo video = (MovieDetailsVideo) o;

        if (id != null ? !id.equals(video.id) : video.id != null) return false;
        if (iso6391 != null ? !iso6391.equals(video.iso6391) : video.iso6391 != null) return false;
        if (iso31661 != null ? !iso31661.equals(video.iso31661) : video.iso31661 != null)
            return false;
        if (key != null ? !key.equals(video.key) : video.key != null) return false;
        if (name != null ? !name.equals(video.name) : video.name != null) return false;
        if (site != null ? !site.equals(video.site) : video.site != null) return false;
        if (size != null ? !size.equals(video.size) : video.size != null) return false;
        return type != null ? type.equals(video.type) : video.type == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (iso6391 != null ? iso6391.hashCode() : 0);
        result = 31 * result + (iso31661 != null ? iso31661.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
