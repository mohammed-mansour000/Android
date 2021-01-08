package com.example.final_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.User_pages.Details_User;
import com.example.final_project.model.Product;

import java.util.ArrayList;

public class ProductAdapter_user extends RecyclerView.Adapter<ProductAdapter_user.MyViewHolder> {

    private int user_id;
    private Context context;
    private ArrayList<Product> products;

    //added here userid in order to use it in Details_user to add to cart
    public ProductAdapter_user(Context context, ArrayList<Product> products, int user_id) {
        this.context = context;
        this.products = products;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public ProductAdapter_user.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.product_list_user,parent,false);

        return new ProductAdapter_user.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ProductAdapter_user.MyViewHolder holder, final int position) {
        holder.title.setText(products.get(position).getName());
        holder.price.setText("$"+products.get(position).getPrice());
        if(!products.get(position).getImage().isEmpty()) {
            Glide.with(context).load(products.get(position).getImage()).into(holder.image);
        }
        //holder.image.set("#" + String.valueOf(position+1));

    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title, price;
        private ImageView edit_cat, delete_cat,image;

        public MyViewHolder (View view){
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.cat_name);
            price = view.findViewById(R.id.cat_price);
            image = view.findViewById(R.id.product_img);
        }

        public void onClick(View v) {

            int position = getAdapterPosition();
            Product product = products.get(position);
            Intent intent = new Intent(context , Details_User.class);
            intent.putExtra("pid",product.getPid());
            intent.putExtra("name",product.getName());
            intent.putExtra("price",product.getPrice());
            intent.putExtra("category",product.getCategory());
            intent.putExtra("image",product.getImage());
            intent.putExtra("user_id",user_id);
            context.startActivity(intent);
        }

//        MyViewHolder.itemView.setOnClickListener {
//            //we can then create an intent here and start a new activity
//            //with our data
//        }
    }
}
