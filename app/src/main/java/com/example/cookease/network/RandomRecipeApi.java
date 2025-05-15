package com.example.cookease.network;

import com.example.cookease.Models.RandomRecipeApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomRecipeApi {
    @GET("recipes/random")
    Call<RandomRecipeApiResponse> getRandomRecipes(
            @Query("tags") String tags,
            @Query("number") int number,
            @Query("apiKey") String apiKey
    );
}
