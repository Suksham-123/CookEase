package com.example.cookease.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Adapters.ProductMatchAdapter;
import com.example.cookease.Adapters.RandomRecipeAdapter;
import com.example.cookease.Listeners.RandomRecipeResponseListener;
import com.example.cookease.Listeners.RecipeClickListener;
import com.example.cookease.Models.ProductMatch;
import com.example.cookease.Models.RandomRecipeApiResponse;
import com.example.cookease.Models.Recipe;
import com.example.cookease.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeClickListener {

    private ProgressBar progressBar;
    private RequestManager manager;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private SearchView searchView;
    private Button buttonSearchByIngredients;
    private final List<String> tags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        setupSpinner();
        setupSearch();

        manager = new RequestManager(this);
        fetchRandomRecipes(); // Initial fetch
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
        searchView = findViewById(R.id.searchView_home);
        spinner = findViewById(R.id.spinner_tags);
        recyclerView = findViewById(R.id.recycler_random);
        buttonSearchByIngredients = findViewById(R.id.button_search_by_ingredients);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Temporary empty adapter
        List<ProductMatch> productList = Collections.emptyList();
        ProductMatchAdapter adapter = new ProductMatchAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // 🔍 Search by Ingredients Button Click
        buttonSearchByIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IngredientsSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupSpinner() {
        String[] tagArray = getResources().getStringArray(R.array.tags);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_text, tagArray);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tags.clear();
                tags.add(parent.getSelectedItem().toString());
                fetchRandomRecipes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    tags.clear();
                    tags.add(query.trim());
                    fetchRandomRecipes();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void fetchRandomRecipes() {
        progressBar.setVisibility(View.VISIBLE);
        manager.getRandomRecipes(randomRecipeResponseListener, tags);
    }

    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {
            progressBar.setVisibility(View.GONE);

            if (response.getRecipes() == null || response.getRecipes().isEmpty()) {
                Toast.makeText(MainActivity.this, "No recipes found!", Toast.LENGTH_SHORT).show();
                return;
            }

            List<Recipe> recipeList = new ArrayList<>(response.getRecipes());
            RandomRecipeAdapter randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, recipeList, MainActivity.this);
            recyclerView.setAdapter(randomRecipeAdapter);
            Log.d("RecipeDebug", "Recipes fetched: " + recipeList.size());
        }

        @Override
        public void didError(String message) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Error fetching recipes: " + message, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onRecipeClicked(int id) {
        Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
