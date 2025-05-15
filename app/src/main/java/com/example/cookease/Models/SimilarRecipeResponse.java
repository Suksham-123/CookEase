package com.example.cookease.Models;

public class SimilarRecipeResponse {
    private final int id;
    private final String title;
    private final String imageType;
    private final int servings;
    private final int readyInMinutes;
    private final String sourceUrl;

    public SimilarRecipeResponse(int id, String title, String imageType, int readyInMinutes, int servings, String sourceUrl) {
        this.id = id;
        this.title = title;
        this.imageType = imageType;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.sourceUrl = sourceUrl;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImageType() { return imageType; }
    public int getServings() { return servings; }
    public int getReadyInMinutes() { return readyInMinutes; }
    public String getSourceUrl() { return sourceUrl; }
}
