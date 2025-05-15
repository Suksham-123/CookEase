package com.example.cookease.network;

import com.example.cookease.Models.RecipeDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeDetailsApi {
    @GET("recipes/{id}/information")
    Call<RecipeDetailsResponse> getRecipeDetails(
            @Path("id") int id,
            @Query("apiKey") String apiKey
    );
}
