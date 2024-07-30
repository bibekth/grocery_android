package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AssignCustomer {
    @POST("/api/customer")
    Call<UserModel.User> postCustomer(@Header("Authorization") String token, @Body UserModel.User user);

    @POST("/api/assign-customer")
    Call<UserModel.User> assignCustomer(@Header("Authorization") String token, @Query("customer_id") String customerID, @Query("assigned_name") String assignedName, @Query("amount") String amount);
}
