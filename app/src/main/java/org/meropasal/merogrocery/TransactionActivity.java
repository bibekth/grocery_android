package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.meropasal.merogrocery.model.ProductPriceModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;
import org.meropasal.merogrocery.recycler.FilterCustomerForPaymentAdapter;
import org.meropasal.merogrocery.recycler.ProductPriceAdapter;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Product;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionActivity extends AppCompatActivity {
    EditText etCustomer, etProduct;
    String token, bearerToken, customerID;
    RecyclerView recyclerView, rcProductPrice;
    ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices;
    FilterCustomerForPaymentAdapter adapter;
    ProductPriceAdapter productPriceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;

        find();
        toggleFocus();
        fetchData();
    }

    protected void fetchData(){
        ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrUserModel = getIntent().getParcelableArrayListExtra("customerArray");
        assert arrUserModel != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilterCustomerForPaymentAdapter(getApplicationContext(),arrUserModel);
        recyclerView.setAdapter(adapter);

        arrProductPrices = new ArrayList<>();
        productPriceAdapter = new ProductPriceAdapter(getApplicationContext(), arrProductPrices);
        rcProductPrice.setLayoutManager(new LinearLayoutManager(this));
        rcProductPrice.setAdapter(productPriceAdapter);

        Product product = RetrofitService.getService(TransactionActivity.this).create(Product.class);
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

    protected void find(){
        etCustomer = findViewById(R.id.etCustomer);
        recyclerView = findViewById(R.id.recyclerView);
        etProduct = findViewById(R.id.etProduct);
        rcProductPrice = findViewById(R.id.rcProductPrice);
    }

    protected void searchFilter(){
        etCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected void toggleFocus(){
        etCustomer.requestFocus();
        if(etCustomer.hasFocus()){
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.GONE);
        }
        etCustomer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
        if(etProduct.hasFocus()){
            rcProductPrice.setVisibility(View.VISIBLE);
        }else{
            rcProductPrice.setVisibility(View.GONE);
        }
        etProduct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    rcProductPrice.setVisibility(View.VISIBLE);
                }else{
                    rcProductPrice.setVisibility(View.GONE);
                }
            }
        });

    }

    protected void selectCustomer(){
        adapter.setOnItemClickListener(new FilterCustomerForPaymentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int customerId, String phoneNumber, String name) {
                String setName = name + " (" + phoneNumber + ")";
                etCustomer.setText(setName);
                recyclerView.setVisibility(View.GONE);
                customerID = String.valueOf(customerId);
                etProduct.requestFocus();
            }
        });

        productPriceAdapter.setOnItemClickListener(new ProductPriceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int productID, String name, String variant, String quantity) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchFilter();
        selectCustomer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}