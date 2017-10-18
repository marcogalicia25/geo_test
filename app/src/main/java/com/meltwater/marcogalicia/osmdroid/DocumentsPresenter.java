package com.meltwater.marcogalicia.osmdroid;

import com.mswim.architecture.mvp.BaseInteractor;
import com.mswim.architecture.mvp.BasePresenter;

/**
 * Created by marcogalicia on 10/17/17.
 */

public class DocumentsPresenter extends BasePresenter<DocumentsView> implements BaseInteractor<Documents> {

    private DocumentsInteractor interactor;

    public DocumentsPresenter() {
        interactor = new DocumentsInteractor(this);
    }

    public void getDocuments() {
        interactor.getDocuments();
    }

    @Override
    public void loadDataCallBack(Documents data) {
        if (isViewAttached()) {
            getView().showLoading(true);
            getView().setData(data);
        }
    }

    @Override
    public void error(int error, String errorString) {

    }
}
