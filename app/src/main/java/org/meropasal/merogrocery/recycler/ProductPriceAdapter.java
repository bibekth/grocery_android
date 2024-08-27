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
import org.meropasal.merogrocery.model.ProductPriceModel;

import java.util.ArrayList;

public class ProductPriceAdapter extends RecyclerView.Adapter<ProductPriceAdapter.ProductPriceHolder> {
    Context context;
    ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices;
    ArrayList<ProductPriceModel.Message.ProductPrices> filteredPrices;
    private ProductPriceAdapter.OnItemClickListener onItemClickListener;

    public ProductPriceAdapter() {
    }

    public ProductPriceAdapter(Context context, ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices) {
        this.context = context;
        this.arrProductPrices = arrProductPrices;
    }

    public ProductPriceAdapter(Context context, ArrayList<ProductPriceModel.Message.ProductPrices> arrProductPrices, String a) {
        this.context = context;
        this.arrProductPrices = arrProductPrices;
        this.filteredPrices = new ArrayList<>(arrProductPrices);
    }

    @NonNull
    @Override
    public ProductPriceAdapter.ProductPriceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_product_price,parent, false);
        return new ProductPriceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPriceAdapter.ProductPriceHolder holder, int position) {
        String num = String.valueOf(position + 1) + ".";
        holder.tvSerialNumber.setText(num);
        holder.tvProductName.setText(arrProductPrices.get(position).getProduct().getName());
        holder.tvVariant.setText(arrProductPrices.get(position).getVariant().getVariant_code());
        holder.tvQuantity.setText(arrProductPrices.get(position).getQuantity());
        holder.tvRate.setText(arrProductPrices.get(position).getPrice());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(Integer.parseInt(arrProductPrices.get(position).getId()), arrProductPrices.get(position).getProduct().getName(), arrProductPrices.get(position).getVariant().getVariant_code(), arrProductPrices.get(position).getQuantity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrProductPrices.size();
    }



    public static class ProductPriceHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvQuantity, tvVariant, tvRate, tvSerialNumber;
        ImageView ivEdit;
        public ProductPriceHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvVariant = itemView.findViewById(R.id.tvVariant);
            tvRate = itemView.findViewById(R.id.tvRate);
            ivEdit = itemView.findViewById(R.id.ivUpdateProductIcon);
            tvSerialNumber = itemView.findViewById(R.id.tvSerialNumber);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int productID, String name, String variant, String quantity);
    }

    public void setOnItemClickListener(ProductPriceAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
