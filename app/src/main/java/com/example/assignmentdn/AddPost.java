package com.example.assignmentdn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPost extends AppCompatActivity {

    EditText title, desc;
    Button add;
    ImageView image;

    byte[] imageByte;

    DB_Operations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        title = findViewById(R.id.txtContentTitle);
        desc = findViewById(R.id.txtContentDescri);
        image = findViewById(R.id.imgContent);
        add = findViewById(R.id.btnAddContent);

        dbOperations = new DB_Operations(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPost(v);
            }
        });
    }

    public void selectimg(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Content Image"), 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                imageByte = byteArrayOutputStream.toByteArray();
                image.setImageBitmap(bitmap);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    public void insertPost(View view) {

        String postTitle = title.getText().toString();
        String postDesc = desc.getText().toString();

        if (postTitle.isEmpty() || postDesc.isEmpty() || imageByte == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        Post post = new Post();
        post.seteTitle(title.getText().toString());
        post.seteDescription(desc.getText().toString());
        post.seteImage(imageByte);

        try {
            dbOperations.createPost(post);
            Toast.makeText(this, "Post Details Inserted", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deletePost(View view) {
        String postTitle = title.getText().toString();

        if (postTitle.isEmpty()) {
            Toast.makeText(this, "Please enter a title to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        int rowsAffected = dbOperations.deletePost(postTitle);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
            title.setText("");
            desc.setText("");
            image.setImageResource(0);
            imageByte = null;
        } else {
            Toast.makeText(this, "No Post Found with the given title", Toast.LENGTH_SHORT).show();
        }
    }
}
