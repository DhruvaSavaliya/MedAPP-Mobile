package com.example.medapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.medapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddMedicineActivity extends AppCompatActivity {

    TextInputEditText edtName, edtDescription, edtPrice, edtStock, edtCategory, edtImageUrl;
    MaterialButton btnSave;
    ImageView imgMedicine;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        imgMedicine = findViewById(R.id.imgMedicine);

        edtImageUrl = findViewById(R.id.edtImageUrl);
        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDescription);
        edtPrice = findViewById(R.id.edtPrice);
        edtStock = findViewById(R.id.edtStock);
        edtCategory = findViewById(R.id.edtCategory);

        btnSave = findViewById(R.id.btnSave);

        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(v -> {

            String name = edtName.getText().toString();
            String description = edtDescription.getText().toString();
            String price = edtPrice.getText().toString();
            String stock = edtStock.getText().toString();
            String category = edtCategory.getText().toString();
            String imageUrl = edtImageUrl.getText().toString();

            HashMap<String,Object> medicine = new HashMap<>();
            medicine.put("name",name);
            medicine.put("description",description);
            medicine.put("price",price);
            medicine.put("stock",stock);
            medicine.put("category",category);
            medicine.put("imageUrl",imageUrl);

            db.collection("Medicines")
                    .add(medicine)
                    .addOnSuccessListener(documentReference -> {

                        Glide.with(this)
                                .load(imageUrl)
                                .into(imgMedicine);

                        Toast.makeText(this,"Medicine Added",Toast.LENGTH_SHORT).show();

                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show());
        });
    }
}