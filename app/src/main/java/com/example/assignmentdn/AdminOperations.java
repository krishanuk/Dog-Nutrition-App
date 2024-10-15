package com.example.assignmentdn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminOperations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_operations);

    }

    public void manageProduct(View view){
        Intent intent = new Intent(this, ManageProduct.class);
        startActivity(intent);
    }
    public void manageContent(View view){
        Intent intent = new Intent(this, AddPost.class);
        startActivity(intent);
    }
}