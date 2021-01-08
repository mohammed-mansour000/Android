package com.example.final_project.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.User_pages.Details_User;
import com.example.final_project.User_pages.Details_cart;
import com.example.final_project.model.Category;
import com.example.final_project.model.Order;
import com.example.final_project.model.Product;
import com.example.final_project.model.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter_cart extends RecyclerView.Adapter<ProductAdapter_cart.MyViewHolder> {
    private Context context;
    private ArrayList<Order> orders;

    public ProductAdapter_cart(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ProductAdapter_cart.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.shopping_cart, parent, false);

        return new ProductAdapter_cart.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter_cart.MyViewHolder holder, final int position) {
        holder.title.setText(orders.get(position).getName());
        holder.price.setText("$" + orders.get(position).getPrice());
        // holder.no.setText("#" + String.valueOf(position+1));

        if(!orders.get(position).getImage().isEmpty()) {
            Glide.with(context).load(orders.get(position).getImage()).into(holder.image);
        }

        holder.delete_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int order_id = orders.get(position).getOrder_id();
                // String  value = products.get(position).getCategory();
                deleteCategory(order_id);
            }
        });

    }

    private void deleteCategory(final int id) {
        TextView close, title;
        Button delete_order;
        final Dialog dialog;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_cat);

        title = dialog.findViewById(R.id.tt);
        close = dialog.findViewById(R.id.close);

        title.setText("Delete Product");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        delete_order = dialog.findViewById(R.id.submit);


        delete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit(dialog,id);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void Submit( final Dialog dialog, final int id) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.url_deleteOrder, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getBoolean("error") == false){
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("order_id", String.valueOf(id));

                    return params;
                }
            };

            Volley.newRequestQueue(context).add(stringRequest);


    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, price;
        private ImageView edit_cat, delete_cat,image;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.cat_name);
            price = view.findViewById(R.id.cat_price);
            delete_cat = view.findViewById(R.id.delete_cat);
            image = view.findViewById(R.id.product_img);
        }

        public void onClick(View v) {

            int position = getAdapterPosition();
            Order order = orders.get(position);
            Intent intent = new Intent(context, Details_cart.class);
            intent.putExtra("pid", order.getPid());
            intent.putExtra("name", order.getName());
            intent.putExtra("price", order.getPrice());
            intent.putExtra("category", order.getCategory());
            intent.putExtra("image",order.getImage());
            intent.putExtra("user_id", order.getUser_id());
            intent.putExtra("order_id", order.getOrder_id());
            context.startActivity(intent);
        }


    }
}
