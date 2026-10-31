package com.example.medapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medapp.R;
import com.example.medapp.models.Medicine;

public class MedicineDetailActivity extends AppCompatActivity {

    TextView txtName, txtPrice, txtDescription;
    Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);

        medicine = (Medicine) getIntent().getSerializableExtra("medicine");

        if (medicine != null) {

            txtName.setText(medicine.getName());
            txtPrice.setText(String.valueOf(medicine.getPrice()));

            // Safe description
            txtDescription.setText(medicine.getName());

        } else {

            txtDescription.setText("Medicine details not available");

        }
    }
}