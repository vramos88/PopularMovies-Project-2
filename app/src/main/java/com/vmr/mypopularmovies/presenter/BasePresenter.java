package com.vmr.mypopularmovies.presenter;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public interface BasePresenter <V> {
    void setView(V view);
    void detachView();
}
