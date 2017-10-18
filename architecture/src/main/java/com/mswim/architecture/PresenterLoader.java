package com.mswim.architecture;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.mswim.architecture.mvp.MvpPresenter;
import com.mswim.architecture.mvp.MvpView;

/**
 * Created by marcogalicia on 19/03/17.
 */

public final class PresenterLoader<V extends MvpView,P extends MvpPresenter<V>> extends Loader<P> {

    private P presenter;
    private final String tag;
    private int count=0;

    public PresenterLoader(Context context,P presenter, String tag) {
        super(context);
        this.presenter=presenter;
        this.tag = tag;
    }

    @Override
    protected void onStartLoading() {
        Log.i("loader", "onStartLoading-" + tag+" count: "+count++);

        // if we already own a presenter instance, simply deliver it.
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }

        // Otherwise, force a load
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        Log.i("loader", "onForceLoad-" + tag);
        // Deliver the result
        deliverResult(presenter);
    }

    @Override
    public void deliverResult(P data) {
        super.deliverResult(data);
        Log.i("loader", "deliverResult-" + tag);
    }

    @Override
    protected void onStopLoading() {
        Log.i("loader", "onStopLoading-" + tag);
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onReset() {
        Log.i("loader", "onReset-" + tag);
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}