package com.vmr.mypopularmovies.view;

import com.vmr.mypopularmovies.api.model.MovieReview;
import com.vmr.mypopularmovies.api.model.MovieVideo;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/13/17.
 */

public interface MovieDetailView extends BaseView{
    void renderMovieVideos(ArrayList<MovieVideo> videos);
    void renderMovieReviews(ArrayList<MovieReview>reviews);
}
