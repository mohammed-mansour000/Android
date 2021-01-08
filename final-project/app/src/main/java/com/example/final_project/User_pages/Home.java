package com.example.final_project.User_pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.Adapter.ProductAdapter;
import com.example.final_project.Adapter.ProductAdapter_user;
import com.example.final_project.Admin_pages.Admin;
import com.example.final_project.R;
import com.example.final_project.model.Product;
import com.example.final_project.model.Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ProductAdapter_user productAdapter_user;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.app_bar_cart:
                Intent intent = new Intent(getApplicationContext(),Cart.class);
                int user_id = getIntent().getExtras().getInt("user_id");
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            return true;

            case R.id.app_bar_dial:
                Uri number = Uri.parse("tel: 70121954");
                Intent intent1 = new Intent(Intent.ACTION_DIAL, number);
                startActivity(intent1);
                return true;

            case R.id.app_bar_gps:
                Uri location = Uri.parse("geo:0,0?q=1600+Naher elbared camp,+tamer cell");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, location);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        recyclerView = findViewById(R.id.category);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);

        getData();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


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
                        p.setImage(jsonObject.getString("image"));

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

        requestQueue = Volley.newRequestQueue(Home.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void adapterPush(ArrayList<Product> products) {
        int user_id = getIntent().getExtras().getInt("user_id");
        productAdapter_user = new ProductAdapter_user(this, products,user_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter_user);

    }
}