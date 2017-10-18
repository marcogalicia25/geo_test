package com.meltwater.marcogalicia.osmdroid;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by marcogalicia on 15/07/17.
 */

public interface EndPoints {

    @GET("/test")
    Call<Documents> getDocuments();

}
