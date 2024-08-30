package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Profile {
    @GET("/api/profile")
    Call<UserModel> getProfile(@Header("Authorization") String token);
}
