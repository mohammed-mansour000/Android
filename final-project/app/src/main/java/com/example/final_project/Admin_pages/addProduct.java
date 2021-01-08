package com.example.final_project.Admin_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project.R;
import com.example.final_project.model.Url;
import com.example.final_project.model.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class addProduct extends AppCompatActivity {

    private EditText edID, edName, edQuantity, edPrice;
    private Spinner spCategory;
    private ProgressBar prog2;
    private Button btAdd;
    private RequestQueue queue;
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayAdapter<Category> CategoryAdapter;
    private int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        edName = findViewById(R.id.edName);
        edPrice = findViewById(R.id.edPrice);
        spCategory = findViewById(R.id.spCategories);
        prog2 = findViewById(R.id.prog2);
        btAdd = findViewById(R.id.btAdd);

        queue = Volley.newRequestQueue(this);

        fillCategories(categories);
        CategoryAdapter = new ArrayAdapter<Category>(this, R.layout.spinner_style, categories);

        spCategory.setAdapter(CategoryAdapter);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prog2.setVisibility(View.VISIBLE);
                btAdd.setEnabled(false);

                StringRequest request = new StringRequest(Request.Method.POST, Url.url_addProduct, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(addProduct.this, response, Toast.LENGTH_SHORT).show();
                        prog2.setVisibility(View.INVISIBLE);
                        btAdd.setEnabled(true);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addProduct.this, error.toString(), Toast.LENGTH_SHORT).show();
                        prog2.setVisibility(View.INVISIBLE);
                        btAdd.setEnabled(true);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", edName.getText().toString());
                        params.put("price", edPrice.getText().toString());
                        params.put("cid", String.valueOf(categories.get(index).getCid()));
                        params.put("key", "cuBubcDE");
                        return params;
                    }
                };

                queue.add(request);


            }
        });



    }

    public void fillCategories(final ArrayList<Category> categories) {


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

                    Toast.makeText(addProduct.this, "No records found", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addProduct.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
