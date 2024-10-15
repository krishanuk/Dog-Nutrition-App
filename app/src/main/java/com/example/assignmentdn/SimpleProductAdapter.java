package com.example.assignmentdn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SimpleProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> productArrayList;
    private String username;

    public SimpleProductAdapter(Context context, ArrayList<Product> productArrayList){
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

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_product_view_simple, viewGroup, false);
        }

        TextView lblName = view.findViewById(R.id.customProductNameSimple);
        TextView lblPrice = view.findViewById(R.id.customProductPriceSimple);
        ImageView imgProduct = view.findViewById(R.id.imgCustomProductSimple);
        ImageView AddToCart = view.findViewById(R.id.AddToCart);
        ImageView shop = view.findViewById(R.id.AddToCart);


        Product product = productArrayList.get(i);

        lblName.setText(product.getpName());
        lblPrice.setText("$" + product.getpPrice());
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getpImage(), 0, product.getpImage().length);
        imgProduct.setImageBitmap(bitmap);

       AddToCart.setTag(i);

        shop.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewProducts.class);
            intent.putExtra("username", username); // Pass the username
            context.startActivity(intent);
        });

        return view;
    }
}
