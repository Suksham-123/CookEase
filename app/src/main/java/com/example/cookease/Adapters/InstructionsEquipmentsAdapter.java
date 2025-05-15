package com.example.cookease.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cookease.Models.Equipment;
import com.example.cookease.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructionsEquipmentsAdapter extends RecyclerView.Adapter<InstructionsEquipmentsAdapter.ViewHolder> {

    private final List<Equipment> equipmentList;
    private final Context context;

    public InstructionsEquipmentsAdapter(Context context, List<Equipment> equipmentList) {
        this.context = context;
        this.equipmentList = equipmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_equipment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Equipment equipment = equipmentList.get(position);

        holder.nameText.setText(equipment.name);
        holder.nameText.setSelected(true);

        if (equipment.getImage() != null && !equipment.getImage().isEmpty()) {
            String imageUrl = "https://spoonacular.com/cdn/equipment_100x100/" + equipment.getImage();
            Log.d("EQUIPMENT_IMAGE_URL", imageUrl);

            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(holder.imageView);
        } else {
            // Optional: load placeholder or hide the image
            holder.imageView.setImageResource(R.drawable.ic_placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_instructions_step_items);
            nameText = itemView.findViewById(R.id.textView_instructions_step_items);
        }
    }
}
