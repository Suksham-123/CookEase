package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("aggregateLikes")
    private int aggregateLikes;

    @SerializedName("servings")
    private int servings;

    @SerializedName("readyInMinutes")
    private int readyInMinutes;

    @SerializedName("image")
    private String image;

    // Empty constructor for Gson
    public Recipe() {}

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public int getServings() {
        return servings;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
