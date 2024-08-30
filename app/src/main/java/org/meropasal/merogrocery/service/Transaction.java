package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.TransactionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Transaction {
    @POST("/api/customercredit")
    Call<TransactionModel> postTransaction(@Header("Authorization") String token,@Body TransactionModel billList);

    @GET("/api/customercredit/{id}")
    Call<TransactionModel> getCustomerTransaction(@Header("Authorization") String token, @Path("id") String id);

    @GET("/api/customercredit")
    Call<TransactionModel> getAllTransactions(@Header("Authorization") String token);
}
