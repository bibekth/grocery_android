package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SetPassword {
    @POST("/api/set-password")
    Call<UserModel> setPassword(@Header("Authorization") String token, @Body UserModel userModel);
}
