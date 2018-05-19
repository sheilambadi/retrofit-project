package com.sheilambadi.android.retrofitproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 5/8/2018.
 */

public class GenreResponse implements Parcelable {
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;

    public GenreResponse() {
    }


    public GenreResponse(List<Genre> genres) {
        super();
        this.genres = genres;
    }

    protected GenreResponse(Parcel in) {
        genres = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Creator<GenreResponse> CREATOR = new Creator<GenreResponse>() {
        @Override
        public GenreResponse createFromParcel(Parcel in) {
            return new GenreResponse(in);
        }

        @Override
        public GenreResponse[] newArray(int size) {
            return new GenreResponse[size];
        }
    };

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(genres);
    }
}
