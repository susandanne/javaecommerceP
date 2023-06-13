package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.databinding.ActivityCartBinding;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
  ActivityCartBinding binding;
  CartAdapter cartAdapter;
  ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       productArrayList=new ArrayList<>();
       cartAdapter=new CartAdapter(this,productArrayList);

       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,linearLayoutManager.getOrientation());
      binding.cartList.addItemDecoration(dividerItemDecoration);
       binding.cartList.setLayoutManager(linearLayoutManager);
       binding.cartList.setAdapter(cartAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}