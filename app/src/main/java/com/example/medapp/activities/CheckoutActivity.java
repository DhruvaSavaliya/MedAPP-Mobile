package com.example.medapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medapp.R;
import com.example.medapp.models.CartManager;
import com.example.medapp.models.Medicine;
import com.example.medapp.models.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etName, etPhone, etAddress;
    private Button btnConfirm;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnConfirm = findViewById(R.id.btnConfirmOrder);

        ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

        // Pre-fill fields for testing
        etName.setText("Test User");
        etPhone.setText("1234567890");
        etAddress.setText("123 Test Street");
        // ===========================

        btnConfirm.setOnClickListener(v -> placeOrder());
    }

    private void placeOrder() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Medicine> cartItems = new ArrayList<>(CartManager.getInstance().getCartItems());
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        int total = CartManager.getInstance().getTotal();
        String orderId = ordersRef.push().getKey();
        if (orderId == null) {
            Toast.makeText(this, "Error generating order ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Order order = new Order(orderId, name, phone, address, cartItems, total, System.currentTimeMillis());

        ordersRef.child(orderId).setValue(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_LONG).show();
                    CartManager.getInstance().clearCart();

                    Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}