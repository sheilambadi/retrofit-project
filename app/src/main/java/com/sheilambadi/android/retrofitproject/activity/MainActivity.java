package com.sheilambadi.android.retrofitproject.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.adapter.MoviesAdapter;
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
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/GothicAOne-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.pb_movies_list);

        moviesPaginationAdapter = new MoviesPaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(moviesPaginationAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;


                loadNextPage();
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
        loadFirstPage();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        Call<MovieResponse> call = movieService.getPopularMovies(API_KEY, currentPage);
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
                Toast.makeText(getApplicationContext(), "Failed to load movies on first page", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        Call<MovieResponse> call = movieService.getPopularMovies(API_KEY, currentPage);
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
                Toast.makeText(getApplicationContext(), "Failed to load movies on first page", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
