package com.example.medapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medapp.R;
import com.example.medapp.utils.FirebaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    private EditText searchBar;
    private BottomNavigationView bottomNavigation;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Admin redirect
        String role = getIntent().getStringExtra("role");
        if ("admin".equals(role)) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
            finish();
            return;
        }

        // Initialize
        searchBar = findViewById(R.id.search_bar);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        gridLayout = findViewById(R.id.gridLayout);

        // Prescription
        findViewById(R.id.card_prescription).setOnClickListener(v ->
                startActivity(new Intent(this, PrescriptionActivity.class)));

        findViewById(R.id.btn_upload).setOnClickListener(v ->
                startActivity(new Intent(this, PrescriptionActivity.class)));

        // Categories
        String[] categories = {
                "OTC Medicines", "Vitamins", "Personal Care", "Baby Care",
                "Wellness", "Chronic Care", "Health Tips", "Herbal"
        };

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            int index = i;
            MaterialCardView card = (MaterialCardView) gridLayout.getChildAt(i);
            card.setOnClickListener(v -> openCategory(categories[index]));
        }

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) return true;

            if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            }

            if (id == R.id.nav_orders) {
                startActivity(new Intent(this, OrderHistoryActivity.class));
                return true;
            }

            if (id == R.id.nav_profile) {
                showLogoutDialog();
                return true;
            }

            return false;
        });
    }

    private void openCategory(String category) {
        Intent intent = new Intent(this, MedicineListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Logout", (dialog, which) -> {

                    FirebaseHelper.getAuth().signOut();

                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}