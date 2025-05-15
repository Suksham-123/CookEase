package com.example.cookease.Listeners;

import com.example.cookease.Models.InstructionsResponse;
import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
