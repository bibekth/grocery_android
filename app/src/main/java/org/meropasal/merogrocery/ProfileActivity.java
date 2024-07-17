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
    TextView tvLogout;
    String token, bearerToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvLogout = findViewById(R.id.tvLogout);

        Intent loginIntent = new Intent(this,LoginActivity.class);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                token = TokenManager.getToken(getApplicationContext());
                bearerToken = "Bearer " + token;
                Logout logout = RetrofitService.getService(ProfileActivity.this).create(Logout.class);

                Call<UserModel> call = logout.postLogout(bearerToken);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            TokenManager.clearToken(getApplicationContext());
                            startActivity(loginIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {

                    }
                });
            }
        });

    }
}