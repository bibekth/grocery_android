package org.meropasal.merogrocery;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    TextView tvAddCustomer, tvAddPayment, tvTotalCredit, tvTodayTotal, tvCreditText, tvTodayText;
    EditText etSearch;
    Intent intentAddCustomer, intentAddPayment, intentSetPrice;
    RecyclerView rcCustomerVendorList;
    ImageView ivProfilePic, ivAddIcon, ivPayReceive, ivNotification;
    String token, bearerToken, role, stTodayText, stTotalText;
    VendorCustomerAdapter vendorCustomerAdapter;
    View llCustomerPayment, llAddCustomer, llAddPayment, llSetPrice, llMakeTransaction, llMakeDraft, llProgressBar;
    ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrUserModel;
    ArrayList<VendorCustomerRecyclerModel.Message.Vendor> arrVendor;
    ProgressBar progressBar;
    Handler handler = new Handler();
    private boolean DoubleBackPressed = false;
    private final Runnable resetDoubleBackPressed = new Runnable() {
        @Override
        public void run() {
            DoubleBackPressed = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;
        role = TokenManager.getRole(getApplicationContext());

        arrUserModel = new ArrayList<>();
        arrVendor = new ArrayList<>();

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
            stTodayText = "Today's Sales";
            stTotalText = "To Receive";
        }else{
            ivPayReceive.setImageResource(R.drawable.to_receive_icon);
            stTodayText = "Credit Purchase";
            stTotalText = "To Pay";
            hideAddIcon();
        }
        tvTodayText.setText(stTodayText);
        tvCreditText.setText(stTotalText);
    }
    protected void filterCustomerVendor(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vendorCustomerAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(Objects.equals(role, "Vendor")){
            makeTransaction();
            fetchCustomers();
        }else{
            fetchVendors();
        }
        ivNotificationClick();
        filterCustomerVendor();
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
        if (handler != null) {
            handler.removeCallbacks(resetDoubleBackPressed);
        }
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
                            tvTotalCredit.setText(message.getCreditAmount());
                            tvTodayTotal.setText(message.getTodayTotal());
                            List<VendorCustomerRecyclerModel.Message.Customer> customer = message.getCustomer();
                            if(customer != null){
                                arrUserModel.clear();
                                arrUserModel.addAll(customer);
                                initialAdapter();
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
    private void fetchVendors(){
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
                            tvTotalCredit.setText(message.getCreditAmount());
                            tvTodayTotal.setText(message.getTodayTotal());
                            List<VendorCustomerRecyclerModel.Message.Vendor> customer = message.getVendor();
                            if(customer != null){
                                arrVendor.clear();
                                arrVendor.addAll(customer);
                                vendorCustomerAdapter = new VendorCustomerAdapter(getApplicationContext(), arrUserModel, arrVendor);
                                rcCustomerVendorList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                rcCustomerVendorList.setAdapter(vendorCustomerAdapter);
                                vendorCustomerAdapter.setOnItemClickListener(new VendorCustomerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Integer customerId, String name) {
                                        Intent intent = new Intent(MainActivity.this, CustomerTransactionActivity.class);
                                        intent.putExtra("CUSTOMER_ID", String.valueOf(customerId));
                                        intent.putExtra("customerName", name);
                                        startActivity(intent);
                                    }
                                });
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
                intentAddPayment.putParcelableArrayListExtra("customerArray", arrUserModel);
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
    private void findView(){
        rcCustomerVendorList = findViewById(R.id.rcCustomerVendorList);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivAddIcon = findViewById(R.id.ivAddIcon);
        ivPayReceive = findViewById(R.id.ivPayReceive);
        llMakeTransaction = findViewById(R.id.llMakeTransaction);
        llMakeDraft = findViewById(R.id.llMakeDraft);
        etSearch = findViewById(R.id.etSearch);
        tvAddCustomer = findViewById(R.id.tvAddCustomer);
        tvAddPayment = findViewById(R.id.tvAddPayment);
        llAddCustomer = findViewById(R.id.llAddCustomer);
        llAddPayment = findViewById(R.id.llAddPayment);
        llSetPrice = findViewById(R.id.llSetPrice);
        progressBar = findViewById(R.id.progressBar);
        llProgressBar = findViewById(R.id.llProgressBar);
        tvTodayTotal = findViewById(R.id.tvTodayTotal);
        tvTotalCredit = findViewById(R.id.tvTotalCredit);
        tvCreditText = findViewById(R.id.tvCreditText);
        tvTodayText = findViewById(R.id.tvTodayText);
        ivNotification = findViewById(R.id.ivNotification);
    }
    private void makeTransaction() {
        llMakeTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTransaction = new Intent(MainActivity.this,TransactionActivity.class);
                intentTransaction.putParcelableArrayListExtra("customerArray", arrUserModel);
                startActivity(intentTransaction);
            }
        });

        llMakeDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDraft = new Intent(MainActivity.this,DraftActivity.class);
                startActivity(intentDraft);
            }
        });
    }
    private void displayProgressBar(){
        llProgressBar.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        llProgressBar.setVisibility(View.GONE);
                    }
                },
                3000);
    }
    private void initialAdapter(){
        vendorCustomerAdapter = new VendorCustomerAdapter(getApplicationContext(), arrUserModel, arrVendor);
        rcCustomerVendorList.setLayoutManager(new LinearLayoutManager(this));
        rcCustomerVendorList.setAdapter(vendorCustomerAdapter);
        vendorCustomerAdapter.setOnItemClickListener(new VendorCustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Integer customerId, String name) {
                Intent intent = new Intent(MainActivity.this, CustomerTransactionActivity.class);
                intent.putExtra("CUSTOMER_ID", String.valueOf(customerId));
                intent.putExtra("customerName", name);
                startActivity(intent);
            }
        });
    }
    private void ivNotificationClick(){
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ivNotificationIntent = new Intent(MainActivity.this, AllTransactionActivity.class);
                startActivity(ivNotificationIntent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (DoubleBackPressed) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.DoubleBackPressed = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        handler.postDelayed(resetDoubleBackPressed, 2000);
    }
}