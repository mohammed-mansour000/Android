package com.example.final_project.Admin_pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.Adapter.ProductAdapter;
import com.example.final_project.R;
import com.example.final_project.model.Url;
import com.example.final_project.Admin_pages.addProduct;
import com.example.final_project.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton add;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        add = findViewById(R.id.fabButton);
        recyclerView = findViewById(R.id.category);
        dialog = new Dialog(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, addProduct.class);
                startActivity(intent);
            }
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        getData();
        //products.clear();
    }

    private void getData() {

        products.clear();

        jsonArrayRequest = new JsonArrayRequest(Url.url_getProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
                        Product p = new Product();
                        p.setPid(jsonObject.getInt("pid"));
                        p.setName(jsonObject.getString("name"));
                        p.setPrice(jsonObject.getDouble("price"));
                        p.setCategory(jsonObject.getString("category"));

                        products.add(p);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                adapterPush(products);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(Admin.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void adapterPush(ArrayList<Product> products) {
        productAdapter = new ProductAdapter(this, products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

    }
}