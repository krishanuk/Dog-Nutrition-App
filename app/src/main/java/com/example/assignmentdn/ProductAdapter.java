package com.example.assignmentdn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> productArrayList;
    private String username;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList, String username) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.username = username;
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return productArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_product_view, viewGroup, false);

        TextView lblName = customView.findViewById(R.id.customProductName);
        TextView lblPrice = customView.findViewById(R.id.lblCustomProductPrice);
        TextView lblID = customView.findViewById(R.id.lblCustomeProductID);
        TextView lblDescrip = customView.findViewById(R.id.lblCustomProductDescription);
        ImageView imgProduct = customView.findViewById(R.id.imgCustomProduct);
        EditText txtQuantity = customView.findViewById(R.id.txtquantity);
        Button btnAddToCart = customView.findViewById(R.id.btnAddToCart);

        Product product = productArrayList.get(i);

        lblName.setText(product.getpName());
        lblPrice.setText("$" + product.getpPrice());
        lblID.setText(String.valueOf(product.getpID()));
        lblDescrip.setText(product.getpDescription());
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getpImage(), 0, product.getpImage().length);
        imgProduct.setImageBitmap(bitmap);

        btnAddToCart.setOnClickListener(v -> {
            String quantityText = txtQuantity.getText().toString();
            if (!quantityText.isEmpty()) {
                int quantity = Integer.parseInt(quantityText);
                DB_Operations dbOperations = new DB_Operations(context);
                dbOperations.addToCart(username, product.getpID(), quantity, product.getpName());
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Please enter a quantity", Toast.LENGTH_SHORT).show();
            }
        });

        return customView;
    }
}
