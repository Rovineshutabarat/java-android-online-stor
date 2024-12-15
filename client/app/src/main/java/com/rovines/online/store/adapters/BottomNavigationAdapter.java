package com.rovines.online.store.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rovines.online.store.R;
import com.rovines.online.store.activities.CartActivity;
import com.rovines.online.store.activities.ProfileActivity;
import com.rovines.online.store.activities.StoreActivity;
import com.rovines.online.store.models.BottomNavigation;

import java.util.List;

public class BottomNavigationAdapter extends RecyclerView.Adapter<BottomNavigationAdapter.BottomNavigationHolder> {
    private final Context context;
    private final List<BottomNavigation> bottomNavigations = List.of(
            new BottomNavigation("Homepage", "https://img.icons8.com/windows/32/home.png"),
            new BottomNavigation("Search", "https://img.icons8.com/glyph-neue/50/search--v1.png"),
            new BottomNavigation("Cart", "https://img.icons8.com/pastel-glyph/64/shopping-cart--v1.png"),
            new BottomNavigation("Profile", "https://img.icons8.com/fluency-systems-regular/50/user--v1.png")
    );

    public BottomNavigationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BottomNavigationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_navigation_recycle_view, parent, false);
        return new BottomNavigationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomNavigationHolder holder, int position) {
        BottomNavigation bottomNavigation = bottomNavigations.get(position);
        holder.bottom_navigation_title.setText(bottomNavigation.getTitle());
        if (bottomNavigation.getImage_url() != null) {
            Glide.with(context)
                    .load(bottomNavigation.getImage_url())
                    .into(holder.bottom_navigation_image);
        }
        holder.bottom_navigation_item.setOnClickListener(v -> {
            Intent intent = null;
            if (position == 0) {
                intent = new Intent(context, StoreActivity.class);
            } else if (position == 1) {
                intent = new Intent(context, StoreActivity.class);
            } else if (position == 2) {
                intent = new Intent(context, CartActivity.class);
            } else if (position == 3) {
                intent = new Intent(context, ProfileActivity.class);
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bottomNavigations.size();
    }

    public static class BottomNavigationHolder extends RecyclerView.ViewHolder {
        TextView bottom_navigation_title;
        ImageView bottom_navigation_image;
        LinearLayout bottom_navigation_item;

        public BottomNavigationHolder(View view) {
            super(view);
            bottom_navigation_title = view.findViewById(R.id.bottom_navigation_title);
            bottom_navigation_image = view.findViewById(R.id.bottom_navigation_image);
            bottom_navigation_item = view.findViewById(R.id.bottom_navigation_item);
        }
    }
}
