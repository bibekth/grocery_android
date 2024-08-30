    package org.meropasal.merogrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Login;
import org.meropasal.merogrocery.utility.DeviceIDManager;
import org.meropasal.merogrocery.utility.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.firebase.FirebaseApp;

    public class LoginActivity extends AppCompatActivity {
    TextView tvClickHere,tvAppName;
    Button btnLogin;
    EditText etPhoneNumber, etPassword;
    String fcm_id, device_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        String token = TokenManager.getToken(getApplicationContext());
        if(token != null){
            Intent tokenIntent = new Intent(this, MainActivity.class);
            startActivity(tokenIntent);
        }

        device_id = DeviceIDManager.getDeviceId(getApplicationContext());
        fcm_id = DeviceIDManager.getFCMToken();
//        if(fcm_id == null){
//            fcm_id = "12345678";
//        }
        tvClickHere = findViewById(R.id.tvClickHere);

        Intent clickHereIntent = new Intent(this, GenerateOTP.class);
        Intent loginIntent = new Intent(this, MainActivity.class);

        tvClickHere.setOnClickListener(v -> startActivity(clickHereIntent));

        tvAppName = findViewById(R.id.tvAppName);

        tvAppName.setTypeface(null, Typeface.BOLD);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String stPhoneNumber = etPhoneNumber.getText().toString();
            String stPassword = etPassword.getText().toString();

            Login loginService = RetrofitService.getService(LoginActivity.this).create(Login.class);
            UserModel userModel = new UserModel(null, null, stPhoneNumber, stPassword, null, null, null, null);

            Call<UserModel> call = loginService.postLogin(userModel, device_id, fcm_id);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        UserModel userModelResponse = response.body();
                        if (userModelResponse != null) {
                            UserModel.User user = userModelResponse.getUser();
                            try {
                                String token = userModelResponse.getToken();
                                String role = user.getRole();
                                TokenManager.saveToken(getApplicationContext(), token);
                                TokenManager.saveRole(getApplicationContext(),role);

                                startActivity(loginIntent);
                            }catch (Exception e){
                                Toast.makeText(LoginActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                }
            });
        });
    }

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onResume() {
            super.onResume();
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

    }