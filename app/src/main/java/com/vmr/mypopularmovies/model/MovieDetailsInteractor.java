package com.vmr.mypopularmovies.model;

import android.content.Context;

import com.vmr.mypopularmovies.api.client.Endpoints;
import com.vmr.mypopularmovies.api.client.MovieService;
import com.vmr.mypopularmovies.api.client.PopularMovieApp;
import com.vmr.mypopularmovies.api.model.MovieReviews;
import com.vmr.mypopularmovies.api.model.MovieVideos;
import com.vmr.mypopularmovies.api.model.Movies;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Victor Ramos on 3/13/17.
 */

public class MovieDetailsInteractor {

    MovieService mService;
    PopularMovieApp mApp;
    CompositeSubscription mCompositeSubscription;


    public MovieDetailsInteractor(Context context){
        mApp = PopularMovieApp.get(context);
        mService = mApp.getMovieService();
        mCompositeSubscription = new CompositeSubscription();
    }

    public void getMovieVideos(Integer id, final MovieDetailCallback callback){
        Subscription subs = mService.getMovieVideos(id,Endpoints.API_KEY)
                .subscribeOn(mApp.SubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieVideos>() {
                    @Override
                    public void onCompleted() {
                        String t ="";

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("");
                    }

                    @Override
                    public void onNext(MovieVideos movie) {
                        callback.onResponseMovieVideos(movie.getResults());
                    }
                });

        mCompositeSubscription.add(subs);
    }

    public void getMovieReviews(Integer id, final MovieDetailCallback callback){
        Subscription subs = mService.getMovieReviews(id,Endpoints.API_KEY)
                .subscribeOn(mApp.SubscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieReviews>() {
                    @Override
                    public void onCompleted() {
                        String t ="";

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError("");
                    }

                    @Override
                    public void onNext(MovieReviews movie) {
                        callback.onResponseMovieReviews(movie.getResults());
                    }
                });

        mCompositeSubscription.add(subs);
    }

    public void unsubscribe(){
        if(mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }
}
