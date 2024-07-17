package org.meropasal.merogrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Toast;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.GetOTP;
import org.meropasal.merogrocery.utility.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateOTP extends AppCompatActivity {
    TextView tvClickHere, tvAppName;
    EditText etPhoneNumber;
    Button btnGenerate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_otp);

        tvClickHere = findViewById(R.id.tvClickHere);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        tvAppName = findViewById(R.id.tvAppName);
        btnGenerate = findViewById(R.id.btnLogin);

        Intent clickHereIntent = new Intent(this, LoginActivity.class);
        Intent generateIntent = new Intent(this, VerifyOTP.class);

        tvClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(clickHereIntent);
            }
        });

        tvAppName.setTypeface(null, Typeface.BOLD);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stPhoneNumber = etPhoneNumber.getText().toString();

                GetOTP getOTP = RetrofitService.getService(GenerateOTP.this).create(GetOTP.class);
                UserModel userModel = new UserModel(null, null,  stPhoneNumber, null, null, null, null, null);

                Call<UserModel> call = getOTP.generateOTP(userModel);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                        if(response.isSuccessful()){
                            UserModel userModelResponse = response.body();
                            if(userModelResponse != null){
                                try{
                                    String otp_code = userModelResponse.getOtp_code();
                                    TokenManager.savePhoneNumber(getApplicationContext(), stPhoneNumber);
                                    startActivity(generateIntent);
                                }catch (Exception e){

                                }
                            }
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable throwable) {

                    }
                });
            }
        });
    }
}