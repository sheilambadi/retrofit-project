package com.sheilambadi.android.retrofitproject.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.model.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private  int rowLayout;
    Context context;

    public class MovieViewHolder extends  RecyclerView.ViewHolder{
        CardView moviesLayout;
        TextView movieTitle;
        TextView movieReleaseDate;
        TextView movieDescription;
        TextView movieRating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            moviesLayout =  itemView.findViewById(R.id.movies_layout);
            movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.tv_movie_genres);
            movieDescription = (TextView) itemView.findViewById(R.id.tv_movie_description);
            movieRating = (TextView) itemView.findViewById(R.id.tv_ratings);
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MovieViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.movieReleaseDate.setText((movies.get(position).getReleaseDate()));
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.movieRating.setText(movies.get(position).getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
