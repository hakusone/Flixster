package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    String rating;
    String posterBasePath;
    String backdropBasePath;
    String videoId;
    String id;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getId() {
        return id;
    }

    public void setPosterBasePath(String baseUrl, String size) {
        posterBasePath = baseUrl + size + "%s";
    }

    public void setBackdropBasePath(String baseUrl, String size) {
        backdropBasePath = baseUrl + size + "%s";
    }

    public String getPosterPath() {
        if (posterBasePath != null)
            return String.format(posterBasePath, posterPath);
        return null;
    }

    public String getBackdropPath() {
        if (backdropBasePath != null)
            return String.format(backdropBasePath, backdropPath);
        return null;
    }

    public Float getVoteAverage() {
        return Float.parseFloat(rating);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getString("vote_average");
        id = jsonObject.getString("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }
}
