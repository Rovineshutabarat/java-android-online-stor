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
public class CartItemRecycleViewAdapter extends RecyclerView.Adapter<CartItemRecycleViewAdapter.CartItemHolder> {
    private Context context;
    private List<Product> products;

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_recycle_view, parent, false);
        return new CartItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
        Product product = products.get(position);
        holder.product_name.setText(product.getName());
        holder.product_price.setText(product.getPrice().toString());
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


    public static class CartItemHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_price;
        ImageView product_image;
        TextView cart_item_quantity;

        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_image = itemView.findViewById(R.id.product_image);
            cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
        }
    }
}
