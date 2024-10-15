package com.example.assignmentdn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewCart extends AppCompatActivity implements CartAdapter.OnCartUpdatedListener {

    DB_Operations dbOperations;
    ArrayList<Cart> cartItemList;
    CartAdapter cartAdapter;
    private String user;
    private TextView totalPriceTextView;
    private ListView cartListView;
    private Button btnProceedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        user = getIntent().getStringExtra("username");

        cartListView = findViewById(R.id.lstCart);
        totalPriceTextView = findViewById(R.id.lblSubTotal);
        btnProceedOrder = findViewById(R.id.btnProceedOrder);

        dbOperations = new DB_Operations(this);
        loadCartItems();

        btnProceedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedOrder();
            }
        });
    }

    private void loadCartItems() {
        cartItemList = dbOperations.ViewCart(user);

        if (cartItemList != null) {
            cartAdapter = new CartAdapter(this, cartItemList, this);
            cartListView.setAdapter(cartAdapter);

            // Calculate total price
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice(cartItemList);
        totalPriceTextView.setText("Total: $" + String.format("%.2f", totalPrice));
    }

    private double calculateTotalPrice(ArrayList<Cart> cartItemList) {
        double totalPrice = 0;
        for (Cart cartItem : cartItemList) {
            double productPrice = getProductPriceById(cartItem.getProductID());
            totalPrice += productPrice * cartItem.getQuantity();
        }
        return totalPrice;
    }

    private double getProductPriceById(int productId) {
        return dbOperations.getProductPriceById(productId);
    }

    private void proceedOrder() {
        String totalPriceText = totalPriceTextView.getText().toString().replace("Total: $", "");
        double totalPrice = Double.parseDouble(totalPriceText);

        StringBuilder orderDetails = new StringBuilder();
        for (Cart item : cartItemList) {
            orderDetails.append(item.getProductName()).append(" (").append(item.getQuantity()).append("), ");
        }

        if (orderDetails.length() > 0) {
            orderDetails.setLength(orderDetails.length() - 2);
        }

        dbOperations.createOrder(user, totalPrice, orderDetails.toString());

        Toast.makeText(this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();

        for (Cart item : cartItemList) {
            dbOperations.deleteCartItem(item.getCartID());
        }

        loadCartItems();
    }

    @Override
    public void onCartUpdated() {
        updateTotalPrice();
    }
}
