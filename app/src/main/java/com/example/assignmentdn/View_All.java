package com.example.assignmentdn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class View_All extends AppCompatActivity {

    DB_Operations dbOperations;
    ArrayList<Product> SimpleViewAllProduct;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        // Retrieve the user from the intent
        Intent intent = getIntent();
        user = intent.getStringExtra("username");

        ListView lstProducts = findViewById(R.id.lstViewProduct);
        dbOperations = new DB_Operations(this);
        SimpleViewAllProduct = dbOperations.SimpleViewAllProduct();

        if (SimpleViewAllProduct != null) {
            SimpleProductAdapter simpleProductAdapter = new SimpleProductAdapter(this, SimpleViewAllProduct);
            lstProducts.setAdapter(simpleProductAdapter);
        }
    }
    public void UpdateUser(View view) {
        Intent intent = new Intent(this, UpdateUser.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void ViewProduct(View view) {
        Intent intent = new Intent(this, ViewProducts.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void ViewPost(View view) {
        Intent intent = new Intent(this, View_Post.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void ViewCartList(View view) {
        Intent intent = new Intent(this, ViewCart.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }


}
