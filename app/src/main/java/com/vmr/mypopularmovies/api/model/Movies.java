package com.vmr.mypopularmovies.api.model;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/5/17.
 */

@Parcel
public class Movies {
    ArrayList<Movie> results;

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
