package com.example.final_project.Admin_pages;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.final_project.R;

public class Details_admin extends AppCompatActivity {

    private TextView text_name,text_price,cat_name;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_admin);

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