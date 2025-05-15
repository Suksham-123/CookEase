package com.example.cookease.network;

import com.example.cookease.Models.SimilarRecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SimilarRecipesApi {
    @GET("recipes/{id}/similar")
    Call<List<SimilarRecipeResponse>> getSimilarRecipes(
            @Path("id") int id,
            @Query("apiKey") String apiKey
    );
}
