package com.example.flixster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.flixster.network.MoviesAPIClient;
import com.example.flixster.R;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        movies = new ArrayList<>();

        // create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // set a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        MoviesAPIClient client = new MoviesAPIClient(getString(R.string.themoviedb_api_key));

        client.getNowPlayingMovies(movieAdapter, (ArrayList<Movie>) movies);
    }

}