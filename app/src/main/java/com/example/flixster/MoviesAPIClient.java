package com.example.flixster;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;

public class MoviesAPIClient {

    public static final String TAG = "MainActivity";

    private final String API_KEY_QUERY_STRING = "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private AsyncHttpClient client;

    public MoviesAPIClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl + API_KEY_QUERY_STRING;
    }

    public void getNowPlayingMovies(MovieAdapter movieAdapter, ArrayList<Movie> movies) {
        JsonHttpResponseHandler loadMovies = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results : " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    Log.i(TAG, "Movies : " + movies.size());

                    client.get(getApiUrl("configuration"), new JsonHttpResponseHandler() {
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
                    });
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

        String url = getApiUrl("movie/now_playing");
        client.get(url, loadMovies);
    }
}
