package com.example.flixster;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

public class MoviesAPIClient {
    private final String API_KEY_QUERY_STRING = "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private AsyncHttpClient client;

    public MoviesAPIClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl + API_KEY_QUERY_STRING;
    }

    public void getNowPlayingMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/now_playing");
        client.get(url, handler);
    }

    public void getConfiguration(JsonHttpResponseHandler handler) {
        String url = getApiUrl("configuration");
        client.get(url, handler);
    }
}
