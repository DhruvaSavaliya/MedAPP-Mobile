package com.example.medapp.models;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private final List<Medicine> cartItems;

    // Private constructor (Singleton)
    private CartManager() {
        cartItems = new ArrayList<>();
    }

    // Get single instance
    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Get all cart items
    public List<Medicine> getCartItems() {
        return cartItems;
    }

    // ✅ Add to cart with quantity handling
    public void addToCart(Medicine medicine) {

        // Check if already exists
        for (Medicine m : cartItems) {
            if (m.getName().equalsIgnoreCase(medicine.getName())) {
                m.setQuantity(m.getQuantity() + 1);
                return;
            }
        }

        // If new item
        medicine.setQuantity(1);
        cartItems.add(medicine);
    }

    // Remove item
    public void removeFromCart(Medicine medicine) {
        cartItems.remove(medicine);
    }

    // Update quantity manually
    public void updateQuantity(Medicine medicine, int quantity) {
        if (quantity <= 0) {
            cartItems.remove(medicine);
        } else {
            medicine.setQuantity(quantity);
        }
    }

    // Calculate total price
    public int getTotal() {
        int total = 0;
        for (Medicine m : cartItems) {
            total += m.getPrice() * m.getQuantity();
        }
        return total;
    }

    // Clear cart
    public void clearCart() {
        cartItems.clear();
    }
}