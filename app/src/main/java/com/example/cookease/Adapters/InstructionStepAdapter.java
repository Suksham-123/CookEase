package com.example.cookease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Models.Step;
import com.example.cookease.R;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepAdapter.InstructionStepViewHolder> {

    private final Context context;
    private final List<Step> stepList;

    public InstructionStepAdapter(Context context, List<Step> stepList) {
        this.context = context;
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_instructions_step, parent, false);
        return new InstructionStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        Step currentStep = stepList.get(position);

        holder.stepNumber.setText(String.valueOf(currentStep.getNumber()));
        holder.stepTitle.setText(currentStep.getStep());

        // Duration handling
        if (currentStep.length != null && currentStep.length.number > 0 && currentStep.length.unit != null) {
            String duration = context.getString(R.string.instruction_duration_format, currentStep.length.number, currentStep.length.unit);
            holder.durationText.setText(duration);

            holder.durationText.setVisibility(View.VISIBLE);
        } else {
            holder.durationText.setVisibility(View.GONE);
        }

        // Ingredients
        if (currentStep.getIngredients() != null && !currentStep.getIngredients().isEmpty()) {
            setupRecyclerView(holder.recyclerIngredients, new InstructionsIngredientsAdapter(context, currentStep.getIngredients()));
        }

        // Equipments
        if (currentStep.equipment != null && !currentStep.equipment.isEmpty()) {
            setupRecyclerView(holder.recyclerEquipments, new InstructionsEquipmentsAdapter(context, currentStep.equipment));


        }
    }

    private void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public static class InstructionStepViewHolder extends RecyclerView.ViewHolder {
        TextView stepNumber, stepTitle, durationText;
        RecyclerView recyclerIngredients, recyclerEquipments;

        public InstructionStepViewHolder(@NonNull View itemView) {
            super(itemView);

            stepNumber = itemView.findViewById(R.id.textView_step_number);
            stepTitle = itemView.findViewById(R.id.textView_step_title);
            durationText = itemView.findViewById(R.id.textView_duration); // must exist in XML
            recyclerIngredients = itemView.findViewById(R.id.recycler_instructions_ingredients);
            recyclerEquipments = itemView.findViewById(R.id.recycler_instructions_equipments);
        }
    }
}
