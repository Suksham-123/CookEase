package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;

public class Ingredients {
    private final int id;
    private final String name;
    private final String image;
    @SerializedName("original")
    private String original;

    public Ingredients(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getOriginal() {
        return original;
    }

}
