package com.mswim.architecture.mvp;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * Created by marcogalicia on 25/10/16.
 */

public class BasePresenter <V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> viewRef;

    @UiThread
    @Override public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    @UiThread
    @Nullable
    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @UiThread
    @Override public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void onDestroy() {

    }

}
