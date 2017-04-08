package com.vmr.mypopularmovies.api.model;

import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Victor Ramos on 2/5/17.
 */

@Parcel
public class Movie  {
    String poster_path;
    Boolean adult;
    String overview;
    String release_date;
    ArrayList<Integer> genre_ids;
    Integer id;
    String original_title;
    String original_language;
    String title;
    String backdrop_path;
    Double popularity;
    Integer vote_count;
    Boolean video;
    Double vote_average;

    public Movie(){}

    public Movie(JSONObject jsonObj){
        poster_path = String.format(Locale.getDefault(),"%s%s","http://image.tmdb.org/t/p/w185/",jsonObj.optString("poster_path"));
        adult = jsonObj.optBoolean("adult");
        overview = jsonObj.optString("overview");
        release_date = jsonObj.optString("release_date");
        id = jsonObj.optInt("id");
        original_title = jsonObj.optString("original_title");
        original_language = jsonObj.optString("original_language");
        title = jsonObj.optString("title");
        backdrop_path = jsonObj.optString("backdrop_ath");
        popularity = jsonObj.optDouble("popularity");
        vote_count = jsonObj.optInt("vote_count");
        video = jsonObj.optBoolean("video");
        vote_average = jsonObj.optDouble("vote_average");
    }

    public Movie(Integer id, String title, String releaseDate, String overview, String voteAvg, String posterPath, String backdropPath){
        this.id = id;
        this.title = title;
        this.release_date = releaseDate;
        this.overview = overview;
        this.vote_average = Double.parseDouble(voteAvg);
        this.poster_path = posterPath;
        this.backdrop_path = backdropPath;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getPosterURL(){
        return String.format(Locale.getDefault(),"%s%s","http://image.tmdb.org/t/p/w185/",poster_path);
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getBackdropURL(){
        return String.format(Locale.getDefault(),"%s%s","http://image.tmdb.org/t/p/w185/",backdrop_path);
    }

    public void setBackdrop_path(String backdrop_ath) {
        this.backdrop_path = backdrop_ath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

}
