package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Payment;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    String token, bearerToken, stPhoneNumber, stAmount, stDate;
    EditText etPhoneNumber, etAmount;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;

        etPhoneNumber = findViewById(R.id.etCustomer);
        etAmount = findViewById(R.id.etAmount);
        btnSave = findViewById(R.id.btnSave);

        savePayment();
    }


    private void savePayment(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stPhoneNumber = etPhoneNumber.getText().toString();
                stAmount = etAmount.getText().toString();
                Toast.makeText(PaymentActivity.this, stPhoneNumber, Toast.LENGTH_SHORT).show();

                Payment payment = RetrofitService.getService(PaymentActivity.this).create(Payment.class);
                Call<UserModel> call = payment.postPayment(bearerToken, stPhoneNumber, stAmount);

                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()){
                            UserModel userModelResponse = response.body();
                            assert userModelResponse != null;
                               if(Objects.equals(userModelResponse.getStatus(), "Success")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                                builder.setMessage("Payment Successful")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
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