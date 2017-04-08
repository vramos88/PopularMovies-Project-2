package com.vmr.mypopularmovies.api.client;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public class PopularMovieApp extends Application {

    private MovieService mMovieService;
    private Scheduler mScheduler;

    public static PopularMovieApp get(Context context) {
        return (PopularMovieApp) context.getApplicationContext();
    }

    public MovieService getMovieService() {
        if (mMovieService == null) mMovieService = MovieClient.create();

        return mMovieService;
    }

    //For setting mocks during testing
    public void setMovieService(MovieService spotifyService) {
        this.mMovieService = spotifyService;
    }

    public Scheduler SubscribeScheduler() {
        if (mScheduler == null) mScheduler = Schedulers.io();

        return mScheduler;
    }

    //User to change scheduler from tests
    public void setScheduler(Scheduler scheduler) {
        this.mScheduler = scheduler;
    }
}
