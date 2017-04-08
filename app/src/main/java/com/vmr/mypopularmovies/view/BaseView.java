package com.vmr.mypopularmovies.view;

import android.content.Context;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public interface BaseView {
    Context getContext();
    void showLoading();
    void hideLoading();
    void showEmptyState();
    void showError(String error);
}
