package com.mswim.architecture.mvp;

import android.support.annotation.UiThread;

/**
 * Created by marcogalicia on 25/10/16.
 */

public interface MvpPresenter <V extends MvpView>{

    @UiThread
    void attachView(V view);

    @UiThread
    void detachView();

    @UiThread
    void onDestroy();

}
