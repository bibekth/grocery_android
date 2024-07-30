package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.meropasal.merogrocery.model.ProductPriceModel;
import org.meropasal.merogrocery.recycler.ProductPriceAdapter;
import org.meropasal.merogrocery.recycler.VendorCustomerAdapter;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Product;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPriceActivity extends AppCompatActivity {

    String token, bearerToken, stProduct, stVariant, stQuantity, stPrice;
    ProductPriceAdapter productPriceAdapter;
    RecyclerView rcProductPrice;
    ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices;
    ImageView ivAddProductIcon, ivUpdateProductIcon;
    View llAddProductSection, llUpdateProductSection;
    Button btnSave, btnUpdate;
    EditText etAddProduct, etAddVariant, etAddQuantity, etAddPrice, etUpdateQuantity, etUpdatePrice;
    TextView tvUpdateProduct, tvUpdateVariant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_price);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer "+token;

        ivAddProductIcon = findViewById(R.id.ivAddProductIcon);
        llAddProductSection = findViewById(R.id.llAddProductSection);
        llUpdateProductSection = findViewById(R.id.llUpdateProductSection);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Recycler View Section
        rcProductPrice = findViewById(R.id.rcProductPrice);
        arrProductPrices = new ArrayList<>();
        productPriceAdapter = new ProductPriceAdapter(getApplicationContext(), arrProductPrices);
        rcProductPrice.setLayoutManager(new LinearLayoutManager(this));
        rcProductPrice.setAdapter(productPriceAdapter);
//        productPriceAdapter.setOnItemClickListener(customerId -> {
//            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//            intent.putExtra("CUSTOMER_ID", customerId);
//            startActivity(intent);
//        });

        fetchProductPrices();
        toggleAddProductSection();
        toggleUpdateProductSection();
        saveProduct();
        updateProduct();
        setProductPrice();
    }

    private void setProductPrice() {
        etAddProduct = findViewById(R.id.etAddProductName);
        etAddPrice = findViewById(R.id.etAddPrice);
        etAddQuantity = findViewById(R.id.etAddQuantity);
        etAddVariant = findViewById(R.id.etAddVariantName);


    }

    private void saveProduct() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateProduct() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void toggleUpdateProductSection() {

    }

    private void toggleAddProductSection() {
        ivAddProductIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llAddProductSection.getVisibility() == View.GONE){
                    llAddProductSection.setVisibility(View.VISIBLE);
                }else{
                    llAddProductSection.setVisibility(View.GONE);
                }
            }
        });
    }

    private void fetchProductPrices() {
        Product product = RetrofitService.getService(ProductPriceActivity.this).create(Product.class);
        Call<ProductPriceModel> call = product.getProductPrices(bearerToken);

        call.enqueue(new Callback<ProductPriceModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ProductPriceModel> call, Response<ProductPriceModel> response) {
                if(response.isSuccessful()){
                    ProductPriceModel productPriceModelResponse = response.body();
                    assert productPriceModelResponse != null;
                    if (Objects.equals(productPriceModelResponse.getStatus(), "Error")) {
                        // handle error
                    }

                    List<ProductPriceModel.Message.ProductPrices> productPricesList = productPriceModelResponse.getMessage().getProductPrices();
                    if(productPricesList != null){
                        if (arrProductPrices != null) {
                            arrProductPrices.clear();
                        } else {
                            arrProductPrices = new ArrayList<>();
                        }
                        arrProductPrices.addAll(productPricesList);
                        productPriceAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductPriceModel> call, Throwable throwable) {

            }
        });
    }
}