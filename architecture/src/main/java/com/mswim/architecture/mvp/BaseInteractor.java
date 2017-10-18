package com.mswim.architecture.mvp;

/**
 * Created by marcogalicia on 25/10/16.
 */

public interface BaseInteractor<T> {
    void loadDataCallBack(T data);

    void error(int error, String errorString);
}
