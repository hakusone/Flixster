package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

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

        // set the adapter on the recyler view
        rvMovies.setAdapter(movieAdapter);

        // set a layout manager on the recyler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        MoviesAPIClient client = new MoviesAPIClient();
        JsonHttpResponseHandler loadMovies = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results : " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies : " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        };

        JsonHttpResponseHandler setupImageConfig = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject images = jsonObject.getJSONObject("images");
                    String baseUrl = images.getString("secure_base_url");
                    JSONArray posterSizes = images.getJSONArray("poster_sizes");
                    JSONArray backdropSizes = images.getJSONArray("backdrop_sizes");

                    // Get second to the last, the one that's not original
                    String posterSize = posterSizes.getString(posterSizes.length() - 2);
                    String backdropSize = backdropSizes.getString(backdropSizes.length() - 2);

                    Log.i(TAG, "Secure Base URL : " + baseUrl);
                    Log.i(TAG, "Poster Size : " + posterSize);
                    Log.i(TAG, "Backdrop Size : " + backdropSize);

                    for (Movie movie : movies) {
                        movie.setPosterBasePath(baseUrl, posterSize);
                        movie.setBackdropBasePath(baseUrl, backdropSize);
                    }

                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        };

        client.getNowPlayingMovies(loadMovies);
        client.getConfiguration(setupImageConfig);
    }

}