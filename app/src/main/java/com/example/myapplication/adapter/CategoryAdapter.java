package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemCategoriesBinding;
import com.example.myapplication.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<Category> categoryAdapters;

    public CategoryAdapter(Context context, ArrayList<Category> categoryAdapters) {
        this.context = context;
        this.categoryAdapters = categoryAdapters;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_categories,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
      Category category=categoryAdapters.get(position);
      holder.binding.label.setText(category.getName());
        Glide.with(context).load(category.getIcon()).into(holder.binding.image);
//        holder.binding.image.setBackgroundColor(Color.parseColor(category.getColor()));
    }

    @Override
    public int getItemCount() {
        return categoryAdapters.size();
    }

    public class  CategoryViewHolder extends RecyclerView.ViewHolder {
           ItemCategoriesBinding binding;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemCategoriesBinding.bind(itemView);
        }
    }
}
