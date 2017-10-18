package com.meltwater.marcogalicia.osmdroid;

import android.support.annotation.NonNull;

import com.mswim.architecture.mvp.BaseInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marcogalicia on 10/17/17.
 */

public class DocumentsInteractor {

    private BaseInteractor<Documents> interf;

    public DocumentsInteractor(BaseInteractor<Documents> interf) {
        this.interf = interf;
    }

    public void getDocuments(){
        EndPoints apiService = APIRetrofitClient.getClient().create(EndPoints.class);
        Call<Documents> call = apiService.getDocuments();
        call.enqueue(new Callback<Documents>() {
            @Override
            public void onResponse(@NonNull Call<Documents> call, @NonNull Response<Documents> response) {

                if (response.isSuccessful()) {
                    Documents bodyResponse = response.body();
                    if (bodyResponse != null) {
                        interf.loadDataCallBack(bodyResponse);
                    } else interf.error(0, null);
                } else interf.error(0, null);
            }

            @Override
            public void onFailure(@NonNull Call<Documents> call, @NonNull Throwable t) {
                interf.error(0, t.toString());
            }
        });
    }
}
