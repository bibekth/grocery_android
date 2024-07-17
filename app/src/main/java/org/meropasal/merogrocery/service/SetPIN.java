package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SetPIN {
    @POST("/api/set-mpin")
    Call<UserModel> postPIN(@Header("Authorization") String token, @Body UserModel userModel);
}
