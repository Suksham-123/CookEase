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
import com.example.cookease.Models.SimilarRecipeResponse;
import com.example.cookease.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<SimilarRecipeAdapter.SimilarRecipeViewHolder> {

    Context context;
    List<SimilarRecipeResponse> list;
    RecipeClickListener listener;

    public SimilarRecipeAdapter(Context context, List<SimilarRecipeResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_similar_recipes, parent, false);
        return new SimilarRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarRecipeViewHolder holder, int position) {
        SimilarRecipeResponse recipe = list.get(position);

        holder.textView_similar_title.setText(recipe.getTitle());
        holder.textView_similar_title.setSelected(true);

        String readyInText = context.getString(R.string.ready_in_format, recipe.getReadyInMinutes());
        holder.textView_similar_ready_in_minutes.setText(readyInText);

        holder.textView_similar_source_url.setText(recipe.getSourceUrl());

        int servings = recipe.getServings();
        String servingsText = context.getString(R.string.servings_format, servings);
        holder.textView_similar_serving.setText(servingsText);

        String imageUrl = "https://img.spoonacular.com/recipes/" + recipe.getId() + "-556x370." + recipe.getImageType();
        Picasso.get().load(imageUrl).into(holder.imageView_similar);

        holder.similar_recipe_holder.setOnClickListener(v ->
                listener.onRecipeClicked(recipe.getId())
        );
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class SimilarRecipeViewHolder extends RecyclerView.ViewHolder {
        CardView similar_recipe_holder;
        TextView textView_similar_title, textView_similar_serving;
        TextView textView_similar_ready_in_minutes, textView_similar_source_url;
        ImageView imageView_similar;

        public SimilarRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            similar_recipe_holder = itemView.findViewById(R.id.similar_recipe_holder);
            textView_similar_title = itemView.findViewById(R.id.textView_similar_title);
            textView_similar_serving = itemView.findViewById(R.id.textView_similar_serving);
            imageView_similar = itemView.findViewById(R.id.imageView_similar);
            textView_similar_ready_in_minutes = itemView.findViewById(R.id.textView_similar_ready_in_minutes);
            textView_similar_source_url = itemView.findViewById(R.id.textView_similar_source_url);
        }
    }
}
