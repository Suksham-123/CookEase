package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDetailsResponse {
    private final int id;
    private final String title;
    private final String image;
    private final int readyInMinutes;
    private final int servings;
    private final String summary;
    private final List<ExtendedIngredient> extendedIngredients;
    private final int aggregateLikes;
    private final String sourceName;

    @SerializedName("analyzedInstructions")
    private List<AnalyzedInstruction> analyzedInstructions;




    public RecipeDetailsResponse(int id, String title, String image, int readyInMinutes, int servings, String summary, List<ExtendedIngredient> extendedIngredients, int aggregateLikes, String sourceName, List<AnalyzedInstruction> analyzedInstructions) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.summary = summary;
        this.extendedIngredients = extendedIngredients;
        this.aggregateLikes = aggregateLikes;
        this.sourceName = sourceName;
        this.analyzedInstructions = analyzedInstructions;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getSummary() {
        return summary;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    // ✅ Fixed: Properly implemented this getter
    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public String getSourceName() {
        return sourceName;
    }
    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
        }

}
