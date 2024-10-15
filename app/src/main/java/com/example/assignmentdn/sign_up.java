package com.example.assignmentdn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class sign_up extends AppCompatActivity {

    EditText txtuser, txtmobile, txtaddre, txtpass;
    DB_Operations db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);


        txtuser =findViewById(R.id.txtUser);
        txtmobile = findViewById(R.id.txtMobile);
        txtaddre = findViewById(R.id.txtAddress);
        txtpass = findViewById(R.id.txtPassword);

        db = new DB_Operations(this);

    }

    public void insertuser(View view){
        String user = txtuser.getText().toString();
        String mobileStr = txtmobile.getText().toString();
        String address= txtaddre.getText().toString();
        String pass = txtpass.getText().toString();


        if (user.isEmpty() || mobileStr.isEmpty() || address.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }



        User u = new User();
        u.setUsername(user);
        u.setMobile(Integer.parseInt(mobileStr));
        u.setAddress(address);
        u.setPassword(pass);

        try{
            db.createUser(u);
            Toast.makeText(this, "User Account Created", Toast.LENGTH_SHORT).show();
        }catch(Exception ex){
            Toast.makeText(this, "Error"+ ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void signin(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void clear(View view){
        txtuser.setText(null);
        txtmobile.setText(null);
        txtaddre.setText(null);
        txtpass.setText(null);
    }
}