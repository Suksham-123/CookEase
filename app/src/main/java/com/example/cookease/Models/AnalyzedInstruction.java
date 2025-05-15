package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AnalyzedInstruction {

    @SerializedName("steps")
    private List<Step> steps;

    public List<Step> getSteps() {
        return steps;
    }

    // You may remove this method if unused, to eliminate the warning
    @SuppressWarnings("unused")
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
