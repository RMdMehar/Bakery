package com.example.bakery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakery.R;
import com.example.bakery.fragment.DetailFragment;
import com.example.bakery.fragment.IngredientsFragment;
import com.example.bakery.fragment.InstructionsFragment;
import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.example.bakery.model.Recipe;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailsAndProcedureActivity extends AppCompatActivity implements DetailFragment.OnInstructionItemClickListener, DetailFragment.OnIngredientItemClickListener {
    Recipe currentRecipe;
    List<Instruction> instructionList = new ArrayList<>();
    List<Ingredient> ingredientList = new ArrayList<>();
    String instructionJSON;
    String ingredientJSON;

    Gson gson = new Gson();
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_and_procedure);

        Intent intent = getIntent();
        String recipeJSON = intent.getStringExtra("recipeJSON");
        currentRecipe = gson.fromJson(recipeJSON, Recipe.class);

        instructionList = currentRecipe.getInstructions();
        instructionJSON = gson.toJson(instructionList);

        ingredientList = currentRecipe.getIngredients();
        ingredientJSON = gson.toJson(ingredientList);

        Bundle bundle = new Bundle();
        bundle.putString("instructionJSON", instructionJSON);
        bundle.putString("ingredientJSON", ingredientJSON);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.details_list, detailFragment)
                .commit();
    }

    @Override
    public void onIngredientSelected(String selectedIngredientJSON) {
        Bundle ingredientArgs = new Bundle();
        ingredientArgs.putString("ingredientJSON", ingredientJSON);

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setArguments(ingredientArgs);

        if (fragmentManager.findFragmentByTag("procedure") == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.procedure, ingredientsFragment, "procedure")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.procedure, ingredientsFragment, "procedure")
                    .commit();
        }
    }

    @Override
    public void onInstructionSelected(Instruction selectedInstruction) {
        String instructionJSON = gson.toJson(selectedInstruction);

        Bundle instructionArgs = new Bundle();
        instructionArgs.putString("instructionJSON", instructionJSON);

        InstructionsFragment instructionsFragment = new InstructionsFragment();
        instructionsFragment.setArguments(instructionArgs);

        if (fragmentManager.findFragmentByTag("procedure") == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.procedure, instructionsFragment, "procedure")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.procedure, instructionsFragment, "procedure")
                    .commit();
        }
    }
}
