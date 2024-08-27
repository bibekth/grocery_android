package org.meropasal.merogrocery.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.AllCustomerModel;
public class AllCustomerFilterAdapter extends RecyclerView.Adapter<AllCustomerFilterAdapter.CustomerViewHolder> {

    Context context;
    private List<AllCustomerModel.Data> originalList;
    private List<AllCustomerModel.Data> filteredList;
    private OnItemClickListener onItemClickListener;
    public AllCustomerFilterAdapter() {
    }
    public AllCustomerFilterAdapter(Context context, List<AllCustomerModel.Data> orinalList) {
        this.context = context;
        this.originalList = orinalList;
        this.filteredList = new ArrayList<>(orinalList);
    }
    @NonNull
    @Override
    public AllCustomerFilterAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_all_customer, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCustomerFilterAdapter.CustomerViewHolder holder, int position) {
        AllCustomerModel.Data currentUser = filteredList.get(position);
        holder.tvName.setText(currentUser.getName() != null ? currentUser.getName() : "Unknown");
        holder.tvPhoneNumber.setText(currentUser.getPhone_number() != null ? currentUser.getPhone_number() : "Unknown");

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(Integer.parseInt(currentUser.getId()), currentUser.getPhone_number());
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
        for (AllCustomerModel.Data user : originalList) {
            String userName = user.getName() != null ? user.getName().toLowerCase() : "";
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
        void onItemClick(Integer customerId, String phoneNumber);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}