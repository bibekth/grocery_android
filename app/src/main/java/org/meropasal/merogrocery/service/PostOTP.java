package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostOTP {
    @POST("/api/tryotp")
    Call<UserModel> postOTP(@Body UserModel userModel);
}
