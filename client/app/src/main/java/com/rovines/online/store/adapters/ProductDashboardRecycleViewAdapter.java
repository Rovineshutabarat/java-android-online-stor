package com.rovines.online.store.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rovines.online.store.R;
import com.rovines.online.store.listeners.ActionListener;
import com.rovines.online.store.models.Product;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductDashboardRecycleViewAdapter extends RecyclerView.Adapter<ProductDashboardRecycleViewAdapter.ProductDashboardViewHolder> {

    private Context context;
    private List<Product> products;
    private ActionListener actionListener;

    @NonNull
    @Override
    public ProductDashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_dashboard_recycle_view, parent, false);
        return new ProductDashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDashboardViewHolder holder, int position) {
        Product product = products.get(position);
        holder.category_name.setText(product.getCategory().getName());
        holder.name.setText(product.getName());
        holder.price.setText("Rp." + product.getPrice().toString());
        if (product.getImage_url() != null) {
            Glide.with(context)
                    .load(product.getImage_url())
                    .into(holder.image);
        }
        holder.edit_product_button.setOnClickListener(v -> {
            actionListener.onEdit(product.getId());
        });

        holder.delete_product_button.setOnClickListener(v -> {
            actionListener.onDelete(product.getId());
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductDashboardViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView category_name;
        TextView name;
        TextView price;
        Button edit_product_button;
        Button delete_product_button;

        public ProductDashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            category_name = itemView.findViewById(R.id.category_name);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            edit_product_button = itemView.findViewById(R.id.edit_product_button);
            delete_product_button = itemView.findViewById(R.id.delete_product_button);
        }
    }
}
