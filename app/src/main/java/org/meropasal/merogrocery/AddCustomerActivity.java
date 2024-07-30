package org.meropasal.merogrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.meropasal.merogrocery.model.AllCustomerModel;
import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.recycler.AllCustomerFilterAdapter;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.AssignCustomer;
import org.meropasal.merogrocery.service.GetAllCustomer;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerActivity extends AppCompatActivity {
    Button btnSave;
    EditText etAmount, etCustomerName, etPhoneNumber;
    String token, bearerToken, stPhoneNumber, stAmount, stCustomerName, customerID;
    ArrayList<AllCustomerModel.Data> customers;
    AllCustomerFilterAdapter adapter;
    TextView tvRegister;
    AutoCompleteTextView phoneNumberAutoComplete;
    RecyclerView rcAddCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        btnSave = findViewById(R.id.btnSave);
        etAmount = findViewById(R.id.etAmount);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etCustomerName = findViewById(R.id.etCustomerName);
        rcAddCustomer = findViewById(R.id.recyclerView);
        tvRegister = findViewById(R.id.tvRegister);

        customers = new ArrayList<>();
        rcAddCustomer.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllCustomerFilterAdapter(getApplicationContext(),customers);
        rcAddCustomer.setAdapter(adapter);
        adapter.setOnItemClickListener(new AllCustomerFilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Integer customerId, String phoneNumber) {
                etPhoneNumber.setText(phoneNumber);
                rcAddCustomer.setVisibility(View.GONE);
                etCustomerName.requestFocus();
                customerID = String.valueOf(customerId);
            }
        });

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;
        Log.v("testtt","helllo");
        searchFilter();
        RecyclerToggle();
        btnSaveClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAllCustomers();
        clickRegister();
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

    private void searchFilter(){
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rcAddCustomer.setVisibility(View.VISIBLE);
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void fetchAllCustomers(){
        GetAllCustomer getAllCustomer = RetrofitService.getService(AddCustomerActivity.this).create(GetAllCustomer.class);
        Call<AllCustomerModel> call = getAllCustomer.getCustomers(bearerToken);

        call.enqueue(new Callback<AllCustomerModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<AllCustomerModel> call, @NonNull Response<AllCustomerModel> response) {
                if(response.isSuccessful()){
                    AllCustomerModel allCustomerModelResponse = response.body();
                    assert allCustomerModelResponse != null;
                    String status = allCustomerModelResponse.getStatus();
                    if(Objects.equals(status, "Success")){
                        List<AllCustomerModel.Data> dataResponse = allCustomerModelResponse.getData();
                        assert dataResponse != null;
                        customers.addAll(dataResponse);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<AllCustomerModel> call, Throwable throwable) {

            }
        });
    }
    private void btnSaveClick(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stPhoneNumber = etPhoneNumber.getText().toString();
                String amount = etAmount.getText().toString();
                stCustomerName = etCustomerName.getText().toString();

                if(amount.isEmpty()){
                    stAmount = "0";
                }else{
                    stAmount = amount;
                }

                AssignCustomer assignCustomer = RetrofitService.getService(AddCustomerActivity.this).create(AssignCustomer.class);
                Call<UserModel.User> call = assignCustomer.assignCustomer(bearerToken, customerID, stCustomerName, stAmount);
                Log.v("testtt",customerID);
                call.enqueue(new Callback<UserModel.User>() {
                    @Override
                    public void onResponse(Call<UserModel.User> call, Response<UserModel.User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AddCustomerActivity.this, "Customer assigned successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel.User> call, Throwable throwable) {

                    }
                });
            }
        });
    }

    private void RecyclerToggle(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcAddCustomer.setVisibility(View.GONE);
            }
        };
        etCustomerName.setOnClickListener(onClickListener);
        etAmount.setOnClickListener(onClickListener);
    }

    private void clickRegister(){
        tvRegister.setOnClickListener(v -> showAddCustomerDialog());
    }

    private void showAddCustomerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddCustomerActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_customer_dialog, null);
        builder.setView(dialogView);

        EditText etAddPhoneNumber = dialogView.findViewById(R.id.etPhoneNumber);
        EditText etAddAssignedName = dialogView.findViewById(R.id.etAssignedName);
        EditText etAddAmount = dialogView.findViewById(R.id.etAmount);
        Button btnAddSave = dialogView.findViewById(R.id.btnSave);

        AlertDialog dialog = builder.create();

        btnAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stAddPhoneNumber, stAddAssignedName, stAddAmount;
                stAddPhoneNumber = etAddPhoneNumber.getText().toString();
                stAddAssignedName = etAddAssignedName.getText().toString();
                stAddAmount = etAddAmount.getText().toString();

                AssignCustomer assignCustomer = RetrofitService.getService(AddCustomerActivity.this).create(AssignCustomer.class);
                UserModel.User user = new UserModel.User(null, null, null, null, stAddPhoneNumber, stAddAmount, stAddAssignedName);

                Call<UserModel.User> call = assignCustomer.postCustomer(bearerToken, user);

                call.enqueue(new Callback<UserModel.User>() {
                    @Override
                    public void onResponse(Call<UserModel.User> call, Response<UserModel.User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AddCustomerActivity.this, "Customer has been assigned successfully.", Toast.LENGTH_SHORT).show();
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel.User> call, Throwable throwable) {

                    }
                });
            }
        });

        dialog.show();
    }
}