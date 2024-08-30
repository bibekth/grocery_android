package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.AllCustomerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetAllCustomer {
    @GET("/api/customerlist")
    Call<AllCustomerModel> getCustomers(@Header("Authorization") String token);
}
