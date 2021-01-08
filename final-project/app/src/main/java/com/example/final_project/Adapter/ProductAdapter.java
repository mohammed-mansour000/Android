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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.Admin_pages.Details_admin;
import com.example.final_project.R;
import com.example.final_project.User_pages.Details_User;
import com.example.final_project.User_pages.Details_cart;
import com.example.final_project.model.Url;
import com.example.final_project.model.Category;
import com.example.final_project.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    private int index;
    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.product_list_admin,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(products.get(position).getName());
        holder.no.setText("#" + String.valueOf(position+1));

        holder.edit_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = products.get(position).getPid();
                String  name = products.get(position).getName();
                double  price = products.get(position).getPrice();
                editCategory(id, name, price);
            }
        });

        holder.delete_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = products.get(position).getPid();
               // String  value = products.get(position).getCategory();
                deleteCategory(id);
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title, no;
        private ImageView edit_cat, delete_cat;

        public MyViewHolder (View view){
            super(view);
            no = view.findViewById(R.id.no);
            title = view.findViewById(R.id.cat_name);
            edit_cat = view.findViewById(R.id.edit_cat);
            delete_cat = view.findViewById(R.id.delete_cat);
        }


    }

    private void editCategory(final int id, String name, double price) {
        TextView close, title;
        final EditText cat_text,price_text;
        Button add_cat;
        final Dialog dialog;

        final ArrayList<Category> categories = new ArrayList<>();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.modcat);

        //fillCategories(categories,dialog);


////////////////////////////////////////////////////////////////////////////////////////////////////

        final ArrayAdapter<Category> CategoryAdapter;
        Spinner spCategory;

        spCategory = dialog.findViewById(R.id.spCategories);
        CategoryAdapter = new ArrayAdapter<Category>(context, R.layout.spinner_style, categories);
        spCategory.setAdapter(CategoryAdapter);
        JsonArrayRequest request = new JsonArrayRequest(Url.url_getCategory, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0;i < response.length();i++) {
                        JSONObject row = response.getJSONObject(i);
                        int cid = row.getInt("cid");
                        String name = row.getString("name");
                        categories.add(new Category(cid, name));
                    }
                    CategoryAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Toast.makeText(context, "No records found", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(context).add(request);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
               // Log.i("index",categories.get(index).getCid()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////


        title = dialog.findViewById(R.id.tt);
        close = dialog.findViewById(R.id.close);

        title.setText("Edit Product");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        add_cat = dialog.findViewById(R.id.update);
        cat_text = dialog.findViewById(R.id.cat_text);
        price_text = dialog.findViewById(R.id.price_text);

        cat_text.setText(name);
        price_text.setText(String.valueOf(price));

        add_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_name = cat_text.getText().toString();
                String new_price = price_text.getText().toString();
                Submit("PUT",new_name, new_price ,dialog,id,index,categories);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void deleteCategory(final int id) {
        TextView close, title;
        final EditText cat_text;
        Button add_cat;
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

        add_cat = dialog.findViewById(R.id.submit);


        add_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit("DELETE","", "" ,dialog,id,0,null);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void Submit(String method, final String new_name, final String new_price, final Dialog dialog, final int id, final int index, final ArrayList<Category> categories) {

        if(method == "PUT") {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.url_updateProduct, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    try {
                        Log.i("tagconvertstr", "["+response+"]");
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
                    Log.e("Volly Error", error.toString());

                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("pid", String.valueOf(id));
                    params.put("name", new_name);
                    params.put("price", new_price);
                    params.put("cid",  String.valueOf(categories.get(index).getCid()));
                    return params;
                }
            };

            Volley.newRequestQueue(context).add(stringRequest);

        }else if(method == "DELETE"){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.url_deleteProduct, new Response.Listener<String>() {
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
                    params.put("pid", String.valueOf(id));

                    return params;
                }
            };

            Volley.newRequestQueue(context).add(stringRequest);

        }
    }

//    public void fillCategories(final ArrayList<Category> categories, final Dialog dialog) {
//
//        final ArrayAdapter<Category> CategoryAdapter;
//
//        Spinner spCategory;
//
//        int index;
//
//        spCategory = dialog.findViewById(R.id.spCategories);
//
//        CategoryAdapter = new ArrayAdapter<Category>(context, R.layout.spinner_style, categories);
//
//        spCategory.setAdapter(CategoryAdapter);
//
//        JsonArrayRequest request = new JsonArrayRequest(Url.url_getCategory, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    for (int i = 0;i < response.length();i++) {
//                        JSONObject row = response.getJSONObject(i);
//                        int cid = row.getInt("cid");
//                        String name = row.getString("name");
//                        categories.add(new Category(cid, name));
//                    }
//                    CategoryAdapter.notifyDataSetChanged();
//
//                } catch (Exception ex) {
//
//                    Toast.makeText(context, "No records found", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Volley.newRequestQueue(context).add(request);
//    }
}
