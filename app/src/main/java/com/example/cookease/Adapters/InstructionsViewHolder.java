package com.example.cookease.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.R;

public class InstructionsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_instruction_step_number;
    TextView textView_instruction_step_text;
    RecyclerView recycler_instructions_ingredients;
    RecyclerView recycler_instructions_equipments;

    public InstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instruction_step_number = itemView.findViewById(R.id.textView_step_number);
        recycler_instructions_ingredients = itemView.findViewById(R.id.recycler_instructions_ingredients);
        recycler_instructions_equipments = itemView.findViewById(R.id.recycler_instructions_equipments);
    }
}
