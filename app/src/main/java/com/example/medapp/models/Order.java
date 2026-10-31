package com.example.medapp.models;

import java.util.List;

public class Order {

    private String orderId;
    private String name;
    private String phone;
    private String address;
    private List<Medicine> cartItems;
    private int total;
    private long timestamp;
    private String status;

    public Order() { this.status = "Pending"; }

    public Order(String orderId, String name, String phone, String address,
                 List<Medicine> cartItems, int total, long timestamp, String status) {
        this.orderId = orderId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.cartItems = cartItems;
        this.total = total;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Order(String orderId, String name, String phone, String address,
                 List<Medicine> cartItems, int total, long timestamp) {
        this(orderId, name, phone, address, cartItems, total, timestamp, "Pending");
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public List<Medicine> getCartItems() { return cartItems; }
    public int getTotal() { return total; }
    public long getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    public int getTotalPrice() { return total; }

    // Setters
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setCartItems(List<Medicine> cartItems) { this.cartItems = cartItems; }
    public void setTotal(int total) { this.total = total; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public void setStatus(String status) { this.status = status; }
}