package com.example.medapp.models;

public class OrderItem {
    private String id;
    private String medicineId;
    private int quantity;
    private double price;

    public OrderItem() {
    }

    public OrderItem(String id, String medicineId, int quantity, double price) {
        this.id = id;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

