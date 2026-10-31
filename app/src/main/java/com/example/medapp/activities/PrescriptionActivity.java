package com.example.medapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medapp.R;
import com.example.medapp.adapters.MedicineAdapter;
import com.example.medapp.models.CartManager;
import com.example.medapp.models.Medicine;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtResult;
    private RecyclerView recyclerView;

    private List<Medicine> medicineList = new ArrayList<>();
    private List<Medicine> matchedList = new ArrayList<>();
    private MedicineAdapter adapter;

    private ActivityResultLauncher<String> imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        imageView = findViewById(R.id.imageView);
        txtResult = findViewById(R.id.txtResult);
        recyclerView = findViewById(R.id.recyclerView);

        // Load all medicines
        loadMedicines();

        // Adapter with Add to Cart
        adapter = new MedicineAdapter(this, matchedList, medicine -> {
            CartManager.getInstance().addToCart(medicine);
            Toast.makeText(this,
                    medicine.getName() + " added to cart",
                    Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Image picker for gallery
        imagePicker = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                        processImage(uri);
                    }
                });

        findViewById(R.id.btnSelect).setOnClickListener(v ->
                imagePicker.launch("image/*"));
    }

    // Load all medicines
    private void loadMedicines() {
        medicineList.clear();

        medicineList.add(new Medicine("Amoxicillin", "Antibiotic", 120, 10, "med1"));
        medicineList.add(new Medicine("Antacid", "Digestive", 50, 10, "med2"));
        medicineList.add(new Medicine("Aspirin", "Painkiller", 40, 10, "med3"));
        medicineList.add(new Medicine("Cetirizine", "Allergy", 60, 10, "med4"));
        medicineList.add(new Medicine("Dextro", "Energy", 30, 10, "med5"));
        medicineList.add(new Medicine("Famotidine", "Acidity", 70, 10, "med6"));
        medicineList.add(new Medicine("Ibuprofen", "Painkiller", 80, 10, "med7"));
        medicineList.add(new Medicine("Paracetamol", "Fever", 50, 10, "med8"));

        medicineList.add(new Medicine("Diabetes", "Condition", 0, 0, "med9"));
        medicineList.add(new Medicine("Insulin", "Hormone", 200, 10, "med10"));
        medicineList.add(new Medicine("Blood Glucose", "Test", 0, 0, "med11"));

        medicineList.add(new Medicine("Apple Cider Vinegar", "Supplement", 150, 10, "med12"));
        medicineList.add(new Medicine("Vitamin B Complex", "Supplement", 120, 10, "med13"));
        medicineList.add(new Medicine("Calcium", "Supplement", 90, 10, "med14"));
        medicineList.add(new Medicine("Iron", "Supplement", 80, 10, "med15"));
        medicineList.add(new Medicine("Magnesium", "Supplement", 110, 10, "med16"));
        medicineList.add(new Medicine("Multivitamin", "Supplement", 140, 10, "med17"));
        medicineList.add(new Medicine("Vitamin C", "Supplement", 100, 10, "med18"));
        medicineList.add(new Medicine("Vitamin D3", "Supplement", 130, 10, "med19"));
        medicineList.add(new Medicine("Zinc", "Supplement", 70, 10, "med20"));

        medicineList.add(new Medicine("Omega 3", "Supplement", 160, 10, "med21"));
        medicineList.add(new Medicine("Protein", "Supplement", 300, 10, "med22"));
        medicineList.add(new Medicine("Gummies", "Supplement", 90, 10, "med23"));
        medicineList.add(new Medicine("Cozyrich", "Supplement", 200, 10, "med24"));
    }

    // OCR processing
    private void processImage(Uri uri) {
        try {
            InputImage image = InputImage.fromFilePath(this, uri);

            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    .process(image)
                    .addOnSuccessListener(text -> {
                        String extractedText = text.getText();
                        txtResult.setText(extractedText);
                        searchMedicines(extractedText);
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "OCR Failed", Toast.LENGTH_SHORT).show());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Search medicines (fuzzy + partial match)
    private void searchMedicines(String text) {
        matchedList.clear();
        text = text.toLowerCase().replaceAll("[^a-z0-9 ]", " ");
        String[] words = text.split("\\s+");

        for (Medicine med : medicineList) {
            String medName = med.getName().toLowerCase();

            if (text.contains(medName)) {
                addMedicine(med);
                continue;
            }

            String[] medWords = medName.split(" ");
            for (String word : words) {
                if (word.length() < 3) continue;

                if (medName.contains(word) || word.contains(medName)) {
                    addMedicine(med);
                    break;
                }

                for (String medWord : medWords) {
                    if (isSimilar(word, medWord)) {
                        addMedicine(med);
                        break;
                    }
                }
            }
        }

        adapter.notifyDataSetChanged();

        if (matchedList.isEmpty()) {
            Toast.makeText(this, "No medicines found", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMedicine(Medicine med) {
        if (!matchedList.contains(med)) {
            matchedList.add(med);
        }
    }

    private boolean isSimilar(String a, String b) {
        return levenshteinDistance(a, b) <= 2;
    }

    private int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }
        return dp[a.length()][b.length()];
    }
}