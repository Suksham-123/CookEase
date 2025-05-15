package com.example.cookease.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cookease.Models.ProductMatch;
import com.example.cookease.R;

import java.util.List;

public class ProductMatchAdapter extends RecyclerView.Adapter<ProductMatchAdapter.ProductViewHolder> {

    private final Context context;
    private final List<ProductMatch> productList;

    public ProductMatchAdapter(Context context, List<ProductMatch> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_product_match, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductMatch product = productList.get(position);
        holder.title.setText(product.title);
        holder.description.setText(product.description);
        holder.price.setText(product.price);

        Glide.with(context)
                .load(product.image)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.link));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, description, price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_product);
            title = itemView.findViewById(R.id.text_title);
            description = itemView.findViewById(R.id.text_description);
            price = itemView.findViewById(R.id.text_price);
        }
    }
}
