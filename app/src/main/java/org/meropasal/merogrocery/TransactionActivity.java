package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import org.meropasal.merogrocery.model.ProductPriceModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;
import org.meropasal.merogrocery.recycler.FilterCustomerForPaymentAdapter;
import org.meropasal.merogrocery.recycler.ListProductPriceAdapter;
import org.meropasal.merogrocery.recycler.MakeTransactionAdapter;
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
    EditText etCustomer;
    String token, bearerToken, customerID;
    ImageView ivAdd;
    RecyclerView rcCustomer, rcProductPrice, rcBillList;
    ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices;
    ArrayList<ProductPriceModel.postProductPrice> arrPostProductPrices;
    FilterCustomerForPaymentAdapter adapter;
    ListProductPriceAdapter productPriceAdapter;
    MakeTransactionAdapter billListAdapter;
//    ProductPriceAdapter billListAdapter;
    ArrayList<ProductPriceModel.Message.ProductPrices> billList;
//    List<ProductPriceModel.RequiredParam> billList = new ArrayList<>();
    Button btnSave;
    CheckBox cbCash, cbCredit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;
        billList = new ArrayList<>();

        arrPostProductPrices = new ArrayList<>();
        find();
        toggleFocus();
        fetchData();

//        billListAdapter = new ProductPriceAdapter(getApplicationContext(), billList);

    }

    protected void fetchData(){
        ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrUserModel = getIntent().getParcelableArrayListExtra("customerArray");
        assert arrUserModel != null;
        rcCustomer.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilterCustomerForPaymentAdapter(getApplicationContext(),arrUserModel);
        rcCustomer.setAdapter(adapter);
    }

    protected void find(){
        etCustomer = findViewById(R.id.etCustomer);
        rcCustomer = findViewById(R.id.rcCustomer);
        rcProductPrice = findViewById(R.id.rcProductPrice);
        ivAdd = findViewById(R.id.ivAdd);
        btnSave = findViewById(R.id.btnSave);
        cbCash = findViewById(R.id.cbCash);
        cbCredit = findViewById(R.id.cbCredit);
        rcBillList = findViewById(R.id.rcBillList);
    }

    protected void addItem(){
        ivAdd.setOnClickListener(v -> DialogBox());
    }

    protected void DialogBox(){
        final String[] DialogProductID = new String[1];
        final String[] DialogProduct = new String[1];
        final String[] DialogVariantCode = new String[1];
        final String[] DialogQuantity = new String[1];
        final String[] DialogAllTogether = new String[1];
        final String[] DialogPrice = new String[1];
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(TransactionActivity.this);
        LayoutInflater alertInflater = getLayoutInflater();
        View alertDialogView = alertInflater.inflate(R.layout.add_item_in_purchase_dialog,null);
        alertBuilder.setView(alertDialogView);

        EditText etProductDialog, etAmountDialog;
        RecyclerView rcProductDialog;
        rcProductDialog = alertDialogView.findViewById(R.id.rcProductDialog);
        etProductDialog = alertDialogView.findViewById(R.id.etProductDialog);
        etAmountDialog = alertDialogView.findViewById(R.id.etAmountDialog);
        etAmountDialog.setText("");
        etProductDialog.setText("");
        Button btnSaveDialog = alertDialogView.findViewById(R.id.btnSaveDialog);
        AlertDialog dialog = alertBuilder.create();

        arrProductPrices = new ArrayList<>();
        productPriceAdapter = new ListProductPriceAdapter(getApplicationContext(), arrProductPrices);
        rcProductDialog.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
        rcProductDialog.setAdapter(productPriceAdapter);
        productPriceAdapter.setOnItemClickListener(new ListProductPriceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String productID, String name, String variant, String quantity, String price) {
                DialogQuantity[0] = quantity;
                DialogProductID[0] = productID;
                DialogPrice[0] = price;
                DialogProduct[0] = name;
                DialogVariantCode[0] = variant;
                DialogAllTogether[0] = DialogProduct[0] + " (" + DialogQuantity[0] + " " + DialogVariantCode[0] + ") " ;
                etProductDialog.setText(DialogAllTogether[0]);

                rcProductDialog.setVisibility(View.GONE);
                rcBillList.setVisibility(View.VISIBLE);
            }
        });

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
        etProductDialog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productPriceAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etProductDialog.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    rcProductDialog.setVisibility(View.GONE);
                } else {
                    rcProductDialog.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSaveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stProductDialog, stAmountDialog;
                stProductDialog = etProductDialog.getText().toString();
                stAmountDialog = etAmountDialog.getText().toString();

                arrPostProductPrices.add(new ProductPriceModel.postProductPrice(stProductDialog,stAmountDialog,"0"));
//                Log.v("testtt","productID => " + productID +", name => " + name +", variant => "+variant+", quantity => "+quantity+", price => "+price );
                billList.add(new ProductPriceModel.Message.ProductPrices(DialogProductID[0],DialogQuantity[0], DialogProduct[0], DialogVariantCode[0], etAmountDialog.getText().toString(), DialogPrice[0], null, null, null));
                billListAdapter = new MakeTransactionAdapter(getApplicationContext(), billList);
                rcBillList.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
                rcBillList.setAdapter(billListAdapter);
                billListAdapter.notifyDataSetChanged();
                dialog.hide();
            }
        });
        dialog.show();
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
            rcCustomer.setVisibility(View.VISIBLE);
        }else{
            rcCustomer.setVisibility(View.GONE);
        }
        etCustomer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    rcCustomer.setVisibility(View.VISIBLE);
                }else{
                    rcCustomer.setVisibility(View.GONE);
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
                rcCustomer.setVisibility(View.GONE);
                customerID = String.valueOf(customerId);
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
        addItem();
        btnSaveClick();
        checkingOnlyOneCheckbox();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    protected void btnSaveClick(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("testtt",billList.toString());
            }
        });
    }

    protected void checkingOnlyOneCheckbox(){
        cbCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCredit.isChecked()) {
                    cbCredit.setChecked(false);
                }
                cbCash.setChecked(true);
            }
        });
        cbCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbCash.isChecked()){
                    cbCash.setChecked(false);
                }
                cbCredit.setChecked(true);
            }
        });
    }
}