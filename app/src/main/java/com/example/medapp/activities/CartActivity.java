package com.example.medapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medapp.R;
import com.example.medapp.adapters.CartAdapter;
import com.example.medapp.models.CartManager;
import com.example.medapp.models.Medicine;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Medicine> cartItems;
    private TextView totalAmountText;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        totalAmountText = findViewById(R.id.textViewTotalAmount);
        btnCheckout = findViewById(R.id.buttonCheckout);

        cartItems = CartManager.getInstance().getCartItems();

        adapter = new CartAdapter(this, cartItems, new CartAdapter.CartListener() {
            @Override
            public void onRemove(Medicine medicine) {
                CartManager.getInstance().removeFromCart(medicine);
                adapter.notifyDataSetChanged();
                updateTotal();
                Toast.makeText(CartActivity.this, medicine.getName() + " removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onQuantityChange(Medicine medicine, int newQty) {
                CartManager.getInstance().updateQuantity(medicine, newQty);
                adapter.notifyDataSetChanged();
                updateTotal();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotal();

        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

    // ✅ IMPORTANT FIX
    @Override
    protected void onResume() {
        super.onResume();

        cartItems = CartManager.getInstance().getCartItems();
        adapter.notifyDataSetChanged();
        updateTotal();
    }

    private void updateTotal() {
        int total = CartManager.getInstance().getTotal();
        totalAmountText.setText("Total: ₹" + total);
    }
}