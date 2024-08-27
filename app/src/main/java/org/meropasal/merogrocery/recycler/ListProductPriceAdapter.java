package org.meropasal.merogrocery.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.meropasal.merogrocery.R;
import org.meropasal.merogrocery.model.AllCustomerModel;
import org.meropasal.merogrocery.model.ProductPriceModel;

import java.util.ArrayList;
import java.util.List;

public class ListProductPriceAdapter extends RecyclerView.Adapter<ListProductPriceAdapter.ViewHolder> {

    Context context;
    ArrayList<ProductPriceModel.Message.ProductPrices> originalList;
    ArrayList<ProductPriceModel.Message.ProductPrices> filteredList;
    private ListProductPriceAdapter.OnItemClickListener onItemClickListener;

    public ListProductPriceAdapter(Context context, ArrayList<ProductPriceModel.Message.ProductPrices> originalList) {
        this.context = context;
        this.originalList = originalList;
        this.filteredList = new ArrayList<>(originalList);
    }

    @NonNull
    @Override
    public ListProductPriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_product_price,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductPriceAdapter.ViewHolder holder, int position) {
        ProductPriceModel.Message.ProductPrices currentProduct = filteredList.get(position);
        holder.tvProduct.setText(currentProduct.getProduct().getName());
        holder.tvQuantity.setText(currentProduct.getQuantity());
        holder.tvVariant.setText(currentProduct.getVariant().getVariant_code());
        holder.tvPrice.setText(currentProduct.getPrice());

        holder.itemView.setOnClickListener(v->{
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(filteredList.get(position).getId(), filteredList.get(position).getProduct().getName(), filteredList.get(position).getVariant().getVariant_code(), filteredList.get(position).getQuantity(), filteredList.get(position).getPrice());
            }
        });
    }

    public void filter(String text){
        filteredList.clear();
        text = text.toLowerCase();
        for (ProductPriceModel.Message.ProductPrices product : originalList) {
            String productName = product.getProduct().getName() != null ? product.getProduct().getName().toLowerCase() : "";
            if(productName.contains(text)){
                filteredList.add(product);
            }else{

            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProduct, tvQuantity, tvVariant, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProduct = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvVariant = itemView.findViewById(R.id.tvVariant);
            tvPrice = itemView.findViewById(R.id.tvRate);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String productID, String name, String variant, String quantity, String price);
    }

    public void setOnItemClickListener(ListProductPriceAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
