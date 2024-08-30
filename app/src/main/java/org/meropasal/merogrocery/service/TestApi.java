package org.meropasal.merogrocery.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TestApi {
    @GET("/api/")
    Call<ResponseBody> getMessage();
}
