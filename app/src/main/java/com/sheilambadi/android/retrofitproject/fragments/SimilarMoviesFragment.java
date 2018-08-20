package com.sheilambadi.android.retrofitproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.activity.MovieDetailsActivity;
import com.sheilambadi.android.retrofitproject.adapter.MoviesPaginationAdapter;
import com.sheilambadi.android.retrofitproject.model.Movie;
import com.sheilambadi.android.retrofitproject.model.MovieResponse;
import com.sheilambadi.android.retrofitproject.rest.ApiClient;
import com.sheilambadi.android.retrofitproject.rest.ApiInterface;
import com.sheilambadi.android.retrofitproject.utils.PaginationScrollListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimilarMoviesFragment extends Fragment {
    private static final String TAG = SimilarMoviesFragment.class.getSimpleName();

    MoviesPaginationAdapter moviesPaginationAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    private ApiInterface movieService;

    //the movie db api key
    private static final String API_KEY = "371550cc422e64f0e576183b8aac074e";

    // int movieId;

    public SimilarMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.pb_movies_list);

        MovieDetailsActivity movieDetailsActivity = (MovieDetailsActivity) getActivity();
        final int movieId = movieDetailsActivity.getMovieId();

        moviesPaginationAdapter = new MoviesPaginationAdapter(getContext());

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(moviesPaginationAdapter);
        //checkConnection();
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                loadNextPage(movieId);
            }
            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        // initialize service amd load data
        movieService = ApiClient.getClient().create(ApiInterface.class);
        loadFirstPage(movieId);

        return view;
    }

    private void loadFirstPage(int idMovie) {
        Log.d(TAG, "loadFirstPage: ");
        Call<MovieResponse> call = movieService.getSimilarMovies(idMovie, API_KEY, currentPage);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                //TOTAL_PAGES = response.body().getTotalPages();
                List<Movie> movies = response.body().getResults();
                Log.i("Pages", response.body().getTotalPages()+"");
                progressBar.setVisibility(View.GONE);
                moviesPaginationAdapter.addAll(movies);

                if (currentPage <= response.body().getTotalPages()){
                    moviesPaginationAdapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //Todo: handle failure
                //Toast.makeText(getApplicationContext(), "Failed to load movies on first page", Toast.LENGTH_SHORT).show();
                /*boolean isConnected = ConnectivityReceiver.isConnected();
                checkInternetConnection(isConnected);*/
            }
        });
    }

    private void loadNextPage(int idMovie) {
        Log.d(TAG, "loadNextPage: " + currentPage);

        Call<MovieResponse> call = movieService.getSimilarMovies(idMovie, API_KEY, currentPage);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                moviesPaginationAdapter.removeLoadingFooter();
                isLoading = false;

                List<Movie> movies = response.body().getResults();
                moviesPaginationAdapter.addAll(movies);

                if (currentPage != response.body().getTotalPages()){
                    moviesPaginationAdapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                //Todo: handle failure
                Toast.makeText(getContext(), "Failed to load movies on first page", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

