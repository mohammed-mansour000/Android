package com.example.final_project.User_pages;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.Adapter.ProductAdapter_cart;
import com.example.final_project.Adapter.ProductAdapter_user;
import com.example.final_project.R;
import com.example.final_project.model.Order;
import com.example.final_project.model.Product;
import com.example.final_project.model.Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {

    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ProductAdapter_cart productAdapter_cart;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final int user_id = getIntent().getExtras().getInt("user_id");

        recyclerView = findViewById(R.id.category);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);

        getData();

        //Log.i("user_id is ",String.valueOf(orders.get(0).getUser_id()));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData() {


        orders.clear();

//        Map<String, String> params = new HashMap();
//        params.put("user_id", String.valueOf(getIntent().getExtras().getInt("user_id")));
//
//        JSONObject parameters = new JSONObject(params);

        StringRequest stringRequest = new StringRequest (Request.Method.DEPRECATED_GET_OR_POST, Url.url_getOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    try {
                        JSONArray values =new JSONArray(response);
                        for (int i = 0; i < values.length(); i++) {
                            JSONObject object = values.getJSONObject(i);

                            // jsonObject = response.getJSONObject(i);
                            Order O = new Order();
                            O.setPid(object.getInt("pid"));
                            O.setOrder_id(object.getInt("order_id"));
                            O.setUser_id(object.getInt("user_id"));
                            O.setName(object.getString("name"));
                            O.setPrice(object.getDouble("price"));
                            O.setCategory(object.getString("category"));
                            O.setImage(object.getString("image"));

                            orders.add(O);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                adapterPush(orders);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params =new HashMap<>();
                params.put("user_id",String.valueOf(getIntent().getExtras().getInt("user_id")));  // 4 is just for now. it should return 3 values from sql.
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(Cart.this);
        requestQueue.add(stringRequest);
    }
    private void adapterPush(ArrayList<Order> orders) {
        productAdapter_cart = new ProductAdapter_cart(this, orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter_cart);

    }
}