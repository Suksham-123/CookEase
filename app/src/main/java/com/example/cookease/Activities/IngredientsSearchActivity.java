package com.example.cookease.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Adapters.RandomRecipeAdapter;
import com.example.cookease.Listeners.RandomRecipeResponseListener;
import com.example.cookease.Listeners.RecipeClickListener;
import com.example.cookease.Models.RandomRecipeApiResponse;
import com.example.cookease.R;

public class IngredientsSearchActivity extends AppCompatActivity {

    EditText editTextIngredients;
    Button buttonSearch;
    RecyclerView recyclerRecipes;
    RandomRecipeAdapter recipeAdapter;
    RequestManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_ingredients);

        // Initialize UI components
        editTextIngredients = findViewById(R.id.editText_ingredients);
        buttonSearch = findViewById(R.id.button_search);
        recyclerRecipes = findViewById(R.id.recycler_ingredient_recipes);
        recyclerRecipes.setHasFixedSize(true);
        recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));

        manager = new RequestManager(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredients = editTextIngredients.getText().toString().trim();
                if (ingredients.isEmpty()) {
                    Toast.makeText(IngredientsSearchActivity.this, "Please enter some ingredients", Toast.LENGTH_SHORT).show();
                } else {
                    fetchRecipes(ingredients);
                }
            }
        });
    }

    private void fetchRecipes(String ingredients) {
        Toast.makeText(this, "Searching for recipes...", Toast.LENGTH_SHORT).show();

        manager.getRecipesByIngredients(new RandomRecipeResponseListener() {
            @Override
            public void didFetch(RandomRecipeApiResponse response, String message) {
                if (response != null && response.getRecipes() != null && !response.getRecipes().isEmpty()) {
                    recipeAdapter = new RandomRecipeAdapter(
                            IngredientsSearchActivity.this,
                            response.getRecipes(),
                            new RecipeClickListener() {
                                @Override
                                public void onRecipeClicked(int id) {
                                    Intent intent = new Intent(IngredientsSearchActivity.this, RecipeDetailsActivity.class);
                                    intent.putExtra("id", id);
                                    startActivity(intent);
                                }
                            }
                    );
                    recyclerRecipes.setAdapter(recipeAdapter);
                } else {
                    Toast.makeText(IngredientsSearchActivity.this, "No recipes found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void didError(String message) {
                Toast.makeText(IngredientsSearchActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }, ingredients);
    }
}
