package com.example.cookease.Models;

public class ExtendedIngredient {
    private final int id;
    private final String name;
    private final String original;
    private final String unit;
    private final double amount;
    private final String image;
    private final Measures measures;

    public ExtendedIngredient(int id, String name, String original, String unit, double amount, String image, Measures measures) {
        this.id = id;
        this.name = name;
        this.original = original;
        this.unit = unit;
        this.amount = amount;
        this.image = image;
        this.measures = measures;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getOriginal() { return original; }
    public String getUnit() { return unit; }
    public double getAmount() { return amount; }
    public String getImage() { return image; }
    public Measures getMeasures() { return measures; }
}
