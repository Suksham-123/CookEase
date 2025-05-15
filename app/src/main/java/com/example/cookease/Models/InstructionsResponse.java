package com.example.cookease.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class InstructionsResponse {

    @SerializedName("name")
    public String name;

    @SerializedName("steps")
    public List<Step> steps;
}

