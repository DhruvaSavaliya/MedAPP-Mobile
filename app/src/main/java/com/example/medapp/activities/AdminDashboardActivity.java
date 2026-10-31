package com.example.medapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medapp.R;
import com.example.medapp.utils.FirebaseHelper;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnAddMedicine, btnManageOrders, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Role protection (Security)
        String role = getIntent().getStringExtra("role");

        if (role == null || !role.equals("admin")) {
            Toast.makeText(this, "Unauthorized Access", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Views
        btnAddMedicine = findViewById(R.id.btnAddMedicine);
        btnManageOrders = findViewById(R.id.btnManageOrders);
        btnLogout = findViewById(R.id.btnLogout);

        // Add Medicine
        btnAddMedicine.setOnClickListener(v ->
                startActivity(new Intent(this, AddMedicineActivity.class)));

        // Manage Orders
        btnManageOrders.setOnClickListener(v ->
                startActivity(new Intent(this, ManageOrdersActivity.class)));

        // Logout
        btnLogout.setOnClickListener(v -> {

            FirebaseHelper.getAuth().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        });
    }
}