package com.vmr.mypopularmovies.model;

import com.vmr.mypopularmovies.api.model.MovieReview;
import com.vmr.mypopularmovies.api.model.MovieVideo;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/13/17.
 */

public interface MovieDetailCallback extends BaseCallback{
    void onResponseMovieVideos(ArrayList<MovieVideo> videos);
    void onResponseMovieReviews(ArrayList<MovieReview> reviews);
}
