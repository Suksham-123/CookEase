package com.example.cookease.Listeners;

import com.example.cookease.Models.ProductMatch;

import java.util.List;

public interface ProductMatchListener {
    void didFetch(List<ProductMatch> response, String message);
    void didError(String message);
}
