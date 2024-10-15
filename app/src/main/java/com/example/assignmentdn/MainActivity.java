package com.example.assignmentdn;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


import com.example.assignmentdn.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

/*
        binding.bottomNavigationView.setOnItemSelectedListener(item-> {
            switch (item.getItemId()){
                case androidx.appcompat.R.id.home:{
                    startActivity(new Intent(MainActivity.this, ManageProduct.class));
                    return true;
                }
            }

            return true;
        });
*/

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
    }


    public void UpdateUser(View view) {
        Intent intent = new Intent(this, UpdateUser.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void manageproduct(View view) {
        Intent intent = new Intent(this, ManageProduct.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void ViewProduct(View view) {
        Intent intent = new Intent(this, ViewProducts.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void AddPost(View view) {
        Intent intent = new Intent(this, AddPost.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void ViewPost(View view) {
        Intent intent = new Intent(this, View_Post.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }
}