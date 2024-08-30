package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.meropasal.merogrocery.model.TransactionModel;
import org.meropasal.merogrocery.recycler.SingleTransactionAdapter;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Transaction;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerTransactionActivity extends AppCompatActivity {
    String token, bearerToken, customerID, customerName;
    RecyclerView rcCustomerTransaction;
    TextView tvName;
    SingleTransactionAdapter singleTransactionAdapter;
    ArrayList<TransactionModel.Message.Transaction> transactionArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transaction);

        find();

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;

        Intent intent = getIntent();
        customerID = intent.getStringExtra("CUSTOMER_ID");
        customerName = intent.getStringExtra("customerName");
        tvName.setText(customerName);

        rcCustomerTransaction.setLayoutManager(new LinearLayoutManager(this));
        fetchTransactions();

    }

    protected void find(){
        rcCustomerTransaction = findViewById(R.id.rcCustomerTransaction);
        tvName = findViewById(R.id.tvName);
    }
    private void fetchTransactions(){
        Transaction transactionService = RetrofitService.getService(this).create(Transaction.class);
        Call<TransactionModel> call = transactionService.getCustomerTransaction(bearerToken, customerID);
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if(response.isSuccessful()){
                    TransactionModel responseTransaction = response.body();
                    ArrayList<TransactionModel.Message.Transaction> arrTransaction = responseTransaction.getMessage().getTransactionArrayList();
                    if(arrTransaction != null){
                        transactionArrayList.clear();
                        transactionArrayList.addAll(arrTransaction);
                        singleTransactionAdapter = new SingleTransactionAdapter(getApplicationContext(), transactionArrayList);
                        rcCustomerTransaction.setAdapter(singleTransactionAdapter);
                        singleTransactionAdapter.setOnItemClickListener(new SingleTransactionAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(String customerID) {

                            }
                        });
                        singleTransactionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable throwable) {
                Toast.makeText(CustomerTransactionActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}