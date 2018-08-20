package com.sheilambadi.android.retrofitproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.model.Result;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    List<Result> trailerResults;
    Context context;

    public TrailerAdapter(List<Result> trailerResults, Context context) {
        this.context = context;
        this.trailerResults = trailerResults;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_layout, parent, false);

        return new TrailerVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Result movieTrailers = trailerResults.get(position);

        final TrailerVH trailerHolder = (TrailerVH) holder;

        trailerHolder.trailerText.setText(YOUTUBE_BASE_URL +  movieTrailers.getKey());
    }

    @Override
    public int getItemCount() {
        return trailerResults.size();
    }

    public class TrailerVH extends RecyclerView.ViewHolder{
        public TextView trailerText;

        public TrailerVH(View itemView) {
            super(itemView);

            trailerText = itemView.findViewById(R.id.tv_link);
        }
    }
}
