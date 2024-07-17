package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetOTP {
    @POST("/api/signup")
    Call<UserModel> generateOTP(@Body UserModel userModel);
}
