package org.meropasal.merogrocery;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;
import org.meropasal.merogrocery.recycler.VendorCustomerAdapter;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.GetCustomer;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvAddCustomer, tvAddPayment;
    Intent intentAddCustomer, intentAddPayment, intentSetPrice;
    RecyclerView rcCustomerVendorList;
    ImageView ivProfilePic, ivAddIcon, ivPayReceive;
    String token, bearerToken, role;
    VendorCustomerAdapter vendorCustomerAdapter;
    View llCustomerPayment, llAddCustomer, llAddPayment, llSetPrice;
    ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrUserModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcCustomerVendorList = findViewById(R.id.rcCustomerVendorList);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivAddIcon = findViewById(R.id.ivAddIcon);
        ivPayReceive = findViewById(R.id.ivPayReceive);
        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;
        role = TokenManager.getRole(getApplicationContext());

        arrUserModel = new ArrayList<>();
        vendorCustomerAdapter = new VendorCustomerAdapter(getApplicationContext(), arrUserModel);
        rcCustomerVendorList.setLayoutManager(new LinearLayoutManager(this));
        rcCustomerVendorList.setAdapter(vendorCustomerAdapter);
        vendorCustomerAdapter.setOnItemClickListener(customerId -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("CUSTOMER_ID", customerId);
            startActivity(intent);
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
            }
        });



        if(Objects.equals(role, "Vendor")){
            ivPayReceive.setImageResource(R.drawable.to_receive_icon);
            toggleCustomerPayment();
            clickCustomer();
            clickPayment();
        }else{
            hideAddIcon();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Objects.equals(role, "Vendor")){
            fetchCustomers();
        }
        hideSection();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fetchCustomers(){
        GetCustomer getCustomer = RetrofitService.getService(MainActivity.this).create(GetCustomer.class);
        Call<VendorCustomerRecyclerModel> call = getCustomer.getCustomers(bearerToken);

        call.enqueue(new Callback<VendorCustomerRecyclerModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<VendorCustomerRecyclerModel> call, Response<VendorCustomerRecyclerModel> response) {
                if(response.isSuccessful()){
                    VendorCustomerRecyclerModel vendorCustomerRecyclerModelResponse = response.body();
                    if(vendorCustomerRecyclerModelResponse == null){
                    }else{
                        VendorCustomerRecyclerModel.Message message = vendorCustomerRecyclerModelResponse.getMessage();
                        if(message == null){
                        }else{
                            List<VendorCustomerRecyclerModel.Message.Customer> customer = message.getCustomer();
                            if(customer != null){
                                arrUserModel.clear();
                                arrUserModel.addAll(customer);
                                vendorCustomerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }else{
                }
            }

            @Override
            public void onFailure(Call<VendorCustomerRecyclerModel> call, Throwable throwable) {

            }
        });
    }
    private void hideSection(){
        llCustomerPayment = findViewById(R.id.llCustomerPayment);
        llCustomerPayment.setVisibility(View.GONE);
    }
    private void hideAddIcon(){
        ivAddIcon.setVisibility(View.GONE);
    }

    private void toggleCustomerPayment(){
        ivAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llCustomerPayment.getVisibility() == View.GONE){
                    llCustomerPayment.setVisibility(View.VISIBLE);
                }else{
                    llCustomerPayment.setVisibility(View.GONE);
                }
            }
        });
    }

    private void clickCustomer(){
        tvAddCustomer = findViewById(R.id.tvAddCustomer);
        tvAddPayment = findViewById(R.id.tvAddPayment);
        llAddCustomer = findViewById(R.id.llAddCustomer);
        llAddPayment = findViewById(R.id.llAddPayment);
        llSetPrice = findViewById(R.id.llSetPrice);

        intentSetPrice = new Intent(this, ProductPriceActivity.class);
        intentAddCustomer = new Intent(this, AddCustomerActivity.class);
        intentAddPayment = new Intent(this, PaymentActivity.class);
        llAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentAddCustomer);
            }
        });
        llAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentAddPayment);
            }
        });

        llSetPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentSetPrice);
            }
        });
    }

    private void clickPayment(){}


}