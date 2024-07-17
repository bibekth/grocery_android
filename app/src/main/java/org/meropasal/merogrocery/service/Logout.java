package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Logout {
    @POST("/api/logout")
    Call<UserModel> postLogout(@Header("Authorization") String token);
}
