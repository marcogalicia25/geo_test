package com.mswim.architecture;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import com.mswim.architecture.mvp.MvpDelegateCallback;
import com.mswim.architecture.mvp.MvpPresenter;
import com.mswim.architecture.mvp.MvpView;

/**
 * Created by marcogalicia on 25/10/16.
 */

public abstract class BaseFragment <V extends MvpView, P extends MvpPresenter<V>> extends Fragment
        implements MvpDelegateCallback<V, P>, MvpView {

    protected P presenter;
    private static final String TAG = "base-fragment";

    @NonNull
    @Override
    public abstract P createPresenter();

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    public BaseActivity getContentActivity(){
        return ((BaseActivity) getActivity()).getBaseActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (presenter == null)
            presenter = createPresenter();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // LoaderCallbacks as an object, so no hint regarding loader will be leak to the subclasses.
        getLoaderManager().initLoader(loaderId(), null, new LoaderManager.LoaderCallbacks<P>() {
            @Override
            public final Loader<P> onCreateLoader(int id, Bundle args) {
                Log.i(TAG, "onCreateLoader-" + tag());
                return new PresenterLoader<>(getContext(), createPresenter(), tag());
            }

            @Override
            public final void onLoadFinished(Loader<P> loader, P presenter) {
                Log.i(TAG, "onLoadFinished-" + tag());
                BaseFragment.this.presenter = presenter;
                onPresenterPrepared(presenter);
            }

            @Override
            public final void onLoaderReset(Loader<P> loader) {
                Log.i(TAG, "onLoaderReset-" + tag());
                BaseFragment.this.presenter = null;
                onPresenterDestroyed();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        if(getPresenter()!=null)
            getPresenter().onDestroy();
        super.onDestroy();
    }

    @NonNull
    protected abstract String tag();
    protected abstract int loaderId();
    protected abstract void onPresenterPrepared(@NonNull P presenter);
    protected void onPresenterDestroyed() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
