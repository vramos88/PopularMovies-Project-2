package com.vmr.mypopularmovies.presenter;

import com.vmr.mypopularmovies.api.model.MovieReview;
import com.vmr.mypopularmovies.api.model.MovieVideo;
import com.vmr.mypopularmovies.model.MovieDetailCallback;
import com.vmr.mypopularmovies.model.MovieDetailsInteractor;
import com.vmr.mypopularmovies.view.MovieDetailView;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/13/17.
 */

public class MovieDetailsPresenter implements BasePresenter<MovieDetailView>, MovieDetailCallback {

    private MovieDetailView mView;
    private MovieDetailsInteractor mInteractor;

    public void getMovieVideos(Integer id){
        mView.showLoading();
        mInteractor.getMovieVideos(id,this);
    }

    public void getMovieReviews(Integer id){
        mView.showLoading();
        mInteractor.getMovieReviews(id,this);
    }

    @Override
    public void onError(String errorMsg) {
        mView.hideLoading();
        mView.showError(errorMsg);
    }

    @Override
    public void onEmptyResponse() {
        mView.hideLoading();
        mView.showEmptyState();
    }

    @Override
    public void setView(MovieDetailView view) {
        mView = view;
        mInteractor = new MovieDetailsInteractor(mView.getContext());
    }

    @Override
    public void detachView() {
        mView = null;
        mInteractor.unsubscribe();
    }

    @Override
    public void onResponseMovieVideos(ArrayList<MovieVideo> videos) {
        mView.hideLoading();
        mView.renderMovieVideos(videos);
    }

    @Override
    public void onResponseMovieReviews(ArrayList<MovieReview> reviews) {
        mView.hideLoading();
        mView.renderMovieReviews(reviews);

    }
}
