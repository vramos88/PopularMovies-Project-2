package com.vmr.mypopularmovies.model;

import com.vmr.mypopularmovies.api.model.Movie;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public interface MoviesCallback extends BaseCallback{
    void onResponse(ArrayList<Movie> movies);
}
