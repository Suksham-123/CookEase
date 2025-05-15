package com.example.cookease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Models.Ingredients;
import com.example.cookease.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsIngredientsAdapter extends RecyclerView.Adapter<InstructionsIngredientsAdapter.ViewHolder> {

    private final Context context;
    private final List<Ingredients> ingredientList;

    public InstructionsIngredientsAdapter(Context context, List<Ingredients> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_instruction_step_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredients ingredient = ingredientList.get(position);

        // ✅ Set actual ingredient name
        holder.nameText.setText(ingredient.getName());
        holder.nameText.setSelected(true);

        // ✅ Set original description if available
        holder.textView_instruction_ingredient.setText(ingredient.getOriginal());

        // ✅ Load ingredient image
        String imageUrl = "https://img.spoonacular.com/ingredients_100x100/" + ingredient.getImage();
        Picasso.get().load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText;
        TextView textView_instruction_ingredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_instructions_step_items);
            nameText = itemView.findViewById(R.id.textView_instructions_step_items);
            textView_instruction_ingredient = itemView.findViewById(R.id.textView_instruction_ingredient);
        }
    }
}
