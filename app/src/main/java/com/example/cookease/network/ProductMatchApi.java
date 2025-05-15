package com.example.cookease.network;

import com.example.cookease.Models.ProductMatch;
import com.example.cookease.Models.ProductMatchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductMatchApi {
    @GET("food/products/search")
    Call<ProductMatchResponse> getProductMatches(
            @Query("query") String query,
            @Query("apiKey") String apiKey
    );
}
