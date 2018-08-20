package com.sheilambadi.android.retrofitproject.activity;

import android.content.Context;
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
import com.sheilambadi.android.retrofitproject.fragments.MovieDescriptionFragment;
import com.sheilambadi.android.retrofitproject.fragments.TopRatedFragment;
import com.sheilambadi.android.retrofitproject.fragments.YoutubeTrailerFragment;
import com.sheilambadi.android.retrofitproject.model.Movie;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/original";
    Movie movie;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView movieImage;
    String title, releaseDate, overview;
    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_movie_details);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/GothicAOne-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

       // MovieDescriptionFragment descriptionFragment = (MovieDescriptionFragment) getSupportFragmentManager().findFragmentByTag(MovieDescriptionFragment.class.getSimpleName());
       /* String title = getIntent().getStringExtra("movieTitle");
        Bundle data = new Bundle();
        data.putString("titleMovie", title);
        descriptionFragment.setArguments(data);*/

        /*String title = getIntent().getStringExtra("movieTitle");
        getSupportFragmentManager().beginTransaction().add(R.id.description_layout, MovieDescriptionFragment.newInstance(title, "Yoh"), "MovieDescriptionFragment").commit()*/;

        title = getIntent().getStringExtra("movieTitle");
        releaseDate = getIntent().getStringExtra("releaseDate");
        overview = getIntent().getStringExtra("overview");
        movieId = getIntent().getIntExtra("movieId", 1);

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // TODO: remove this tight coupling
    public String getMovieTitle(){
        return title;
    }

    public String getMovieReleaseDate(){
        return releaseDate;
    }

    public String getMovieOverview(){
        return overview;
    }

    public int getMovieId(){
        return movieId;
    }

    private void setupViewPager(ViewPager viewPager) {
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieDescriptionFragment(), "Description");
        adapter.addFragment(new YoutubeTrailerFragment(), "Watch Trailers");
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
