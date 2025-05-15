package com.example.cookease.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Models.Step;
import com.example.cookease.R;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder> {
    Context context;
    List<Step> steps;

    public InstructionsAdapter(Context context, List<Step> steps) {
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_instructions_step, parent, false);
        return new InstructionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        Step step = steps.get(position);

        if (holder.textView_instruction_step_text == null) {
            Log.e("InstructionsAdapter", "textView_instruction_step_text is null at position: " + position);
            return;
        }

        holder.textView_instruction_step_number.setText("Step " + step.getNumber());
        holder.textView_instruction_step_text.setText(step.getStep());

        // Ingredients RecyclerView
        holder.recycler_instructions_ingredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_instructions_ingredients.setAdapter(new InstructionsIngredientsAdapter(context, step.getIngredients()));

        // Equipments RecyclerView
        holder.recycler_instructions_equipments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_instructions_equipments.setAdapter(new InstructionsEquipmentsAdapter(context, step.getEquipment()));

    }


    @Override
    public int getItemCount() {
        return steps.size();
    }
}
