package org.meropasal.merogrocery.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.TransactionModel;

import java.util.ArrayList;

public class AllTransactionAdapter extends RecyclerView.Adapter<AllTransactionAdapter.ViewHolder> {
    Context context;
    ArrayList<TransactionModel.Message.Transaction> originalList;
    ArrayList<TransactionModel.Message.Transaction> filteredList;

    public AllTransactionAdapter() {
    }

    public AllTransactionAdapter(Context context, ArrayList<TransactionModel.Message.Transaction> originalList) {
        this.context = context;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>(originalList);
    }

    @NonNull
    @Override
    public AllTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_all_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTransactionAdapter.ViewHolder holder, int position) {
        TransactionModel.Message.Transaction currentItem = originalList.get(position);
        String name = currentItem.getAssigned_customer().getAssign_customer() != null ? currentItem.getAssigned_customer().getAssigned_name() : currentItem.getAssigned_customer().getAssign_vendor() != null ? currentItem.getAssigned_customer().getAssign_vendor().getFirm_name() : "No Name";   ;
        holder.tvName.setText(name);
        holder.tvDate.setText(currentItem.getCreated_at());
        holder.tvAmount.setText(currentItem.getTotal_credit());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
