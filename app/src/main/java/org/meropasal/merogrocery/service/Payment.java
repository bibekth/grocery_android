package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Payment {
    @POST("/api/payment")
    Call<UserModel> postPayment(@Header("Authorization") String token, @Query("customer_id") String customer_id, @Query("amount") String amount);
}
