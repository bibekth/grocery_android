package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Login {
    @POST("/api/login")
    Call<UserModel> postLogin(@Body UserModel userModel, @Query("device_id") String device_id, @Query("fcm_id") String fcm_id);
}
