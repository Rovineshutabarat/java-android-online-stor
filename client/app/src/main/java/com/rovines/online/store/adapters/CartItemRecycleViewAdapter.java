package com.rovines.online.store.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rovines.online.store.R;
import com.rovines.online.store.helpers.CartManager;
import com.rovines.online.store.models.CartItem;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartItemRecycleViewAdapter extends RecyclerView.Adapter<CartItemRecycleViewAdapter.CartItemHolder> {
    private Context context;
    private List<CartItem> cartItems;
    private CartManager cartManager;

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_recycle_view, parent, false);
        cartManager = CartManager.getInstance(context);
        return new CartItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.product_name.setText(cartItem.getName());
        holder.product_price.setText(cartItem.getPrice().toString());
        holder.cart_item_quantity.setText(cartItem.getQuantity().toString());
        if (cartItem.getImage_url() != null) {
            Glide.with(context)
                    .load(cartItem.getImage_url())
                    .into(holder.product_image);
        }

        holder.delete_button.setOnClickListener(v -> {
            cartManager.removeFromCart(cartItem.getId());
            Toast.makeText(context, "Berhasil Hapus Produk Dari Keranjang.", Toast.LENGTH_LONG).show();
            cartItems.remove(position);
            notifyItemRemoved(position);
        });

        holder.increase_quantity_btn.setOnClickListener(v -> {
            cartManager.updateQuantity(cartItem, +1);
            notifyItemChanged(position);
        });

        holder.decrease_quantity_btn.setOnClickListener(v -> {
            cartManager.updateQuantity(cartItem, -1);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }


    public static class CartItemHolder extends RecyclerView.ViewHolder {
        TextView product_name;
        TextView product_price;
        ImageView product_image;
        TextView cart_item_quantity;
        ImageView delete_button;
        Button decrease_quantity_btn;
        Button increase_quantity_btn;

        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_image = itemView.findViewById(R.id.product_image);
            cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
            delete_button = itemView.findViewById(R.id.delete_button);
            decrease_quantity_btn = itemView.findViewById(R.id.decrease_quantity_btn);
            increase_quantity_btn = itemView.findViewById(R.id.increase_quantity_btn);
        }
    }
}
