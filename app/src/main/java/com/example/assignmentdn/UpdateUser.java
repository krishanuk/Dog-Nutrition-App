package com.example.assignmentdn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateUser extends AppCompatActivity {

    TextView lbluser;
    EditText txtMob, txtAddress, txtpass;
    DB_Operations dbOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);
        dbOperations = new DB_Operations(this);

        lbluser = findViewById(R.id.lblUsername);
        txtMob = findViewById(R.id.txtUpdateMobile);
        txtAddress = findViewById(R.id.txtUpdateAddress);
        txtpass = findViewById(R.id.txtUpdatePassword);

        String username = getIntent().getStringExtra("username");

        lbluser.setText(username);

        User user = dbOperations.findUser(username);
        txtMob.setText("" + user.getMobile());
        txtAddress.setText("" + user.getAddress());
        txtpass.setText(""+user.getPassword());

    }

    public void update(View view){
        User user = new User();
        user.setUsername(lbluser.getText().toString());
        user.setMobile(Integer.parseInt(txtMob.getText().toString()));
        user.setAddress(txtAddress.getText().toString());
        user.setPassword(txtpass.getText().toString());

        try{
            if (dbOperations.UpdateUser(user)>0){
                Toast.makeText(this,"User Details Updated", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void logout(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();


        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}