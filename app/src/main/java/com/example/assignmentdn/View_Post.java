package com.example.assignmentdn;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class View_Post extends AppCompatActivity {

    DB_Operations dbOperations;
    ArrayList<Post> postArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_post);

        ListView lstpost = findViewById(R.id.lstPost);
        dbOperations = new DB_Operations(this);
        postArrayList = dbOperations.ViewAllPost();

        if (postArrayList != null){
           PostAdapter postAdapter = new PostAdapter(this,postArrayList);
           lstpost.setAdapter(postAdapter);
        }

    }
}