package com.rovines.online.store.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rovines.online.store.R;
import com.rovines.online.store.models.Product;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.PopularProductViewHolder> {
    private Context context;
    private List<Product> products;

    @NonNull
    @Override
    public ProductRecycleViewAdapter.PopularProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_recycle_view, parent, false);
        return new ProductRecycleViewAdapter.PopularProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.product_name.setText(product.getName());
        holder.product_category_name.setText(product.getCategory().getName());
        holder.product_price.setText(String.format("Rp.%.2f", product.getPrice()));
        if (product.getImage_url() != null) {
            Glide.with(context)
                    .load(product.getImage_url())
                    .into(holder.product_image);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class PopularProductViewHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_price;
        TextView product_category_name;
        ImageView product_image;

        public PopularProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_category_name = itemView.findViewById(R.id.product_category_name);
            product_image = itemView.findViewById(R.id.product_image);
        }
    }
}
