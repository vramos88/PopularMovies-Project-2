package com.vmr.mypopularmovies.presenter;

import com.vmr.mypopularmovies.api.model.Movie;
import com.vmr.mypopularmovies.model.MoviesCallback;
import com.vmr.mypopularmovies.model.MoviesInteractor;
import com.vmr.mypopularmovies.view.MoviesView;

import java.util.ArrayList;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public class MoviesPresenter implements BasePresenter<MoviesView>, MoviesCallback {

    private MoviesView mView;
    private MoviesInteractor mInteractor;

    @Override
    public void setView(MoviesView view) {

        mView = view;
        mInteractor = new MoviesInteractor(mView.getContext());
    }

    @Override
    public void detachView() {
        mView = null;
        mInteractor.unsubscribe();
    }

    @Override
    public void onResponse(ArrayList<Movie> movies) {
        mView.hideLoading();
        mView.renderMovies(movies);
    }

    @Override
    public void onEmptyResponse() {
        mView.hideLoading();
        mView.showEmptyState();

    }

    @Override
    public void onError(String errorMsg) {
        mView.hideLoading();
        mView.showError(errorMsg);
    }

    public void getPopularMovies(){
        mView.showLoading();
        mInteractor.getPopularMovies(this);
    }

    public void getTopRatedMovies(){
        mView.showLoading();
        mInteractor.getTopRatedMovies(this);
    }
}
