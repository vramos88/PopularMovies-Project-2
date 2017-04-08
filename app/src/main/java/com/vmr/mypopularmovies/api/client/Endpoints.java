package com.vmr.mypopularmovies.api.client;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public class Endpoints {

    public static final String API_KEY = "";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String TOP_RATED = "movie/top_rated";
    public static final String POPULAR = "movie/popular";
    public static final String MOVIE_VIDEOS = "movie/{"+QueryParam.ID+"}/videos";
    public static final String MOVIE_REVIEWS = "movie/{"+QueryParam.ID+"}/reviews";

}
