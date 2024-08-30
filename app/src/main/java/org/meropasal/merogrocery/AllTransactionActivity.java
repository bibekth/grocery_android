package org.meropasal.merogrocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import org.meropasal.merogrocery.model.TransactionModel;
import org.meropasal.merogrocery.recycler.AllTransactionAdapter;
import org.meropasal.merogrocery.retrofit.RetrofitService;
import org.meropasal.merogrocery.service.Transaction;
import org.meropasal.merogrocery.utility.TokenManager;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllTransactionActivity extends AppCompatActivity {
    String role, token, bearerToken;
    RecyclerView rcAllTransaction;
    AllTransactionAdapter allTransactionAdapter;
    TextView tvName;
    ArrayList<TransactionModel.Message.Transaction> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction);

        token = TokenManager.getToken(getApplicationContext());
        bearerToken = "Bearer " + token;
        fetchAllTransaction();
        find();
    }
    protected void fetchAllTransaction(){
        Transaction transactionService = RetrofitService.getService(this).create(Transaction.class);
        Call<TransactionModel> call = transactionService.getAllTransactions(bearerToken);
        call.enqueue(new Callback<TransactionModel>() {
            @Override
            public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                if(response.isSuccessful()){
                    TransactionModel responseBody = response.body();
                    if(responseBody != null && Objects.equals(responseBody.getStatus(), "Success")){
                        ArrayList<TransactionModel.Message.Transaction> transactions = responseBody.getMessage().getTransactionArrayList();
                        arrayList.clear();
                        arrayList.addAll(transactions);
                        rcAllTransaction.setLayoutManager(new LinearLayoutManager(AllTransactionActivity.this));
                        allTransactionAdapter = new AllTransactionAdapter(getApplicationContext(), arrayList);
                        rcAllTransaction.setAdapter(allTransactionAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionModel> call, Throwable throwable) {

            }
        });
    }

    protected void find(){
        tvName = findViewById(R.id.tvName);
        rcAllTransaction = findViewById(R.id.rcAllTransaction);
    }
}