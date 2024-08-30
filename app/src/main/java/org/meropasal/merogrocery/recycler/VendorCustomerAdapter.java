package org.meropasal.merogrocery.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;

import java.util.ArrayList;
import java.util.List;

public class VendorCustomerAdapter extends RecyclerView.Adapter<VendorCustomerAdapter.VendorCustomerHolder> {

    Context context;
    ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrCustomer;
    ArrayList<VendorCustomerRecyclerModel.Message.Customer> filterCustomer;
    ArrayList<VendorCustomerRecyclerModel.Message.Vendor> arrVendor;
    ArrayList<VendorCustomerRecyclerModel.Message.Vendor> filterVendor;
    private OnItemClickListener onItemClickListener;
    public VendorCustomerAdapter(Context context, ArrayList<VendorCustomerRecyclerModel.Message.Customer> arrCustomer, ArrayList<VendorCustomerRecyclerModel.Message.Vendor> arrVendor) {
        this.context = context;
        this.arrCustomer = arrCustomer;
        this.arrVendor = arrVendor;
        this.filterCustomer = new ArrayList<>(arrCustomer);
        this.filterVendor = new ArrayList<>(arrVendor);
    }

    @NonNull
    @Override
    public VendorCustomerAdapter.VendorCustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_vendor_customer,parent, false);
        return new VendorCustomerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorCustomerAdapter.VendorCustomerHolder holder, int position) {
        if(arrVendor.size() == 0){
            VendorCustomerRecyclerModel.Message.Customer currentUser = filterCustomer.get(position);
            holder.name.setText(currentUser.getAssigned_name());
            holder.phoneNumber.setText(currentUser.getPhone_number());
            holder.totalCredit.setText(currentUser.getAmount());

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(filterCustomer.get(position).getId(), filterCustomer.get(position).getAssigned_name());
                }
            });
        }else if(arrCustomer.size() == 0){
            VendorCustomerRecyclerModel.Message.Vendor currentUser = filterVendor.get(position);
            holder.name.setText(currentUser.getFirm_name());
            holder.phoneNumber.setText(currentUser.getContact());
            holder.totalCredit.setText(currentUser.getCredit_amount());

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(filterVendor.get(position).getId(), filterVendor.get(position).getFirm_name());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(arrVendor.size() == 0) {
            return filterCustomer.size();
        }else{
            return filterVendor.size();
        }
    }

    public void filter(String texts) {
        if(arrVendor.size() == 0){
            filterCustomer.clear();
            texts = texts.toLowerCase();
            for (VendorCustomerRecyclerModel.Message.Customer user : arrCustomer) {
                String userName = user.getAssigned_name()    != null ? user.getAssigned_name().toLowerCase() : "";
                String phoneNumber = user.getPhone_number() != null ? user.getPhone_number() : "";
                if (userName.contains(texts) || phoneNumber.contains(texts)) {
                    filterCustomer.add(user);
                } else {
                }
            }
        }else{
            filterVendor.clear();
            texts = texts.toLowerCase();
            for (VendorCustomerRecyclerModel.Message.Vendor user : arrVendor) {
                String userName = user.getFirm_name() != null ? user.getFirm_name().toLowerCase() : "";
                String phoneNumber = user.getContact() != null ? user.getContact() : "";
                if (userName.contains(texts) || phoneNumber.contains(texts)) {
                    filterVendor.add(user);
                } else {
                }
            }
        }

        notifyDataSetChanged();
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
        void onItemClick(Integer customerId, String name);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
