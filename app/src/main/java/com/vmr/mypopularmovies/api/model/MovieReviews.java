package com.vmr.mypopularmovies.api.model;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/13/17.
 */

@Parcel
public class MovieReviews {
    ArrayList<MovieReview> results;

    public ArrayList<MovieReview> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieReview> results) {
        this.results = results;
    }
}
