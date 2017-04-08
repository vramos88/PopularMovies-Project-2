package com.vmr.mypopularmovies.model;

/**
 * Created by Victor Ramos on 3/13/17.
 */

public interface BaseCallback {
    void onError(String errorMsg);
    void onEmptyResponse();
}
