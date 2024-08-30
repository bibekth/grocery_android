package org.meropasal.merogrocery.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;

import java.util.ArrayList;

public class FilterCustomerForPaymentAdapter extends RecyclerView.Adapter<FilterCustomerForPaymentAdapter.CustomerViewHolder>{

    Context context;
    private ArrayList<VendorCustomerRecyclerModel.Message.Customer> originalList;
    private ArrayList<VendorCustomerRecyclerModel.Message.Customer> filteredList;
    private FilterCustomerForPaymentAdapter.OnItemClickListener onItemClickListener;

    public FilterCustomerForPaymentAdapter() {
    }

    public FilterCustomerForPaymentAdapter(Context context, ArrayList<VendorCustomerRecyclerModel.Message.Customer> originalList) {
        this.context = context;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>(originalList);
    }

    @NonNull
    @Override
    public FilterCustomerForPaymentAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_all_customer, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterCustomerForPaymentAdapter.CustomerViewHolder holder, int position) {
        VendorCustomerRecyclerModel.Message.Customer currentUser = filteredList.get(position);
        holder.tvName.setText(currentUser.getAssigned_name() != null ? currentUser.getAssigned_name() : "Unknown");
        holder.tvPhoneNumber.setText(currentUser.getPhone_number() != null ? currentUser.getPhone_number() : "Unknown");

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(Integer.parseInt(currentUser.getId().toString()), currentUser.getPhone_number(), currentUser.getAssigned_name());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String texts) {
        filteredList.clear();
        texts = texts.toLowerCase();
        for (VendorCustomerRecyclerModel.Message.Customer user : originalList) {
            String userName = user.getAssigned_name() != null ? user.getAssigned_name().toLowerCase() : "";
            String phoneNumber = user.getPhone_number() != null ? user.getPhone_number() : "";
            if (userName.contains(texts) || phoneNumber.contains(texts)) {
                filteredList.add(user);
            } else {
            }
        }
        notifyDataSetChanged();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvPhoneNumber;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int customerID, String phoneNumber, String assignedName);
    }

    public void setOnItemClickListener(FilterCustomerForPaymentAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
