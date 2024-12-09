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
import com.rovines.online.store.models.Category;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryRecycleViewAdapter extends RecyclerView.Adapter<CategoryRecycleViewAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categories;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_recycle_view, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.category_name.setText(category.getName());
        if (category.getImage_url() != null) {
            Glide.with(context)
                    .load(category.getImage_url())
                    .into(holder.category_image);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        ImageView category_image;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            category_name = itemView.findViewById(R.id.category_name);
            category_image = itemView.findViewById(R.id.category_image);
        }
    }
}
