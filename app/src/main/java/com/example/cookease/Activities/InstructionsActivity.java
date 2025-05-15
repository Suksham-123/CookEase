package com.example.cookease.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Adapters.InstructionsAdapter;
import com.example.cookease.Listeners.InstructionsListener;
import com.example.cookease.Models.InstructionsResponse;
import com.example.cookease.Models.Step;
import com.example.cookease.R;


import java.util.List;

public class InstructionsActivity extends AppCompatActivity implements InstructionsListener {

    RecyclerView recyclerView;
    RequestManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        recyclerView = findViewById(R.id.recycler_view_instructions);
        manager = new RequestManager(this);

        int recipeId = getIntent().getIntExtra("recipeId", 0);
        manager.getInstructions(this, recipeId);
    }

    @Override
    public void didFetch(List<InstructionsResponse> response, String message) {
        if (response != null && !response.isEmpty()) {
            List<Step> steps = response.get(0).steps;

            InstructionsAdapter adapter = new InstructionsAdapter(this, steps);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Toast.makeText(this, "No instructions found.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void didError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
