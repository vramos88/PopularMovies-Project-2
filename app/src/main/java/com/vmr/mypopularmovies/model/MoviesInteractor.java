package com.vmr.mypopularmovies.model;

import android.content.Context;

import com.vmr.mypopularmovies.api.client.Endpoints;
import com.vmr.mypopularmovies.api.client.MovieService;
import com.vmr.mypopularmovies.api.client.PopularMovieApp;
import com.vmr.mypopularmovies.api.model.Movie;
import com.vmr.mypopularmovies.api.model.Movies;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public class MoviesInteractor {
    MovieService mService;
    PopularMovieApp mApp;
    CompositeSubscription mCompositeSubscription;


    public MoviesInteractor(Context context){
        mApp = PopularMovieApp.get(context);
        mService = mApp.getMovieService();
        mCompositeSubscription = new CompositeSubscription();
    }

    public void getPopularMovies(final MoviesCallback callback){
        Subscription subscription = mService.getPopularMovies(Endpoints.API_KEY)
                .subscribeOn(mApp.SubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movies>() {
                    @Override
                    public void onCompleted() {
                        String t ="";

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("");
                    }

                    @Override
                    public void onNext(Movies movie) {
                        callback.onResponse(movie.getResults());
                    }
                });

        mCompositeSubscription.add(subscription);
    }

    public void getTopRatedMovies(final MoviesCallback callback){
        Subscription subscription = mService.getTopRatedMovies(Endpoints.API_KEY)
                .subscribeOn(mApp.SubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movies>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("");
                    }

                    @Override
                    public void onNext(Movies movies) {
                        callback.onResponse(movies.getResults());
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    public void unsubscribe(){
        if(mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }
}
