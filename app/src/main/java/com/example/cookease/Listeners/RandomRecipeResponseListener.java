package com.example.cookease.Listeners;

import com.example.cookease.Models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);

    void didError(String message);


}
