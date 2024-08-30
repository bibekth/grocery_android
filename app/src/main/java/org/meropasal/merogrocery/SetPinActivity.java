package org.meropasal.merogrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.SetPIN;
import org.meropasal.merogrocery.utility.MakeAppNameBolder;
import org.meropasal.merogrocery.utility.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPinActivity extends AppCompatActivity {

    TextView tvAppName;
    EditText etPIN, etRePIN;
    Button btnSave;
    String stPIN, stRePIN, token, bearerToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        Intent saveIntent = new Intent(this, MainActivity.class);

        tvAppName = findViewById(R.id.tvAppName);
        MakeAppNameBolder.makeBolder(this,tvAppName);

        etPIN = findViewById(R.id.etFourDigitPin);
        etRePIN = findViewById(R.id.etReEnterPin);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stPIN = etPIN.getText().toString();
                stRePIN = etRePIN.getText().toString();

                SetPIN setPIN = RetrofitService.getService(SetPinActivity.this).create(SetPIN.class);
                token = TokenManager.getToken(getApplicationContext());
                bearerToken = "Bearer " + token;
                UserModel userModel = new UserModel(null,null,null,null,null,null,stPIN,null);
                Call<UserModel> call = setPIN.postPIN(bearerToken,userModel);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                        if(response.isSuccessful()){
                            UserModel userModelResponse = response.body();
                            if(userModelResponse != null){
                                TokenManager.clearPhoneNumber(getApplicationContext());
                                startActivity(saveIntent);
                            }
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