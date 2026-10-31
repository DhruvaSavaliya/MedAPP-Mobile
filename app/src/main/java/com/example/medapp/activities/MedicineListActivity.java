package com.example.medapp.activities;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medapp.R;
import com.example.medapp.adapters.MedicineAdapter;
import com.example.medapp.models.CartManager;
import com.example.medapp.models.Medicine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicineAdapter medicineAdapter;
    private List<Medicine> medicines;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        recyclerView = findViewById(R.id.recyclerViewMedicines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        category = getIntent().getStringExtra("category");

        medicines = getMedicinesForCategory(category);

        // ✅ FINAL FIXED ADAPTER (NEW OBJECT FOR CART)
        medicineAdapter = new MedicineAdapter(this, medicines, medicine -> {

            Medicine newMedicine = new Medicine(
                    medicine.getName(),
                    medicine.getCategory(),
                    medicine.getPrice(),
                    1,
                    medicine.getImage()
            );

            CartManager.getInstance().addToCart(newMedicine);

            Toast.makeText(this,
                    medicine.getName() + " added to cart",
                    Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(medicineAdapter);
    }

    private List<Medicine> getMedicinesForCategory(String category) {

        List<Medicine> list = new ArrayList<>();
        AssetManager assetManager = getAssets();
        String folderPath = "";

        // 🟩 HEALTH TIPS
        if (category.equals("Health Tips")) {

            List<Medicine> tips = new ArrayList<>();

            tips.add(new Medicine("Hydration", category, "images/healthtips/hydration.jpg",
                    "Drink enough water daily to stay hydrated."));

            tips.add(new Medicine("Sleep", category, "images/healthtips/sleep.jpg",
                    "Maintain proper sleep schedule for good health."));

            tips.add(new Medicine("Posture", category, "images/healthtips/posture.jpg",
                    "Maintain correct posture to avoid strain."));

            tips.add(new Medicine("Nutrition", category, "images/healthtips/nutrition.jpg",
                    "Eat balanced diet for better health."));

            tips.add(new Medicine("Exercise", category, "images/healthtips/exercise.jpg",
                    "Exercise daily to stay fit."));

            tips.add(new Medicine("Sun Safety", category, "images/healthtips/sun.jpg",
                    "Use sunscreen to protect skin."));

            tips.add(new Medicine("Hygiene", category, "images/healthtips/hygiene.jpg",
                    "Maintain personal hygiene."));

            tips.add(new Medicine("Dental Care", category, "images/healthtips/dental.jpg",
                    "Brush and floss daily."));

            return tips;
        }

        // 🟦 CATEGORY IMAGES FROM ASSETS
        switch (category) {
            case "OTC Medicines": folderPath = "images/OTC_Medicines"; break;
            case "Vitamins": folderPath = "images/Vitamins"; break;
            case "Wellness": folderPath = "images/wellness"; break;
            case "Baby Care": folderPath = "images/babycare"; break;
            case "Personal Care": folderPath = "images/personalcare"; break;
            case "Chronic Care": folderPath = "images/chroniccare"; break;
            case "Herbal": folderPath = "images/herbal"; break;
            default: folderPath = "images/OTC_Medicines";
        }

        try {
            String[] files = assetManager.list(folderPath);

            if (files != null) {

                int price = 120;

                for (String fileName : files) {

                    if (!fileName.startsWith(".") &&
                            (fileName.endsWith(".png") || fileName.endsWith(".jpg"))) {

                        String name = fileName.substring(0, fileName.lastIndexOf('.'))
                                .replace("_", " ");

                        name = name.substring(0, 1).toUpperCase() + name.substring(1);

                        String fullPath = folderPath + "/" + fileName;

                        list.add(new Medicine(
                                name,
                                category,
                                price,
                                1,
                                fullPath
                        ));

                        price += 20;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load medicines", Toast.LENGTH_SHORT).show();
        }

        return list;
    }
}