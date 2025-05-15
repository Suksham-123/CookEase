// EquipmentDeserializer.java
package com.example.cookease.Models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDeserializer implements JsonDeserializer<Equipment> {
    @Override
    public Equipment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Equipment equipment = new Equipment();
        equipment.id = jsonObject.get("id").getAsInt();
        equipment.name = jsonObject.get("name").getAsString();
        equipment.localizedName = jsonObject.get("localizedName").getAsString();
        equipment.image = jsonObject.get("image").getAsString();


        // Handle productMatches (object or array or missing)
        if (jsonObject.has("productMatches")) {
            JsonElement element = jsonObject.get("productMatches");

            List<ProductMatch> productMatches = new ArrayList<>();
            if (element.isJsonArray()) {
                for (JsonElement item : element.getAsJsonArray()) {
                    ProductMatch match = context.deserialize(item, ProductMatch.class);
                    productMatches.add(match);
                }
            } else if (element.isJsonObject()) {
                // Handle single object case
                ProductMatch match = context.deserialize(element, ProductMatch.class);
                productMatches.add(match);
            }

            equipment.productMatches = productMatches;
        }

        return equipment;
    }
}
