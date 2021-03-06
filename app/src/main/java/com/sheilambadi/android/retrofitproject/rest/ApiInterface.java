package com.sheilambadi.android.retrofitproject.rest;

import com.sheilambadi.android.retrofitproject.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    // endpoint to movies playing in theaters
    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    // endpoint to most popular movies
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    //endpoint to top rated movies ~ first 20
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    // endpoint to upcoming movies
    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    // endpoint to latest movies
    @GET("movie/latest")
    Call<MovieResponse> getLatestMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

    //endpoint to a specific movie
    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("/genre/movie/list")
    Call<MovieResponse> getGenreList(@Query("api_key") String apiKey);
}
