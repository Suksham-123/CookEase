package com.example.cookease.Activities;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.example.cookease.Listeners.SearchRecipesListener;
import com.example.cookease.Models.Equipment;
import com.example.cookease.Models.ProductMatchResponse;
import com.example.cookease.Models.Recipe;
import com.example.cookease.R;
import com.example.cookease.RequestQueueSingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.cookease.Models.EquipmentDeserializer;
import com.example.cookease.Listeners.ProductMatchListener;
import com.example.cookease.Listeners.RandomRecipeResponseListener;
import com.example.cookease.Listeners.RecipeDetailsListener;
import com.example.cookease.Listeners.SimilarRecipesListener;
import com.example.cookease.Models.InstructionsResponse;
import com.example.cookease.Models.RandomRecipeApiResponse;
import com.example.cookease.Listeners.InstructionsListener;
import com.example.cookease.Models.RecipeDetailsResponse;
import com.example.cookease.Models.SimilarRecipeResponse;
import com.example.cookease.network.InstructionsApi;
import com.example.cookease.network.ProductMatchApi;
import com.example.cookease.network.RandomRecipeApi;
import com.example.cookease.network.RecipeDetailsApi;
import com.example.cookease.network.SimilarRecipesApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    Context context;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Equipment.class, new EquipmentDeserializer())
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRecipeDetails(RecipeDetailsListener listener, int id) {
        RecipeDetailsApi callApi = retrofit.create(RecipeDetailsApi.class);
        Call<RecipeDetailsResponse> call = callApi.getRecipeDetails(id, "58d39e05683d41f788af9b73157b1c44");
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeDetailsResponse> call, @NonNull Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError("Error: " + response.message());
                    return;
                }

                RecipeDetailsResponse data = response.body();

                if (data != null) {
                    Log.d("RecipeDetails", "Ready in: " + data.getReadyInMinutes() + ", Servings: " + data.getServings());
                    listener.didFetch(data, response.message());
                } else {
                    listener.didError("No data found.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeDetailsResponse> call, @NonNull Throwable t) {
                listener.didError("Request failed: " + t.getMessage());
            }
        });
    }

    public void getSimilarRecipes(SimilarRecipesListener listener, int id) {
        SimilarRecipesApi callApi = retrofit.create(SimilarRecipesApi.class);
        Call<List<SimilarRecipeResponse>> call = callApi.getSimilarRecipes(id, "58d39e05683d41f788af9b73157b1c44");
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SimilarRecipeResponse>> call, @NonNull Response<List<SimilarRecipeResponse>> response) {
                if (!response.isSuccessful()) {
                    listener.didError("Error: " + response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<SimilarRecipeResponse>> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getInstructions(InstructionsListener listener, int id) {
        InstructionsApi callApi = retrofit.create(InstructionsApi.class);
        Call<List<InstructionsResponse>> call = callApi.getInstructions(id, "58d39e05683d41f788af9b73157b1c44");
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<InstructionsResponse>> call, @NonNull Response<List<InstructionsResponse>> response) {
                if (!response.isSuccessful()) {
                    listener.didError("Error: " + response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<InstructionsResponse>> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags) {
        RandomRecipeApi foodRecipesApi = retrofit.create(RandomRecipeApi.class);
        String joinedTags = String.join(",", tags);

        Call<RandomRecipeApiResponse> call = foodRecipesApi.getRandomRecipes(joinedTags, 10, context.getString(R.string.api_key));
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<RandomRecipeApiResponse> call, @NonNull Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError("Error " + response.code() + ": " + response.message());
                    return;
                }
                listener.didFetch(response.body(), "Success");
            }

            @Override
            public void onFailure(@NonNull Call<RandomRecipeApiResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage() != null ? t.getMessage() : "Unknown error");
            }
        });
    }

    public void getProductMatches(String query, ProductMatchListener listener) {
        ProductMatchApi productMatchApi = retrofit.create(ProductMatchApi.class);
        Call<ProductMatchResponse> call = productMatchApi.getProductMatches(query, "58d39e05683d41f788af9b73157b1c44");

        call.enqueue(new Callback<ProductMatchResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductMatchResponse> call, @NonNull Response<ProductMatchResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    listener.didError("Failed to fetch product matches");
                    return;
                }
                listener.didFetch(response.body().products, response.message());
            }

            @Override
            public void onFailure(@NonNull Call<ProductMatchResponse> call, @NonNull Throwable t) {
                listener.didError("Network error: " + t.getMessage());
            }
        });
    }

    public void getRecipesByIngredients(RandomRecipeResponseListener listener, String ingredients) {
        try {
            String encodedIngredients = URLEncoder.encode(ingredients, "UTF-8");
            String url = "https://api.spoonacular.com/recipes/findByIngredients?ingredients="
                    + encodedIngredients
                    + "&number=10&apiKey=58d39e05683d41f788af9b73157b1c44";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            List<Recipe> recipeList = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject recipeJson = response.getJSONObject(i);
                                Recipe recipe = new Recipe();
                                recipe.setId(recipeJson.getInt("id"));
                                recipe.setTitle(recipeJson.getString("title"));
                                recipe.setImage(recipeJson.getString("image"));
                                recipeList.add(recipe);
                            }

                            RandomRecipeApiResponse apiResponse = new RandomRecipeApiResponse();
                            apiResponse.setRecipes(recipeList);
                            listener.didFetch(apiResponse, "Success");

                        } catch (JSONException e) {
                            listener.didError("Parsing error");
                        }
                    },
                    error -> listener.didError("Request error")
            );

            RequestQueueSingleton.getInstance(context).addToRequestQueue(request);

        } catch (UnsupportedEncodingException e) {
            listener.didError("Encoding error");
        }
    }


}
