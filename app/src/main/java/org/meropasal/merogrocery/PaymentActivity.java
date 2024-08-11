package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;
import org.meropasal.merogrocery.recycler.AllCustomerFilterAdapter;
import org.meropasal.merogrocery.recycler.FilterCustomerForPaymentAdapter;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Payment;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    String token, bearerToken, stPhoneNumber, stAmount, customerID;
    EditText etPhoneNumber, etAmount;
    Button btnSave;
    RecyclerView rcCustomers;
    FilterCustomerForPaymentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;

        etPhoneNumber = findViewById(R.id.etCustomer);
        etAmount = findViewById(R.id.etAmount);
        btnSave = findViewById(R.id.btnSave);
        rcCustomers = findViewById(R.id.recyclerView);

        ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrUserModel = getIntent().getParcelableArrayListExtra("customerArray");
        assert arrUserModel != null;

        rcCustomers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilterCustomerForPaymentAdapter(getApplicationContext(),arrUserModel);
        rcCustomers.setAdapter(adapter);

        adapter.setOnItemClickListener(new FilterCustomerForPaymentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int customerId, String phoneNumber, String name) {
                String setName = name + " (" + phoneNumber + ")";
                etPhoneNumber.setText(setName);
                rcCustomers.setVisibility(View.GONE);
                etAmount.requestFocus();
                customerID = String.valueOf(customerId);
            }
        });

        savePayment();
    }


    private void savePayment(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stPhoneNumber = etPhoneNumber.getText().toString();
                stAmount = etAmount.getText().toString();

                Payment payment = RetrofitService.getService(PaymentActivity.this).create(Payment.class);
                Call<UserModel> call = payment.postPayment(bearerToken, customerID, stAmount);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            UserModel userModelResponse = response.body();
                            assert userModelResponse != null;
                               if(Objects.equals(userModelResponse.getStatus(), "Success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                                builder.setMessage("Payment Successful")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {

                    }
                });
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
        etPhoneNumber.requestFocus();
        rcCustomers.setVisibility(View.VISIBLE);
        searchFilter();

        etPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    rcCustomers.setVisibility(View.GONE);
                } else {
                    rcCustomers.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void searchFilter(){
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}