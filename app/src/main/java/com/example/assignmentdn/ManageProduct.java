package com.example.assignmentdn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ManageProduct extends AppCompatActivity {

    EditText txtid, txtname, txtprice, txtbrand, txttype, txtage, txtdescription;
    ImageView img;
    byte[] imageByte;

    DB_Operations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_product);

        dbOperations = new DB_Operations(this);

        txtid = findViewById(R.id.txtProductID);
        txtname = findViewById(R.id.txtProductName);
        txtprice = findViewById(R.id.txtProductPrice);
        txtbrand = findViewById(R.id.txtProductBrand);
        txttype = findViewById(R.id.txtProductType);
        txtage = findViewById(R.id.txtProductAge);
        txtdescription = findViewById(R.id.txtProductDescription);
        img = findViewById(R.id.imgProduct);

    }

    public void selectImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 170);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent,"Select Product Image"), 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111){
            if (data != null){
                Uri uri =data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                    imageByte = byteArrayOutputStream.toByteArray();
                    img.setImageBitmap(bitmap);

                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        }
    }

    public void  insertProduct(View view){

        String Validid = txtid.getText().toString();
        String Validname = txtname.getText().toString();
        String Validprice = txtprice.getText().toString();
        String Validbrand = txtbrand.getText().toString();
        String Validtype = txttype.getText().toString();
        String Validage = txtage.getText().toString();
        String Validdescription = txtdescription.getText().toString();

        if (Validid.isEmpty() || Validname.isEmpty() || Validprice.isEmpty() || Validbrand.isEmpty() ||
                Validtype.isEmpty() || Validage.isEmpty() || Validdescription.isEmpty() || imageByte == null) {
            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }


        Product product = new Product();
        product.setpID(Integer.parseInt(txtid.getText().toString()));
        product.setpName(txtname.getText().toString());
        product.setpPrice(Double.parseDouble(txtprice.getText().toString()));
        product.setpBrand(txtbrand.getText().toString());
        product.setpType(txttype.getText().toString());
        product.setpAge(Integer.parseInt(txtage.getText().toString()));
        product.setpDescription(txtdescription.getText().toString());
        product.setpImage(imageByte);

        try {
            dbOperations.insertProduct(product);
            Toast.makeText(this, "Product Details Inserted", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cleare(View view){
        txtid.setText(null);
        txtname.setText(null);
        txtprice.setText(null);
        txtbrand.setText(null);
        txttype.setText(null);
        txtage.setText(null);
        txtdescription.setText(null);
        img.setImageDrawable(null);
    }


    public void deleteProduct(View view) {
        String idText = txtid.getText().toString();
        if (idText.isEmpty()) {
            Toast.makeText(this, "Please enter a Product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int productID = Integer.parseInt(idText);
        int rowsAffected = dbOperations.deleteProduct(productID);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
            cleare(null);
        } else {
            Toast.makeText(this, "No Product Found with the given ID", Toast.LENGTH_SHORT).show();
        }
    }

}