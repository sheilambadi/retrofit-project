package com.sheilambadi.android.retrofitproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.activity.MovieDetailsActivity;
import com.sheilambadi.android.retrofitproject.adapter.TrailerAdapter;
import com.sheilambadi.android.retrofitproject.model.Result;
import com.sheilambadi.android.retrofitproject.model.YoutubeTrailer;
import com.sheilambadi.android.retrofitproject.rest.ApiClient;
import com.sheilambadi.android.retrofitproject.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class YoutubeTrailerFragment extends Fragment {
    //the movie db api key
    private static final String API_KEY = "371550cc422e64f0e576183b8aac074e";
    LinearLayoutManager linearLayoutManager;
    TrailerAdapter trailerAdapter;
    RecyclerView recyclerView;
    private ApiInterface movieService;

    public YoutubeTrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube_trailer, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_trailer);

        // trailerAdapter = new TrailerAdapter(getContext());

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // recyclerView.setAdapter(trailerAdapter);

        movieService = ApiClient.getClient().create(ApiInterface.class);

        MovieDetailsActivity movieDetailsActivity = (MovieDetailsActivity) getActivity();
        int movieId = movieDetailsActivity.getMovieId();

        Call<YoutubeTrailer> call = movieService.getYoutubeTrailer(movieId, API_KEY);
        call.enqueue(new Callback<YoutubeTrailer>() {
            @Override
            public void onResponse(Call<YoutubeTrailer> call, Response<YoutubeTrailer> response) {

                if(response.body().getResults() != null){
                    List<Result> trailerResult = response.body().getResults();
                    recyclerView.setAdapter(new TrailerAdapter(trailerResult, getContext()));
                }
            }

            @Override
            public void onFailure(Call<YoutubeTrailer> call, Throwable t) {

            }
        });

        return view;
    }
}
