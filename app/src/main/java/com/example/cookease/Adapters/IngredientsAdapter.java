package com.example.cookease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Models.ExtendedIngredient;
import com.example.cookease.Models.MeasureUnit;
import com.example.cookease.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    Context context;
    List<ExtendedIngredient> list;

    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_instruction_step_items, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        ExtendedIngredient ingredient = list.get(position);

        // Set name
        holder.textView_name.setText(ingredient.getName());
        holder.textView_name.setSelected(true);

        // Set original description
        holder.textView_description.setText(ingredient.getOriginal());

        // Load image
        String imageUrl = "https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage();
        Picasso.get().load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name, textView_description;
        ImageView imageView;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_instructions_step_items);
            textView_description = itemView.findViewById(R.id.textView_instruction_ingredient);
            imageView = itemView.findViewById(R.id.imageView_instructions_step_items);
        }
    }
}
