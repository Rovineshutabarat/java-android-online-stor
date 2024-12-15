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
import com.rovines.online.store.models.Category;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryDashboardRecycleViewAdapter extends RecyclerView.Adapter<CategoryDashboardRecycleViewAdapter.CategoryDashboardRecycleViewHolder> {
    private Context context;
    private List<Category> categories;
    private ActionListener actionListener;

    @NonNull
    @Override
    public CategoryDashboardRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_dashboard_recycle_view, parent, false);
        return new CategoryDashboardRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDashboardRecycleViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());
        holder.description.setText(category.getDescription());
        if (category.getImage_url() != null) {
            Glide.with(context)
                    .load(category.getImage_url())
                    .into(holder.image);
        }
        holder.delete_category_button.setOnClickListener(v -> {
            actionListener.onDelete(category.getId());
        });

        holder.edit_category_button.setOnClickListener(v -> {
            actionListener.onEdit(category.getId());
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryDashboardRecycleViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;
        private ImageView image;
        private Button delete_category_button;
        private Button edit_category_button;

        public CategoryDashboardRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.description = itemView.findViewById(R.id.description);
            this.image = itemView.findViewById(R.id.image);
            this.delete_category_button = itemView.findViewById(R.id.delete_category_button);
            this.edit_category_button = itemView.findViewById(R.id.edit_category_button);
        }
    }
}
