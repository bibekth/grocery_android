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
import org.meropasal.merogrocery.service.SetPassword;
import org.meropasal.merogrocery.utility.MakeAppNameBolder;
import org.meropasal.merogrocery.utility.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPasswordActivity extends AppCompatActivity {
    TextView tvAppName;
    EditText etPassword, etRePassword;
    Button btnSave;
    String stPassword, stRePassword, token, bearerToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        Intent saveIntent = new Intent(this,SetPinActivity.class);

        tvAppName = findViewById(R.id.tvAppName);
        MakeAppNameBolder.makeBolder(this,tvAppName);

        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stPassword = etPassword.getText().toString();
                stRePassword = etRePassword.getText().toString();
                token = TokenManager.getToken(getApplicationContext());
                bearerToken = "Bearer " + token;
                SetPassword setPassword = RetrofitService.getService(SetPasswordActivity.this).create(SetPassword.class);
                UserModel userModel = new UserModel(null, null, null, stPassword, null, null, null, null);

                Call<UserModel> call = setPassword.setPassword(bearerToken,userModel);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                        if(response.isSuccessful()){
                            UserModel userModelResponse = response.body();
                            if(userModelResponse != null){
                                startActivity(saveIntent);
                            }else{
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