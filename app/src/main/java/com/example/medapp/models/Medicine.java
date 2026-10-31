package com.example.medapp.models;

public class Medicine {

    private String name;
    private String category;
    private int price;
    private int quantity;
    private String imagePath;
    private String description;

    public Medicine() { }

    public Medicine(String name, String category, int price, int quantity, String imagePath) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public Medicine(String name, String category, String description, String imagePath) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;
        this.price = 0;
        this.quantity = 0;
    }

    // ✅ Getters
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getImagePath() { return imagePath; }
    public String getDescription() { return description; }

    // dded for compatibility
    public String getImage() { return imagePath; }

    // ✅ Setters
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(int price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setDescription(String description) { this.description = description; }

}