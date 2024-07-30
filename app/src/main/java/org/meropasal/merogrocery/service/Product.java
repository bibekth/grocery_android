package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.ProductPriceModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Product {
    @GET("/api/productprice")
    Call<ProductPriceModel> getProductPrices(@Header("Authorization") String token);

    @POST("/api/set-product-price")
    Call<ProductPriceModel.RequiredParam> setProductPrice(@Header("Authorization") String token, @Body ProductPriceModel.RequiredParam requiredParam);
}
