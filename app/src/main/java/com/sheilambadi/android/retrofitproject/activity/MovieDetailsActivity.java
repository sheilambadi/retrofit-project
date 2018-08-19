package com.sheilambadi.android.retrofitproject.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sheilambadi.android.retrofitproject.GlideApp;
import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.adapter.TabsPagerAdapter;
import com.sheilambadi.android.retrofitproject.databinding.ActivityMovieDetailsBinding;
import com.sheilambadi.android.retrofitproject.fragments.NowPlayingFragment;
import com.sheilambadi.android.retrofitproject.fragments.PopularMoviesFragment;
import com.sheilambadi.android.retrofitproject.fragments.TopRatedFragment;
import com.sheilambadi.android.retrofitproject.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/original";
    Movie movie;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_movie_details);

        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        viewPager = binding.moviesViewPager;
        setupViewPager(viewPager);

        tabLayout = binding.movieTabs;
        tabLayout.setupWithViewPager(viewPager);
        movieImage = binding.moviePic;
        String imageLink = "";

        if(getIntent().hasExtra("moviePic")){
            imageLink =  getIntent().getStringExtra("moviePic");
        }

        GlideApp.with(this)
                .load(BASE_URL_IMG + imageLink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .error(new ColorDrawable(Color.GRAY))
                .thumbnail(0.1f)
                .into(movieImage);

        // getIncomingIntentText();
        binding.setMovie(movie);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), "Description");
        adapter.addFragment(new PopularMoviesFragment(), "Watch Trailers");
        adapter.addFragment(new TopRatedFragment(), "Similar Movies");
        // adapter.addFragment(new UpcomingFragment(), "Upcoming");
        // Todo: Add Latest movies fragment
        viewPager.setAdapter(adapter);
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
