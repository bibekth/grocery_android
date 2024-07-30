package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetCustomer {
    @GET("/api/customer")
    Call<VendorCustomerRecyclerModel> getCustomers(@Header("Authorization") String token);
}
