package com.example.medapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medapp.R;
import com.example.medapp.models.Medicine;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<Medicine> cartItems;
    private final CartListener listener;

    // Listener interface
    public interface CartListener {
        void onRemove(Medicine medicine);
        void onQuantityChange(Medicine medicine, int newQty);
    }

    public CartAdapter(Context context, List<Medicine> cartItems, CartListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Medicine medicine = cartItems.get(position);

        holder.txtName.setText(medicine.getName());
        holder.txtPrice.setText("₹" + medicine.getPrice());
        holder.txtQuantity.setText(String.valueOf(medicine.getQuantity()));

        holder.btnIncrease.setOnClickListener(v -> {
            int newQty = medicine.getQuantity() + 1;
            medicine.setQuantity(newQty);
            holder.txtQuantity.setText(String.valueOf(newQty));
            if (listener != null) listener.onQuantityChange(medicine, newQty);
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int newQty = medicine.getQuantity() - 1;
            if (newQty < 1) return;
            medicine.setQuantity(newQty);
            holder.txtQuantity.setText(String.valueOf(newQty));
            if (listener != null) listener.onQuantityChange(medicine, newQty);
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) listener.onRemove(medicine);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtQuantity;
        Button btnIncrease, btnDecrease, btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCartMedicineName);
            txtPrice = itemView.findViewById(R.id.txtCartMedicinePrice);
            txtQuantity = itemView.findViewById(R.id.txtCartQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}