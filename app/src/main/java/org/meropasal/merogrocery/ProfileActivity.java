package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Logout;
import org.meropasal.merogrocery.utility.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView tvLogout, tvAddCustomer;
    String token, bearerToken;
    Intent intentTvLogout, intentTvAddCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;

        tvLogout = findViewById(R.id.tvLogout);
        tvAddCustomer = findViewById(R.id.tvAddCustomer);

        intentTvLogout = new Intent(this,LoginActivity.class);
        intentTvAddCustomer = new Intent(this, ListCustomerActivity.class);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout logout = RetrofitService.getService(ProfileActivity.this).create(Logout.class);
                Call<UserModel> call = logout.postLogout(bearerToken);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            TokenManager.clearToken(getApplicationContext());
                            TokenManager.clearRole(getApplicationContext());
                            startActivity(intentTvLogout);
                        }
                    }
                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {

                    }
                });
            }
        });

        tvAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentTvAddCustomer);
            }
        });
    }
}