package com.example.bakery.model;

import android.widget.ListView;

import java.util.List;

public class Recipe {
    private String name;
    private List<Ingredient> ingredients;
    private List<Instruction> instructions;

    public Recipe(String name, List<Ingredient> ingredients, List<Instruction> instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}
