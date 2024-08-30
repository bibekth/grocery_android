package org.meropasal.merogrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.PostOTP;
import org.meropasal.merogrocery.utility.MakeAppNameBolder;
import org.meropasal.merogrocery.utility.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTP extends AppCompatActivity {
    TextView tvAppName;
    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    String stOTP1, stOTP2, stOTP3, stOTP4, stOTP5, stOTP6, stOTP, stPhoneNumber, token, role;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        tvAppName = findViewById(R.id.tvAppName);
        MakeAppNameBolder.makeBolder(this,tvAppName);

        tvAppName.setTypeface(null, Typeface.BOLD);

        Intent sendOTP = new Intent(VerifyOTP.this, SetPasswordActivity.class);
        otp1 = findViewById(R.id.etOTP1);
        otp2 = findViewById(R.id.etOTP2);
        otp3 = findViewById(R.id.etOTP3);
        otp4 = findViewById(R.id.etOTP4);
        otp5 = findViewById(R.id.etOTP5);
        otp6 = findViewById(R.id.etOTP6);
        btnSend = findViewById(R.id.btnSend);
        otp1.requestFocus();
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1){
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1){
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1){
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1){
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1){
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSend.setOnClickListener(v -> {
            stOTP1 = otp1.getText().toString();
            stOTP2 = otp2.getText().toString();
            stOTP3 = otp3.getText().toString();
            stOTP4 = otp4.getText().toString();
            stOTP5 = otp5.getText().toString();
            stOTP6 = otp6.getText().toString();

            stOTP = stOTP1+stOTP2+stOTP3+stOTP4+stOTP5+stOTP6;

            stPhoneNumber = TokenManager.getPhoneNumber(getApplicationContext());
            PostOTP postOTP = RetrofitService.getService(VerifyOTP.this).create(PostOTP.class);
            UserModel userModel = new UserModel(null,null,stPhoneNumber,null,stOTP,null,null, null);

            Call<UserModel> call = postOTP.postOTP(userModel);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    if(response.isSuccessful()){
                        UserModel userModelResponse = response.body();
                        if(userModelResponse != null){
                            UserModel.User user = userModelResponse.getUser();
                            try{
                                token = userModelResponse.getToken();
                                role = user.getRole();
                                TokenManager.saveToken(getApplicationContext(), token);
                                TokenManager.saveRole(getApplicationContext(),role);
                                startActivity(sendOTP);
                            }catch (Exception e){

                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable throwable) {
                }
            });
        });
    }
}