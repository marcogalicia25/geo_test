package com.mswim.architecture;

import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mswim.architecture.mvp.MvpDelegateCallback;
import com.mswim.architecture.mvp.MvpPresenter;
import com.mswim.architecture.mvp.MvpView;

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends AppCompatActivity implements MvpDelegateCallback<V, P>, MvpView {

    protected P presenter;
    private static final String TAG = "base-activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportLoaderManager().initLoader(loaderId(), null, new LoaderManager.LoaderCallbacks<P>() {
            @Override
            public final Loader<P> onCreateLoader(int id, Bundle args) {
                Log.i(TAG, "onCreateLoader");
                return new PresenterLoader<>(BaseActivity.this, createPresenter(), tag());
            }

            @Override
            public final void onLoadFinished(Loader<P> loader, P presenter) {
                Log.i(TAG, "onLoadFinished");
                BaseActivity.this.presenter = presenter;
                onPresenterPrepared(presenter);
            }

            @Override
            public final void onLoaderReset(Loader<P> loader) {
                Log.i(TAG, "onLoaderReset");
                BaseActivity.this.presenter = null;
                onPresenterDestroyed();
            }
        });

    }


    protected abstract void onPresenterPrepared(@NonNull P presenter);

    protected void onPresenterDestroyed() {
    }

    protected abstract int loaderId();

    @NonNull
    protected abstract String tag();

    @NonNull
    @Override
    public abstract P createPresenter();

    @Override
    public P getPresenter() {
        return presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }

    public BaseActivity getBaseActivity() {
        return BaseActivity.this;
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null)
            getPresenter().onDestroy();
        super.onDestroy();
    }
}
