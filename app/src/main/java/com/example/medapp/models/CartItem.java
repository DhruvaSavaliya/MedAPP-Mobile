package com.example.medapp.models;

public class CartItem {

    Medicine medicine;
    int quantity;

    public CartItem(Medicine medicine) {
        this.medicine = medicine;
        this.quantity = 1;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQty() {
        quantity++;
    }

    public void decreaseQty() {
        if(quantity > 1){
            quantity--;
        }
    }
}