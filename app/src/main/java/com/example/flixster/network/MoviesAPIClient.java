package com.example.flixster.network;

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

    public static final String TAG = "MoviesAPIClient";
    private final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private AsyncHttpClient client;
    private String apiKey;

    public MoviesAPIClient(String apiKey) {
        this.client = new AsyncHttpClient();
        this.apiKey = apiKey;
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl + "?api_key=" + apiKey;
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
                    getConfiguration(movieAdapter, movies);

                    for (Movie movie : movies) {
                        getVideoForMovie(movie);
                    }
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

    public void getConfiguration(MovieAdapter movieAdapter, ArrayList<Movie> movies) {
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
    }

    public void getVideoForMovie(Movie movie) {
        String movieVideoUrl = "movie/" + movie.getId() + "/videos";
        Log.d(TAG, getApiUrl(movieVideoUrl));

        client.get(getApiUrl(movieVideoUrl), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() > 0) {
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject obj = (JSONObject) results.get(i);
                            if (obj.getString("site").equals("YouTube")
                            && obj.getString("type").equals("Trailer")) {
                                Log.d(TAG, obj.getString("key"));
                                movie.setVideoId(obj.getString("key"));
                                break;
                            }
                        }
                        if (movie.getVideoId() == null) {
                            JSONObject firstResult = (JSONObject) results.get(0);
                            String videoId = firstResult.getString("key");
                            movie.setVideoId(videoId);
                        }
                    }
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
    }

}
