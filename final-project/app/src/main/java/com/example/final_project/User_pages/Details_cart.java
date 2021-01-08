package com.example.final_project.User_pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.final_project.R;

public class Details_cart extends AppCompatActivity {

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


    private TextView text_name,text_price,cat_name;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cart);

        final int pid = getIntent().getExtras().getInt("pid");
        String name = getIntent().getExtras().getString("name");
        double price = getIntent().getExtras().getDouble("price");
        String categoryName = getIntent().getExtras().getString("category");
        String image = getIntent().getExtras().getString("image");

        text_name = findViewById(R.id.name);
        text_price = findViewById(R.id.price);
        cat_name = findViewById(R.id.cat_name);
        imageView = findViewById(R.id.imageView);

        if(!image.isEmpty()){
            Glide.with(this).load(image).into(imageView);
        }

        text_name.setText(name);
        text_price.setText(price + "");
        cat_name.setText(categoryName);
    }
}