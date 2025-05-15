package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Equipment {
    public int id;
    public String name;
    public String localizedName;

    @SerializedName("image")
    public String image;
    public List<ProductMatch> productMatches;

    public String getImage() {
        return image;
    }
    public Equipment() {

    }

    // Constructor to initialize Equipment objects
    public Equipment(int id, String name, String localizedName, String image, List<ProductMatch> productMatches) {
        this.id = id;
        this.name = name;
        this.localizedName = localizedName;
        this.image = image;
        this.productMatches = productMatches;
    }

}
