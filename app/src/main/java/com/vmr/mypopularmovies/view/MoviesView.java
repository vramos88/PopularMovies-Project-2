package com.vmr.mypopularmovies.view;

import com.vmr.mypopularmovies.api.model.Movie;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public interface MoviesView extends BaseView {

    void renderMovies(ArrayList<Movie> movies);

}
