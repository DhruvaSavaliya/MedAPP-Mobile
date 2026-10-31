package com.example.medapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medapp.R;
import com.example.medapp.adapters.OrderAdapter;
import com.example.medapp.models.Order;
import com.example.medapp.utils.Constants;
import com.example.medapp.utils.FirebaseHelper;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerOrders;
    private OrderAdapter adapter;
    private List<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerOrders = findViewById(R.id.recyclerOrders);
        adapter = new OrderAdapter((Context) orderList, null);
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrders.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {
        String userId = FirebaseHelper.getAuth().getCurrentUser().getUid();
        FirebaseHelper.getDb()
                .collection(Constants.COLLECTION_ORDERS)
                .whereEqualTo("userId", userId)
                .orderBy("timestamp")
                .get()
                .addOnSuccessListener(qs -> {
                    orderList.clear();
                    for (DocumentSnapshot doc : qs) {
                        Order order = doc.toObject(Order.class);
                        if (order != null) {
                            orderList.add(order);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to load orders", Toast.LENGTH_SHORT).show());
    }
}

