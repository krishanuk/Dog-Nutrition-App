package com.example.assignmentdn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Cart> cartArrayList;
    private DB_Operations dbOperations;
    private OnCartUpdatedListener cartUpdatedListener;

    public CartAdapter(Context context, ArrayList<Cart> cartArrayList, OnCartUpdatedListener cartUpdatedListener) {
        this.context = context;
        this.cartArrayList = cartArrayList;
        this.dbOperations = new DB_Operations(context); // Initialize DB_Operations
        this.cartUpdatedListener = cartUpdatedListener;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_cart_item, parent, false);
        }

        TextView productName = convertView.findViewById(R.id.lblItemName);
        TextView productQuantity = convertView.findViewById(R.id.lblItemQuantity);
        TextView productPrice = convertView.findViewById(R.id.lblcartItemPrice);
        ImageView deleteItem = convertView.findViewById(R.id.imgRemove);

        final Cart cartItem = cartArrayList.get(position);

        productName.setText(cartItem.getProductName());
        productQuantity.setText("Quantity: " + cartItem.getQuantity());

        // get porduct price in database
        double price = getProductPriceById(cartItem.getProductID());
        productPrice.setText("Price: $" + String.format("%.2f", price));


        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the item from the database
                dbOperations.deleteCartItem(cartItem.getCartID());


                cartArrayList.remove(position);
                notifyDataSetChanged();


                if (cartUpdatedListener != null) {
                    cartUpdatedListener.onCartUpdated();
                }


                Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private double getProductPriceById(int productId) {
        return dbOperations.getProductPriceById(productId);
    }

    public interface OnCartUpdatedListener {
        void onCartUpdated();
    }
}
