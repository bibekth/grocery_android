package org.meropasal.merogrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.meropasal.merogrocery.model.ProductPriceModel;
import org.meropasal.merogrocery.model.ProductVariantModel;
import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.recycler.ProductPriceAdapter;
import org.meropasal.merogrocery.recycler.ProductVariantAdapter;
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

    String token, bearerToken, stProduct, stVariant, stQuantity, stPrice, productID, variantID, updateProductID;
    ProductPriceAdapter productPriceAdapter;
    ProductVariantAdapter productAdapter, variantAdapter;
    RecyclerView rcProductPrice, rcProductName, rcVariantName;
    ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices;
    ArrayList<ProductVariantModel.Products> arrProductNames;
    ArrayList<ProductVariantModel.Variants> arrVariantNames;
    ImageView ivAddProductIcon, ivUpdateProductIcon, ivCrossAddProductVariantToSystem, ivCrossUpdate;
    View llAddProductSection, llUpdateProductSection, llAddProductVariantToSystem, llAddProductToSystem, llAddVariantToSystem, llDefault;
    Button btnSave, btnUpdate, btnAddVariantToSystem, btnAddProductToSystem;
    Product productServiceAPI;
    EditText etAddProduct, etAddVariant, etAddQuantity, etAddPrice, etUpdateQuantity, etUpdatePrice, etAddProductToSystem, etAddVariantCodeToSystem, etAddVariantToSystem;
    TextView tvUpdateProductName, tvUpdateVariant, tvClickHere;
    CheckBox cbProduct, cbVariant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_price);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer "+token;

        productServiceAPI = RetrofitService.getService(ProductPriceActivity.this).create(Product.class);

        findView();
        fetchProductPrices();
        toggleAddProductSection();
        saveProduct();
        updateProduct();
    }

    private void recyclerSection(){
        // Recycler View Section
        rcProductPrice = findViewById(R.id.rcProductPrice);
        arrProductPrices = new ArrayList<>();
        productPriceAdapter = new ProductPriceAdapter(getApplicationContext(), arrProductPrices);
        rcProductPrice.setLayoutManager(new LinearLayoutManager(this));
        rcProductPrice.setAdapter(productPriceAdapter);
        productPriceAdapter.setOnItemClickListener(new ProductPriceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int productID, String name, String variant, String quantity) {
                String updateText = "Update price of " + name + " of "+quantity + " " + variant;
                tvUpdateProductName.setText(updateText);
                updateProductID = String.valueOf(productID);
                if (llUpdateProductSection.getVisibility() == View.GONE) {
                    llUpdateProductSection.setVisibility(View.VISIBLE);
                } else {
                    llUpdateProductSection.setVisibility(View.GONE);
                }
            }
        });

        rcProductName = findViewById(R.id.rcProductName);
        arrProductNames = new ArrayList<>();
        productAdapter = new ProductVariantAdapter(getApplicationContext(),arrProductNames, null);
        rcProductName.setLayoutManager(new LinearLayoutManager(this));
        rcProductName.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new ProductVariantAdapter.OnItemClickListener()    {
            @Override
            public void onItemClick(int id, String name, String code) {
                etAddProduct.setText(name);
                rcProductName .setVisibility(View.GONE);
                etAddVariant.requestFocus();
                productID = String.valueOf(id);
            }
        });


        rcVariantName = findViewById(R.id.rcVariantName);
        arrVariantNames = new ArrayList<>();
        variantAdapter = new ProductVariantAdapter(getApplicationContext(), null, arrVariantNames);
        rcVariantName.setLayoutManager(new LinearLayoutManager(this));
        rcVariantName.setAdapter(variantAdapter);
        variantAdapter.setOnItemClickListener(new ProductVariantAdapter.OnItemClickListener()    {
            @Override
            public void onItemClick(int id, String name, String code) {
                String setName = name + " (" + code + ")";
                etAddVariant.setText(setName);
                rcVariantName .setVisibility(View.GONE);
                etAddQuantity.requestFocus();
                variantID = String.valueOf(id);
            }
        });

    }

    private void findView() {
        etAddProduct = findViewById(R.id.etAddProductName);
        etAddPrice = findViewById(R.id.etAddPrice);
        etAddQuantity = findViewById(R.id.etAddQuantity);
        etAddVariant = findViewById(R.id.etAddVariantName);
        llAddProductToSystem = findViewById(R.id.llAddProductToSystem);
        llAddVariantToSystem = findViewById(R.id.llAddVariantToSystem);
        llAddProductVariantToSystem = findViewById(R.id.llAddProductVariantToSystem);
        tvClickHere = findViewById(R.id.tvClickHere);
        cbProduct = findViewById(R.id.cbProduct);
        cbVariant = findViewById(R.id.cbVariant);
        llDefault = findViewById(R.id.llDefault);
        ivCrossAddProductVariantToSystem = findViewById(R.id.ivCrossAddProductVariantToSystem);
        ivCrossUpdate = findViewById(R.id.ivCrossUpdate);
        ivAddProductIcon = findViewById(R.id.ivAddProductIcon);
        llAddProductSection = findViewById(R.id.llAddProductSection);
        llUpdateProductSection = findViewById(R.id.llUpdateSection);
        etUpdatePrice = findViewById(R.id.etAmount);
        tvUpdateProductName = findViewById(R.id.tvUpdateProductName);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnAddProductToSystem = findViewById(R.id.btnAddProductToSystem);
        btnAddVariantToSystem = findViewById(R.id.btnAddVariantToSystem);
        etAddVariantToSystem = findViewById(R.id.etAddVariantToSystem);
        etAddVariantCodeToSystem = findViewById(R.id.etAddVariantCodeToSystem);
        etAddProductToSystem = findViewById(R.id.etAddProductToSystem);
    }

    private void saveProduct() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stQuantity = etAddQuantity.getText().toString();
                stPrice = etAddPrice.getText().toString();
                Product productService = RetrofitService.getService(ProductPriceActivity.this).create(Product.class);
                Call<ProductPriceModel.RequiredParam> call = productService.setProductPrice(bearerToken, new ProductPriceModel.RequiredParam(productID, variantID,stQuantity, stPrice));

                call.enqueue(new Callback<ProductPriceModel.RequiredParam>() {
                    @Override
                    public void onResponse(Call<ProductPriceModel.RequiredParam> call, Response<ProductPriceModel.RequiredParam> response) {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            String status = response.body().getStatus();
                            if(Objects.equals(status, "Success")){
                                fetchProductPrices();
                                Toast.makeText(ProductPriceActivity.this, "Product set successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProductPriceActivity.this, "Product set failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductPriceModel.RequiredParam> call, Throwable throwable) {

                    }
                });

                llAddProductSection.setVisibility(View.GONE);
            }
        });
    }

    private void updateProduct() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatePrice = etUpdatePrice.getText().toString();

                Product productService = RetrofitService.getService(ProductPriceActivity.this).create(Product.class);
                Call<ProductPriceModel> call = productService.updatePrice(bearerToken, updateProductID, updatePrice);

                call.enqueue(new Callback<ProductPriceModel>() {
                    @Override
                    public void onResponse(Call<ProductPriceModel> call, Response<ProductPriceModel> response) {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            String status = response.body().getStatus();

                            if(Objects.equals(status, "Success")){
                                fetchProductPrices();
                                Toast.makeText(ProductPriceActivity.this, "Price updated successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProductPriceActivity.this, "Price update failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductPriceModel> call, Throwable throwable) {

                    }
                });
                etUpdatePrice.setText("");
                llUpdateProductSection.setVisibility(View.GONE);
            }
        });
    }


    private void toggleAddProductSection() {
        ivAddProductIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llAddProductSection.getVisibility() == View.GONE){
                    llDefault.setVisibility(View.GONE);
                    llAddProductSection.setVisibility(View.VISIBLE);
                    etAddProduct.requestFocus();
                }else{
                    llDefault.setVisibility(View.VISIBLE);
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

    private void fetchAllProductsAndVariants(){
        Product productService = RetrofitService.getService(ProductPriceActivity.this).create(Product.class);
        Call<ProductVariantModel> call = productService.getAllProducts(bearerToken);

        call.enqueue(new Callback<ProductVariantModel>() {
            @Override
            public void onResponse(Call<ProductVariantModel> call, Response<ProductVariantModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    ArrayList<ProductVariantModel.Products> allProducts = response.body().getProducts();
                    ArrayList<ProductVariantModel.Variants> allVariants = response.body().getVariants();

                    arrProductNames.addAll(allProducts);
                    arrVariantNames.addAll(allVariants);

                    productAdapter.notifyDataSetChanged();
                    variantAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductVariantModel> call, Throwable throwable) {

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
        fetchAllProductsAndVariants();
        SearchProductVariant();
        recyclerSection();
        toggleAddProductVariantToSystem();
        aboutFocus();
        addProductTOSystem();
        addVariantToSystem();
        responseToCross();
        sendProductList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void SearchProductVariant(){
        etAddProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                productAdapter.initialSetProduct();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rcProductName.setVisibility(View.VISIBLE);
                productAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etAddVariant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                variantAdapter.initialSetVariant();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rcProductName.setVisibility(View.GONE);
                rcVariantName.setVisibility(View.VISIBLE);
                variantAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addProductTOSystem(){
        btnAddProductToSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etAddProductToSystem.getText().toString();
                Call<UserModel> call = productServiceAPI.addProduct(bearerToken, name);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                        if(response.isSuccessful()){
                            UserModel modelResponse = response.body();
                            assert modelResponse != null;
                            if(Objects.equals(modelResponse.getStatus(), "Success")){
                                Toast.makeText(ProductPriceActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProductPriceActivity.this, modelResponse.getStatus(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ProductPriceActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {
                    }
                });

                llAddProductVariantToSystem.setVisibility(View.GONE);
                llAddProductSection.setVisibility(View.GONE);
                llDefault.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addVariantToSystem(){
        btnAddVariantToSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etAddVariantToSystem.getText().toString();
                String code = etAddVariantCodeToSystem.getText().toString();

                Call<UserModel> call = productServiceAPI.addVariant(bearerToken, name, code);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            Toast.makeText(ProductPriceActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProductPriceActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {
                    }
                });

                llAddProductVariantToSystem.setVisibility(View.GONE);
                llAddProductSection.setVisibility(View.GONE);
                llDefault.setVisibility(View.VISIBLE);
            }
        });
    }

    private void toggleAddProductVariantToSystem(){
        tvClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAddProductVariantToSystem.setVisibility(View.VISIBLE);
            }
        });

        cbProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbProduct.isChecked()) {
                    cbVariant.setChecked(false);
                    llAddProductToSystem.setVisibility(View.VISIBLE);
                }else{
                    llAddProductToSystem.setVisibility(View.GONE);
                }
                llAddVariantToSystem.setVisibility(View.GONE);
            }
        });

        cbVariant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbVariant.isChecked()){
                    cbProduct.setChecked(false);
                    llAddVariantToSystem.setVisibility(View.VISIBLE);
                }else{
                    llAddVariantToSystem.setVisibility(View.GONE);
                }
                llAddProductToSystem.setVisibility(View.GONE);
            }
        });
    }

    private void aboutFocus(){
        etAddProduct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    rcProductName.setVisibility(View.GONE);
                } else {
                    rcProductName.setVisibility(View.VISIBLE);
                }
            }
        });

        etAddVariant.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    rcVariantName.setVisibility(View.GONE);
                } else {
                    rcVariantName.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void responseToCross(){
        ivCrossAddProductVariantToSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAddProductVariantToSystem.setVisibility(View.GONE);
                llAddProductSection.setVisibility(View.VISIBLE);
            }
        });

        ivCrossUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llUpdateProductSection.setVisibility(View.GONE);
                llDefault.setVisibility(View.VISIBLE);
            }
        });
    }

    public ArrayList<ProductPriceModel.Message.ProductPrices> sendProductList(){
        return arrProductPrices;
    }


}