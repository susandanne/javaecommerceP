package com.example.myapplication.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ItemProductBinding;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Product;
import com.example.myapplication.utils.Constants;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     ActivityMainBinding binding;
    ArrayList<Category> categoryArrayList;
    CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;
    ArrayList<Product>productArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categorydata();
        productdata();
        initSlider();

    }

    private void initSlider() {
//        binding.carousel.addData(new CarouselItem("https://cdn.pixabay.com/photo/2023/05/31/15/22/poppies-8031678_1280.jpg","img1"));
        getrecentoffers();
    }

    private void productdata() {
        productArrayList=new ArrayList<>();
//        productArrayList.add(new Product("hello","","",4,5,7,7));
        getrecentProducts();
        productAdapter=new ProductAdapter(this,productArrayList);
        binding.productslistRC.setAdapter(productAdapter);
    }

    private void categorydata(){

        categoryArrayList=new ArrayList<>();
//         categoryArrayList.add(new Category("name","https://cdn.pixabay.com/photo/2023/05/31/15/22/poppies-8031678_1280.jpg","purple","gggg",1));
//         categoryArrayList.add(new Category("name","https://cdn.pixabay.com/photo/2023/05/31/15/22/poppies-8031678_1280.jpg","purple","gggg",2));
//         categoryArrayList.add(new Category("name","https://cdn.pixabay.com/photo/2023/05/31/15/22/poppies-8031678_1280.jpg","purple","gggg",3));
//         categoryArrayList.add(new Category("name","https://cdn.pixabay.com/photo/2023/05/31/15/22/poppies-8031678_1280.jpg","orange","gggg",4));
        categoryAdapter=new CategoryAdapter(this,categoryArrayList);



        binding.categorylistRC.setAdapter(categoryAdapter);
        getCategories();
    }
     void getCategories(){
         RequestQueue queue = Volley.newRequestQueue(this);

         StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
//                 Log.i(TAG, "onResponse: "+response.toString());
                 try {
                     JSONObject object=new JSONObject(response);
                     if(object.getString("status").equals("success")){
                         JSONArray jsonArray= object.getJSONArray("categories");
                         for (int i=0;i<jsonArray.length();i++) {
                             JSONObject jsonObject = jsonArray.getJSONObject(i);
                             Category c = new Category(
                                     jsonObject.getString("name"),
                           Constants.CATEGORIES_IMAGE_URL+jsonObject.getString("icon"),
                             jsonObject.getString("color"),
                             jsonObject.getString("brief"),
                             jsonObject.getInt("id")
                           );
                           categoryArrayList.add(c);

                         }
                         categoryAdapter.notifyDataSetChanged();
                     }
                     else{

                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

             }
         });
        queue.add(stringRequest);
     }
     void getrecentProducts(){
        RequestQueue queue=Volley.newRequestQueue(this);
        String url=Constants.GET_PRODUCTS_URL + "?count=10";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainobect=new JSONObject(response);
                    if(mainobect.getString("status").equals("success")){
                     JSONArray jsonArray=mainobect.getJSONArray("products");
                     for(int i=0;i<jsonArray.length();i++){
                         JSONObject childobject=jsonArray.getJSONObject(i);
                         Product product=new Product(
                              childobject.getString("name"),
                           Constants.PRODUCTS_IMAGE_URL + childobject.getString("image"),
                              childobject.getString("status"),
                              childobject.getDouble("price"),
                              childobject.getDouble("price_discount"),
                              childobject.getInt("stock"),
                              childobject.getInt("id")
                         );
                         productArrayList.add(product);
                     }
                     productAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
     }
     void getrecentoffers(){
         RequestQueue queue = Volley.newRequestQueue(this);
         StringRequest request= new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONObject main=new JSONObject(response);
                     if(main.getString("status").equals("success")){
                         JSONArray offersarry=main.getJSONArray("news_infos");
                        for(int i=0;i<offersarry.length();i++){
                            JSONObject childoject=offersarry.getJSONObject(i);
                            binding.carousel.addData(new CarouselItem(
                                    Constants.NEWS_IMAGE_URL+childoject.getString("image"),
                                    childoject.getString("title")
                            ));
                        }
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {

             }
         });
         queue.add(request);
     }
}