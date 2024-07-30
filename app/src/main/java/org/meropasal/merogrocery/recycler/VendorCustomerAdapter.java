package org.meropasal.merogrocery.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.UserModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;

import java.util.ArrayList;
import java.util.List;

public class VendorCustomerAdapter extends RecyclerView.Adapter<VendorCustomerAdapter.VendorCustomerHolder> {

    Context context;
    ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrCustomer;
    private OnItemClickListener onItemClickListener;
    public VendorCustomerAdapter(Context context, ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrCustomer) {
        this.context = context;
        this.arrCustomer = arrCustomer;
    }

    @NonNull
    @Override
    public VendorCustomerAdapter.VendorCustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_vendor_customer,parent, false);
        return new VendorCustomerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorCustomerAdapter.VendorCustomerHolder holder, int position) {
        holder.profileImage.setImageResource(R.drawable.logo);
        if(arrCustomer.get(position).getAssigned_name() == null){
            holder.name.setText(R.string.app_name);
        }else{
            holder.name.setText(arrCustomer.get(position).getAssigned_name());
        }
        if(arrCustomer.get(position).getPhone_number() != null) {
            holder.phoneNumber.setText(arrCustomer.get(position).getPhone_number());
        }else{
            holder.phoneNumber.setText(R.string.my_phone_number);
        }
        holder.totalCredit.setText(arrCustomer.get(position).getAmount());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(arrCustomer.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCustomer.size();
    }

    public static class VendorCustomerHolder extends RecyclerView.ViewHolder {
        TextView name, phoneNumber, totalCredit;
        ImageView profileImage;
        public VendorCustomerHolder(@NonNull View itemView) {
            super(itemView);
            phoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            name = itemView.findViewById(R.id.tvUserName);
            profileImage = itemView.findViewById(R.id.ivProfilePic);
            totalCredit = itemView.findViewById(R.id.tvTotalCredit);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Integer customerId);
    }

    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
