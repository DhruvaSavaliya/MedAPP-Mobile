package com.example.medapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medapp.R;
import com.example.medapp.models.Medicine;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MEDICINE = 1;
    private static final int TYPE_HEALTHTIP = 2;

    private Context context;
    private List<Medicine> items;
    private OnAddToCartListener listener;

    public interface OnAddToCartListener {
        void onAddToCart(Medicine medicine);
    }

    public MedicineAdapter(Context context, List<Medicine> items, OnAddToCartListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    // 🔥 NEW: Update list dynamically (IMPORTANT for OCR)
    public void updateList(List<Medicine> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Medicine item = items.get(position);
        boolean isHealthTip = item.getPrice() == 0 && item.getQuantity() == 0 && item.getDescription() != null;
        return isHealthTip ? TYPE_HEALTHTIP : TYPE_MEDICINE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEALTHTIP) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_health_tip, parent, false);
            return new HealthTipViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
            return new MedicineViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Medicine item = items.get(position);

        if (getItemViewType(position) == TYPE_HEALTHTIP) {

            HealthTipViewHolder tipHolder = (HealthTipViewHolder) holder;
            tipHolder.txtTitle.setText(item.getName());
            tipHolder.txtDescription.setText(item.getDescription());

            loadImageFromAssets(item.getImagePath(), tipHolder.imageView);

            tipHolder.itemView.setOnClickListener(v ->
                    Toast.makeText(context, "Health Tip clicked!", Toast.LENGTH_SHORT).show()
            );

        } else {

            MedicineViewHolder medHolder = (MedicineViewHolder) holder;
            medHolder.txtName.setText(item.getName());
            medHolder.txtCategory.setText(item.getCategory());
            medHolder.txtPrice.setText("₹" + item.getPrice());

            loadImageFromAssets(item.getImagePath(), medHolder.imageView);

            medHolder.btnAddToCart.setOnClickListener(v -> {
                if (listener != null) listener.onAddToCart(item);
            });

            medHolder.itemView.setOnClickListener(v ->
                    Toast.makeText(context, item.getName() + " clicked!", Toast.LENGTH_SHORT).show()
            );
        }
    }

    // 🔥 CLEAN IMAGE LOADER (Reusable)
    private void loadImageFromAssets(String imagePath, ImageView imageView) {
        try {
            InputStream is = context.getAssets().open(imagePath);
            Drawable drawable = Drawable.createFromStream(is, null);
            imageView.setImageDrawable(drawable);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            imageView.setImageResource(R.drawable.ic_launcher_background); // fallback
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ---------------- VIEW HOLDERS ---------------- //

    static class MedicineViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName, txtCategory, txtPrice;
        Button btnAddToCart;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewMedicine);
            txtName = itemView.findViewById(R.id.txtMedicineName);
            txtCategory = itemView.findViewById(R.id.txtMedicineCategory);
            txtPrice = itemView.findViewById(R.id.txtMedicinePrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    static class HealthTipViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle, txtDescription;

        public HealthTipViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewHealthTip);
            txtTitle = itemView.findViewById(R.id.txtHealthTipTitle);
            txtDescription = itemView.findViewById(R.id.txtHealthTipDescription);
        }
    }
}