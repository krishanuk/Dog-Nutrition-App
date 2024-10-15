package com.example.assignmentdn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewProducts extends AppCompatActivity {

    DB_Operations dbOperations;
    ArrayList<Product> productArrayList;
    private String user;

    Spinner categorySpinner;
    ListView lstProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_products);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");

        lstProducts = findViewById(R.id.lstProduct);
        categorySpinner = findViewById(R.id.spinnerCategory);

        dbOperations = new DB_Operations(this);

        ArrayList<String> categories = dbOperations.getAllCategories();
        categories.add(0, "All Products");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                if (selectedCategory.equals("All Products")) {
                    productArrayList = dbOperations.ViewAllProduct();
                } else {
                    productArrayList = dbOperations.getProductsByType(selectedCategory); 
                }
                if (productArrayList != null) {
                    ProductAdapter productAdapter = new ProductAdapter(ViewProducts.this, productArrayList, user);
                    lstProducts.setAdapter(productAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        productArrayList = dbOperations.ViewAllProduct();
        if (productArrayList != null) {
            ProductAdapter productAdapter = new ProductAdapter(ViewProducts.this, productArrayList, user);
            lstProducts.setAdapter(productAdapter);
        }
    }
}
