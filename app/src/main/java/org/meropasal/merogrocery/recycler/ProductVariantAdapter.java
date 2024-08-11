package org.meropasal.merogrocery.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.ProductVariantModel;
import org.meropasal.merogrocery.model.VendorCustomerRecyclerModel;

import java.util.ArrayList;

public class ProductVariantAdapter extends RecyclerView.Adapter<ProductVariantAdapter.ProductVariantHolder>{
    Context context;
    ArrayList<ProductVariantModel.Products> allProducts, filteredProducts;
    ArrayList<ProductVariantModel.Variants> allVariants, filteredVariants;
    private ProductVariantAdapter.OnItemClickListener onItemClickListener;

    public ProductVariantAdapter() {
    }

    public ProductVariantAdapter(Context context, ArrayList<ProductVariantModel.Products> allProducts, ArrayList<ProductVariantModel.Variants> allVariants) {
        this.context = context;
        this.allProducts = allProducts != null ? allProducts : new ArrayList<>();
        this.allVariants = allVariants != null ? allVariants : new ArrayList<>();
        this.filteredProducts = new ArrayList<>(this.allProducts);
        this.filteredVariants = new ArrayList<>(this.allVariants);
    }

    @NonNull
    @Override
    public ProductVariantAdapter.ProductVariantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_product_variant, parent, false);
        return new ProductVariantAdapter.ProductVariantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVariantAdapter.ProductVariantHolder holder, int position) {
        ProductVariantModel.Products currentProduct;
        ProductVariantModel.Variants currentVariant;
        if(allVariants.isEmpty()){
            currentVariant = null;
            currentProduct = filteredProducts.get(position);
        }else{
            currentProduct = null;
            currentVariant = filteredVariants.get(position);
        }

        if(allVariants.isEmpty()){
            holder.tvProductVariant.setText(currentProduct.getName());
            holder.tvCode.setText("");
            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(Integer.parseInt(currentProduct.getId()), currentProduct.getName(), null);
                }
            });
        }else{
            holder.tvProductVariant.setText(currentVariant.getName());
            String code = "(" + currentVariant.getVariant_code() + ")";
            holder.tvCode.setText(code);
            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(Integer.parseInt(currentVariant.getId()), currentVariant.getName(), currentVariant.getVariant_code());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(allVariants.isEmpty()) {
            return filteredProducts.size();
        }else{
            return filteredVariants.size();
        }
    }

    public void filter(String texts) {

        if(allVariants.isEmpty()) {
            filteredProducts.clear();
            texts = texts.toLowerCase();
            for (ProductVariantModel.Products products : allProducts) {
                String userName = products.getName() != null ? products.getName().toLowerCase() : "";
                if (userName.contains(texts)) {
                    filteredProducts.add(products);
                } else {
                }
            }
        }else{
            filteredVariants.clear();
            texts = texts.toLowerCase();
            for (ProductVariantModel.Variants variants : allVariants) {
                String userName = variants.getName() != null ? variants.getName().toLowerCase() : "";
                if (userName.contains(texts)) {
                    filteredVariants.add(variants);
                } else {
                }
            }
        }
        notifyDataSetChanged();
    }

    public void initialSetProduct(){
            filteredProducts.addAll(allProducts);
    }

    public void initialSetVariant(){
        filteredVariants.addAll(allVariants);
    }


    public static class ProductVariantHolder extends RecyclerView.ViewHolder {
        private final TextView tvProductVariant;
        private final TextView tvCode;
        public ProductVariantHolder(@NonNull View itemView) {
            super(itemView);
            tvProductVariant = itemView.findViewById(R.id.tvProductVariant);
            tvCode = itemView.findViewById(R.id.tvCode);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int id, String name, String code);
    }
    public void setOnItemClickListener(ProductVariantAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
