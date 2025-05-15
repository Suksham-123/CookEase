package com.example.cookease.network;

import com.example.cookease.Models.InstructionsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InstructionsApi {
    @GET("recipes/{id}/analyzedInstructions")
    Call<List<InstructionsResponse>> getInstructions(
            @Path("id") int id,
            @Query("apiKey") String apiKey
    );
}
