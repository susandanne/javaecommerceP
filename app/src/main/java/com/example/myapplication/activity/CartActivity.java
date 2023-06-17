package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.databinding.ActivityCartBinding;
import com.example.myapplication.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
  ActivityCartBinding binding;
  CartAdapter cartAdapter;
  ArrayList<Product> productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       productArrayList=new ArrayList<>();
//       productArrayList.add(new Product("name","","",1,22,33,1));
        Cart cart = TinyCartHelper.getCart();
       for(Map.Entry<Item,Integer>item:cart.getAllItemsWithQty().entrySet()){
    Product product=(Product) item.getKey();
    int quantity=item.getValue();
    product.setQuantity(quantity);
    productArrayList.add(product);

       }

       cartAdapter=new CartAdapter(this, productArrayList, new CartAdapter.Cartlistner() {
           @Override
           public void onQuantityChanged() {
               binding.subtotal.setText(String.format("dollar %.2f ",cart.getTotalPrice()));
           }
       });

       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,linearLayoutManager.getOrientation());
      binding.cartList.addItemDecoration(dividerItemDecoration);
       binding.cartList.setLayoutManager(linearLayoutManager);
       binding.cartList.setAdapter(cartAdapter);

       binding.subtotal.setText(String.format("dollar %.2f ",cart.getTotalPrice()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,CheckoutActivity2.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}