package com.sheilambadi.android.retrofitproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheilambadi.android.retrofitproject.R;
import com.sheilambadi.android.retrofitproject.activity.MovieDetailsActivity;

public class MovieDescriptionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDescriptionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MovieDescriptionFragment newInstance(String param1, String param2) {
        MovieDescriptionFragment fragment = new MovieDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_description, container, false);

        TextView movieText = view.findViewById(R.id.txt_movie_title);
        TextView releaseDateText = view.findViewById(R.id.txt_release_date);
        TextView overviewText = view.findViewById(R.id.txt_overview);
        /*Bundle getData = getArguments();
        String movieTitle = getData.getString("titleMovie");*/

        MovieDetailsActivity movieDetailsActivity = (MovieDetailsActivity) getActivity();
        String movieTitle = movieDetailsActivity.getMovieTitle();
        String movieReleaseDate = movieDetailsActivity.getMovieReleaseDate();
        String movieOverview = movieDetailsActivity.getMovieOverview();
        movieText.setText(movieTitle);
        releaseDateText.setText(movieReleaseDate);
        overviewText.setText(movieOverview);


        return view;
    }

}
