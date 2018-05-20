package com.sheilambadi.android.retrofitproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;

import com.sheilambadi.android.retrofitproject.R;

import com.sheilambadi.android.retrofitproject.adapter.TabsPagerAdapter;


public class BrowseMoviesFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public BrowseMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_movies, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

     private void setupViewPager(ViewPager viewPager) {
        TabsPagerAdapter adapter = new TabsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NowPlayingFragment(), "Now Playing");
        adapter.addFragment(new PopularMoviesFragment(), "Popular");
        adapter.addFragment(new TopRatedFragment(), "Top Rated");
        // adapter.addFragment(new UpcomingFragment(), "Upcoming");
         // Todo: Add Latest movies fragment
        viewPager.setAdapter(adapter);
    }
}
