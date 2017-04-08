package com.vmr.mypopularmovies.api.model;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/13/17.
 */

@Parcel
public class MovieVideos {
    ArrayList<MovieVideo> results;


    public ArrayList<MovieVideo> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieVideo> results) {
        this.results = results;
    }
}
