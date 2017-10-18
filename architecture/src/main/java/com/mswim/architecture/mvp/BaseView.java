package com.mswim.architecture.mvp;

import android.support.annotation.UiThread;

/**
 * Created by marcogalicia on 25/10/16.
 */

public interface BaseView<M> extends MvpView {

    @UiThread
    void showLoading(boolean showLoading);

    @UiThread
    void showError(int code, String error);

    @UiThread
    void setData(M data);

}
