package com.example.medapp.utils;

import com.example.medapp.models.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineParser {

    /**
     * Parses raw OCR text into a list of Medicine objects.
     * @param rawText OCR result text
     * @return List of Medicine
     */
    public static List<Medicine> parseToMedicines(String rawText) {
        List<Medicine> medicines = new ArrayList<>();

        if (rawText == null || rawText.isEmpty()) {
            return medicines;
        }

        String[] lines = rawText.split("\\r?\\n"); // Split by line

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // If you want, you can apply corrections here using a map
            // For now, we just use the line as medicine name

            String name = line;

            // Set default values for other fields
            int price = 100;       // default price
            String category = "Unknown"; // default category
            int quantity = 1;      // default quantity
            String imagePath = ""; // no image by default

            medicines.add(new Medicine(name, category, price, quantity, imagePath));
        }

        return medicines;
    }
}