/*package com.example.assignmentdn;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class ViewUsers extends AppCompatActivity {

    DB_Operations dbOperations;
    ArrayList<String>usernameList;
    EditText txtusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_users);

        dbOperations = new DB_Operations(this);
        usernameList = new ArrayList<>();

        txtusername = findViewById(R.id.txtUserName);
        ListView lstUsers = findViewById(R.id.lstUser);

        ArrayList<User>userList = dbOperations.getAllUsers();

        if (userList != null){
            for (User user : userList){
                usernameList.add(user.getUsername());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernameList);

            lstUsers.setAdapter(adapter);

            lstUsers.setAdapter(adapter);
            lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String username = adapterView.getItemAtPosition(i).toString();
                    txtusername.setText(username);
                }
            });
        }
    }

    public void delete(View view){
        try{
            String username = txtusername.getText().toString();
            if (!username.isEmpty()){
                if (dbOperations.deleteuser(username)>0){
                    Toast.makeText(this,"User Deleted",Toast.LENGTH_SHORT).show();
                }
            }else {
                txtusername.setError("Please select or input username");
            }
        }catch (Exception ex){
            Toast.makeText(this,"Error" + ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
*/
