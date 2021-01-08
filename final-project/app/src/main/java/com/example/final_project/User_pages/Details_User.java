package com.example.final_project.User_pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.final_project.Admin_pages.addProduct;
import com.example.final_project.R;
import com.example.final_project.model.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Details_User extends AppCompatActivity {

    private TextView text_name,text_price,cat_name;
    private Button add_to_cart;
    private RequestQueue queue;
    private ImageView imageView;


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
        setContentView(R.layout.activity_details__user);

        final int pid = getIntent().getExtras().getInt("pid");
        String name = getIntent().getExtras().getString("name");
        double price = getIntent().getExtras().getDouble("price");
        String categoryName = getIntent().getExtras().getString("category");
        String image = getIntent().getExtras().getString("image");
        final int user_id = getIntent().getExtras().getInt("user_id");

        queue = Volley.newRequestQueue(this);

        Log.i("user_id",user_id+"");

        text_name = findViewById(R.id.name);
        text_price = findViewById(R.id.price);
        cat_name = findViewById(R.id.cat_name);
        add_to_cart = findViewById(R.id.add_to_cart);
        imageView = findViewById(R.id.imageView);

        if(!image.isEmpty()){
            Glide.with(this).load(image).into(imageView);
        }

        text_name.setText(name);
        text_price.setText(price + "");
        cat_name.setText(categoryName);

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder(pid,user_id);
            }
        });
    }

    private void addOrder(final int pid, final int user_id){
        add_to_cart.setEnabled(false);

        StringRequest request = new StringRequest(Request.Method.POST, Url.url_addOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.i("tagconvertstr", "["+response+"]");

                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getBoolean("error") == false){

                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    // e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                add_to_cart.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                add_to_cart.setEnabled(true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("product_id", String.valueOf(pid));
                params.put("user_id", String.valueOf(user_id));
                return params;
            }
        };

        queue.add(request);
    }
}