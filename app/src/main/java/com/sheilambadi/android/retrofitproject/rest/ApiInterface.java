package com.sheilambadi.android.retrofitproject.rest;

import com.sheilambadi.android.retrofitproject.model.MovieResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //endpoint to top rated movies ~ first 20
    @GET("movie/top_rated")
    Class<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    //endpoint to a specific movie
    @GET("movie/{id}")
    Class<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
