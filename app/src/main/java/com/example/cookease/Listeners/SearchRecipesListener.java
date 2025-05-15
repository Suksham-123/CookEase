package com.example.cookease.Listeners;

import com.example.cookease.Models.Recipe;

import java.util.List;

public interface SearchRecipesListener {
    void didFetch(List<Recipe> recipes, String message);
    void didError(String message);
}
