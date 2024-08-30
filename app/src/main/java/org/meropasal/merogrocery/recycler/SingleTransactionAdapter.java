package org.meropasal.merogrocery.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.ProductPriceModel;
import org.meropasal.merogrocery.model.TransactionModel;

import java.util.ArrayList;

public class SingleTransactionAdapter extends RecyclerView.Adapter<SingleTransactionAdapter.ViewHolder> {
    Context context;
    ArrayList<TransactionModel.Message.Transaction> originalList;
    ArrayList<TransactionModel.Message.Transaction> filteredList;
    private SingleTransactionAdapter.OnItemClickListener onItemClickListener;

    public SingleTransactionAdapter() {
    }

    public SingleTransactionAdapter(Context context, ArrayList<TransactionModel.Message.Transaction> originalList) {
        this.context = context;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>(originalList);
        Log.v("testtt",String.valueOf(originalList.size()));
    }

    @NonNull
    @Override
    public SingleTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_single_transaction,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleTransactionAdapter.ViewHolder holder, int position) {
        TransactionModel.Message.Transaction currentData = originalList.get(position);
        holder.tvDate.setText(currentData.getCreated_at());
        holder.tvAmount.setText(currentData.getTotal_credit());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(originalList.get(position).getAssigned_customer().getId());
                }
            }
        });
    }

    public void filter(String text){
        filteredList.clear();
        text = text.toLowerCase();
        for (TransactionModel.Message.Transaction transaction : originalList) {
            String date = transaction.getCreated_at() != null ? transaction.getCreated_at().toLowerCase() : "";
            String amount = transaction.getTotal_credit() != null ? transaction.getTotal_credit() : "";
            if(date.contains(text) || amount.contains(text)){
                filteredList.add(transaction);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return originalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String customerID);
    }

    public void setOnItemClickListener(SingleTransactionAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
