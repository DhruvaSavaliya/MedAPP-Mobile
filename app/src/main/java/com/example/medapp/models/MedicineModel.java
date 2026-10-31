package com.example.medapp.models;

public class MedicineModel {

    private String name;      // Medicine name
    private int quantity;     // Quantity (for cart or detection)
    private String dosage;    // Dosage string like "500mg"
    private String category;  // Category like "OTC", "Vitamins"
    private int price;        // Price in ₹
    private String imagePath; // Asset image path

    // Full constructor
    public MedicineModel(String name, int quantity, String dosage, String category, int price, String imagePath) {
        this.name = name;
        this.quantity = quantity;
        this.dosage = dosage;
        this.category = category;
        this.price = price;
        this.imagePath = imagePath;
    }

    // Minimal constructor for OCR-detected medicines (no category, no image, price=0)
    public MedicineModel(String name, int quantity, String dosage) {
        this(name, quantity, dosage, "", 0, "");
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

}