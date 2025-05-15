package com.example.cookease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookease.Listeners.RecipeClickListener;
import com.example.cookease.Models.Recipe;
import com.example.cookease.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeAdapter.ViewHolder> {

    private final Context context;
    private final List<Recipe> recipes;
    private final RecipeClickListener listener;

    public RandomRecipeAdapter(Context context, List<Recipe> recipes, RecipeClickListener listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.textView_title.setText(recipe.getTitle());
        holder.textView_likes.setText(context.getString(R.string.likes_format, recipe.getAggregateLikes()));
        holder.textView_servings.setText(context.getString(R.string.servings_format, recipe.getServings()));
        holder.textView_time.setText(context.getString(R.string.time_format, recipe.getReadyInMinutes()));

        Picasso.get()
                .load(recipe.getImage())
                .into(holder.imageView_food);

        holder.cardView.setOnClickListener(v -> listener.onRecipeClicked(recipe.getId()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_title, textView_likes, textView_servings, textView_time;
        ImageView imageView_food;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_likes = itemView.findViewById(R.id.textView_likes);
            textView_servings = itemView.findViewById(R.id.textView_servings);
            textView_time = itemView.findViewById(R.id.textView_time);
            imageView_food = itemView.findViewById(R.id.imageView_food);
            cardView = itemView.findViewById(R.id.random_list_container);
        }
    }
}
