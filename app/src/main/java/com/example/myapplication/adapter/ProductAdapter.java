package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.ProductDetailActivity;
import com.example.myapplication.databinding.ItemProductBinding;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Productviewholder> {
    Context context;
    ArrayList<Product>products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public Productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Productviewholder(LayoutInflater.from(context).inflate(R.layout.item_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Productviewholder holder, int position) {
     Product pd=products.get(position);
     Glide.with(context).load(pd.getImage()).into(holder.itemProductBinding.image);

     holder.itemProductBinding.label.setText(pd.getName());
     holder.itemProductBinding.price.setText("dollar"+pd.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("name",pd.getName());
                intent.putExtra("image",pd.getImage());
                intent.putExtra("id",pd.getId());
                intent.putExtra("price",pd.getPrice());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class Productviewholder extends RecyclerView.ViewHolder {
        ItemProductBinding itemProductBinding;
        public Productviewholder(@NonNull View itemView) {
            super(itemView);
            itemProductBinding=ItemProductBinding.bind(itemView);
        }
    }
}
