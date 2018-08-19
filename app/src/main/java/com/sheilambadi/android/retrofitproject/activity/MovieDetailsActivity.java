package com.sheilambadi.android.retrofitproject.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.databinding.ActivityMovieDetailsBinding;
import com.sheilambadi.android.retrofitproject.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_movie_details);

        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        getIncomingIntentText();
        binding.setMovie(movie);
    }

    private void getIncomingIntentText(){
        if(getIntent().hasExtra("movieTitle")){
            String title = getIntent().getStringExtra("movieTitle");

            movie = new Movie();
            movie.setTitle(title);
        } else {
            Toast.makeText(this, "No text", Toast.LENGTH_SHORT).show();
        }
    }
}
