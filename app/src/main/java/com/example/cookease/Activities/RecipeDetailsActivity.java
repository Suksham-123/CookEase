package com.example.cookease.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Adapters.IngredientsAdapter;
import com.example.cookease.Adapters.InstructionStepAdapter;
import com.example.cookease.Adapters.ProductMatchAdapter;
import com.example.cookease.Adapters.SimilarRecipeAdapter;
import com.example.cookease.Listeners.ProductMatchListener;
import com.example.cookease.Listeners.RecipeClickListener;
import com.example.cookease.Listeners.RecipeDetailsListener;
import com.example.cookease.Listeners.SimilarRecipesListener;
import com.example.cookease.Models.ExtendedIngredient;
import com.example.cookease.Models.ProductMatch;
import com.example.cookease.Models.RecipeDetailsResponse;
import com.example.cookease.Models.SimilarRecipeResponse;
import com.example.cookease.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    int id;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary, textView_meal_likes;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar, recycler_product_matches, recyclerInstructionSteps;
    RequestManager manager;
    AlertDialog progressDialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    ProductMatchAdapter productMatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_details);

        findViews();

        int recipeId = getIntent().getIntExtra("id", -1);
        if (recipeId == -1) {
            Toast.makeText(this, "Invalid recipe ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        id = recipeId;
        manager = new RequestManager(this);
        showProgressDialog();

        manager.getRecipeDetails(recipeDetailsListener, recipeId);
        manager.getSimilarRecipes(similarRecipesListener, recipeId);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        textView_meal_likes = findViewById(R.id.textView_meal_likes);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
        recycler_product_matches = findViewById(R.id.recycler_product_matches);
        recyclerInstructionSteps = findViewById(R.id.recycler_instruction_steps);
    }

    private void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        ViewGroup root = findViewById(android.R.id.content);
        View dialogView = inflater.inflate(R.layout.dialog_progress, root, false);
        builder.setView(dialogView);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dismissProgressDialog();

            textView_meal_name.setText(response.getTitle() != null ? response.getTitle() : "No title available");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView_meal_summary.setText(response.getSummary() != null
                        ? Html.fromHtml(response.getSummary(), Html.FROM_HTML_MODE_COMPACT)
                        : "No summary available");
            } else {
                textView_meal_summary.setText(response.getSummary() != null
                        ? Html.fromHtml(response.getSummary())
                        : "No summary available");
            }

            textView_meal_source.setText(getString(R.string.source_name_format, response.getSourceName()));
            textView_meal_likes.setText(getString(R.string.likes, response.getAggregateLikes()));
            ((TextView) findViewById(R.id.textView_ready_in_minutes)).setText(getString(R.string.ready_in_minutes, response.getReadyInMinutes()));
            ((TextView) findViewById(R.id.textView_servings)).setText(getString(R.string.servings, response.getServings()));

            if (response.getImage() != null && !response.getImage().isEmpty()) {
                Picasso.get().load(response.getImage()).into(imageView_meal_image);
            }

            // Ingredients
            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            if (response.getExtendedIngredients() != null && !response.getExtendedIngredients().isEmpty()) {
                ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.getExtendedIngredients());
                recycler_meal_ingredients.setAdapter(ingredientsAdapter);

                // Fetch product matches
                List<String> ingredientNames = new ArrayList<>();
                for (ExtendedIngredient ingredient : response.getExtendedIngredients()) {
                    if (ingredient.getName() != null && !ingredient.getName().isEmpty()) {
                        ingredientNames.add(ingredient.getName());
                    }
                }
                manager.getProductMatches(String.join(",", ingredientNames), productMatchesListener);
            }

            // Instruction steps
            if (response.getAnalyzedInstructions() != null && !response.getAnalyzedInstructions().isEmpty()) {
                List<com.example.cookease.Models.Step> steps = response.getAnalyzedInstructions().get(0).getSteps();
                InstructionStepAdapter instructionStepAdapter = new InstructionStepAdapter(RecipeDetailsActivity.this, steps);
                recyclerInstructionSteps.setHasFixedSize(true);
                recyclerInstructionSteps.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this));
                recyclerInstructionSteps.setAdapter(instructionStepAdapter);
            } else {
                Toast.makeText(RecipeDetailsActivity.this, "No instructions found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void didError(String message) {
            dismissProgressDialog();
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = id -> {
        try {
            Intent intent = new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } catch (NumberFormatException e) {
            Toast.makeText(RecipeDetailsActivity.this, "Invalid recipe ID", Toast.LENGTH_SHORT).show();
        }
    };

    private final ProductMatchListener productMatchesListener = new ProductMatchListener() {
        @Override
        public void didFetch(List<ProductMatch> matches, String message) {
            recycler_product_matches.setHasFixedSize(true);
            recycler_product_matches.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            productMatchAdapter = new ProductMatchAdapter(RecipeDetailsActivity.this, matches);
            recycler_product_matches.setAdapter(productMatchAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, "Error loading product matches: " + message, Toast.LENGTH_SHORT).show();
        }
    };
}
