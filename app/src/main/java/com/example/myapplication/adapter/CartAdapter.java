package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemCartBinding;
import com.example.myapplication.databinding.QuantityDialogBinding;
import com.example.myapplication.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewholder> {
  Context context;
  ArrayList<Product>Cartlist;
  Cartlistner cartlistner;
    Cart cart;
  public interface Cartlistner{
      public void onQuantityChanged();
  }

    public CartAdapter(Context context, ArrayList<Product> cartlist,Cartlistner cartlistner) {
        this.context = context;
       this.Cartlist = cartlist;
       this.cartlistner=cartlistner;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewholder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewholder holder, int position) {
      Product product=Cartlist.get(position);
        Glide.with(context).load(product.getImage()).into(holder.binding.image);
        holder.binding.name.setText(product.getName());
        holder.binding.price.setText("Dollar"+product.getPrice());
        holder.binding.quantity.setText("product"+product.getQuantity());


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                QuantityDialogBinding quantityDialogBinding=QuantityDialogBinding.inflate(LayoutInflater.from(context));
                AlertDialog alertDialog=new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                quantityDialogBinding.productName.setText(product.getName());
                quantityDialogBinding.productStock.setText(" stock:"+product.getStock());
                quantityDialogBinding.quantity.setText(""+product.getQuantity());
                int stock=product.getStock();
              quantityDialogBinding.plusBtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
              int quantity= product.getQuantity();
                  quantity++;
                  if(quantity> product.getStock()){
                      Toast.makeText(context,"stock is max :",Toast.LENGTH_LONG).show();


                      return;
                  }
                  else{

                      product.setQuantity(quantity);
                      quantityDialogBinding.quantity.setText(String.valueOf(quantity));
                  }
                      cart.updateItem(product,product.getQuantity());
                      notifyDataSetChanged();
                      cartlistner.onQuantityChanged();
                  }
              });
             quantityDialogBinding.minusBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                 int quantity= product.getQuantity();
                 if(quantity>1){
                     quantity--;
                     product.setQuantity(quantity);
                     quantityDialogBinding.quantity.setText(String.valueOf(quantity));
                 }
                     cart.updateItem(product,product.getQuantity());
                     notifyDataSetChanged();
                     cartlistner.onQuantityChanged();
                 }
             });
             quantityDialogBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                   alertDialog.dismiss();
//                   cart.updateItem(product,product.getQuantity());
//                   notifyDataSetChanged();
//                   cartlistner.onQuantityChanged();
                 }
             });

                alertDialog.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return Cartlist.size();
    }

    public class CartViewholder extends RecyclerView.ViewHolder {
        ItemCartBinding binding;
        public CartViewholder(@NonNull View itemView) {
            super(itemView);
            binding=ItemCartBinding.bind(itemView);
        }
    }
}
