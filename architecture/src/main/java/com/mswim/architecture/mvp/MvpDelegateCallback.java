package com.mswim.architecture.mvp;

import android.support.annotation.NonNull;

/**
 * Created by marcogalicia on 25/10/16.
 */

public interface MvpDelegateCallback <V extends MvpView, P extends MvpPresenter<V>> {
    @NonNull
    public P createPresenter();
    public P getPresenter();
    public V getMvpView();
}
