package org.meropasal.merogrocery.service;

import org.meropasal.merogrocery.model.ProductPriceModel;
import org.meropasal.merogrocery.model.ProductVariantModel;
import org.meropasal.merogrocery.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Product {
    @GET("/api/productprice")
    Call<ProductPriceModel> getProductPrices(@Header("Authorization") String token);

    @POST("/api/set-product-price")
    Call<ProductPriceModel.RequiredParam> setProductPrice(@Header("Authorization") String token, @Body ProductPriceModel.RequiredParam requiredParam);

    @GET("/api/product")
    Call<ProductVariantModel> getAllProducts(@Header("Authorization") String token);

    @PATCH("/api/productprice/{id}")
    Call<ProductPriceModel> updatePrice(@Header("Authorization") String token, @Path("id") String id, @Query("price") String price);

    @POST("/api/product")
    Call<UserModel> addProduct(@Header("Authorization") String token, @Query("name") String productName);

    @POST("/api/variant")
    Call<UserModel> addVariant(@Header("Authorization") String token, @Query("name") String name, @Query("variant_code") String variantCode);
}
