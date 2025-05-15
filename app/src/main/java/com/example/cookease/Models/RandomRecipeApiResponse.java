package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RandomRecipeApiResponse {

    @SerializedName("recipes")
    public List<Recipe> recipes;

    public RandomRecipeApiResponse(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    public RandomRecipeApiResponse() {
        this.recipes = new ArrayList<>();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }


}
