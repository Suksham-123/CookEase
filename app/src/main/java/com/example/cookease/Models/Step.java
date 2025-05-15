package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Step {
    private final int number;
    private final String step;
    private final List<Ingredients> ingredients;
    @SerializedName("equipment")
    public List<Equipment> equipment;
    public Length length;
    public Step(int number, String step, List<Ingredients> ingredients) {
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
    }

    public int getNumber() { return number; }
    public String getStep() { return step; }
    public List<Ingredients> getIngredients() { return ingredients; }
    public List<Equipment> getEquipment() {
        return equipment;
    }
}

