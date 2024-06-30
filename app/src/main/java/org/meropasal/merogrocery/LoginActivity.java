    package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.meropasal.merogrocery.model.Mock;
import org.meropasal.merogrocery.services.LoginPost;
import org.meropasal.merogrocery.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;

    public class LoginActivity extends AppCompatActivity {

    TextView tvClickHere,tvAppName;
    Button btnLogin;
    String id;
    EditText etPhoneNumber, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvClickHere = findViewById(R.id.tvClickHere);
        Intent clickHereIntent = new Intent(this, GenerateOTP.class);

        tvClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(clickHereIntent);
            }
        });

        tvAppName = findViewById(R.id.tvAppName);

        tvAppName.setTypeface(null, Typeface.BOLD);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        String stPhoneNumber = etPhoneNumber.getText().toString();
        String stPassword = etPassword.getText().toString();
        id = stPhoneNumber;
        LoginPost loginPost = RetrofitClient.getClient().create(LoginPost.class);
        Call<Body> call = loginPost.getToken(id);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mock mock = new Mock("id");


                call.enqueue(new Callback<Body>() {
                    @Override
                    public void onResponse(Call<Body> call, Response<Body> response) {
                        Log.i("apiresponse",response.toString());
                        if(response.isSuccessful()){

//                            Log.e("apiresponse",response.toString());
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Body> call, Throwable throwable) {
                        Log.e("apiresponse",throwable.getMessage());
                        Toast.makeText(LoginActivity.this, "Failed to connect to server.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}