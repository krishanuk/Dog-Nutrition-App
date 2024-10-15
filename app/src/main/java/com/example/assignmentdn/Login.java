package com.example.assignmentdn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    DB_Operations dbOperations;
    EditText txtUser, txtPss;
    Button btnLogin;
    private int loginAttempts = 0;

    private final int MAX_ATTEMPTS = 3;
    private final int TIMEOUT_TIME = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = findViewById(R.id.txtUsername);
        txtPss = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);


        dbOperations = new DB_Operations(this);
    }

    public void loginProcess(View view) {

        String username = txtUser.getText().toString();
        String password = txtPss.getText().toString();


        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter Username & Password", Toast.LENGTH_SHORT).show();
        } else {

            if (dbOperations.checkLogin(username, password)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, View_All.class);

                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                loginAttempts++;


                if (loginAttempts >= MAX_ATTEMPTS) {
                    btnLogin.setEnabled(false);

                    Toast.makeText(this, "Too many attempts. Wait 30 seconds.", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnLogin.setEnabled(true);
                            loginAttempts = 0;
                        }
                    }, TIMEOUT_TIME);
                } else {
                    Toast.makeText(this, "Check Username & Password", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void signup(View view) {
        Intent intent = new Intent(this, sign_up.class);
        startActivity(intent);
    }
}
