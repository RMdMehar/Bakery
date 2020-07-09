package com.example.bakery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakery.R;
import com.example.bakery.fragment.DetailFragment;
import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.example.bakery.model.Recipe;
import com.example.bakery.utilities.InstructionsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    Gson gson;
    Recipe currentRecipe;
    List<Instruction> instructionList = new ArrayList<>();
    List<Ingredient> ingredientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gson = new Gson();
        Intent receivingIntent = getIntent();
        String recipeJSON = receivingIntent.getStringExtra("recipeJSON");
        currentRecipe = gson.fromJson(recipeJSON, Recipe.class);
        instructionList = currentRecipe.getInstructions();
        ingredientList = currentRecipe.getIngredients();

        String instructionJSON = gson.toJson(instructionList);
        String ingredientJSON = gson.toJson(ingredientList);
        Bundle bundle = new Bundle();
        bundle.putString("instructionJSON", instructionJSON);
        bundle.putString("ingredientJSON", ingredientJSON);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment detailPortraitFragment = new DetailFragment();
        detailPortraitFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.detail_portrait_fragment, detailPortraitFragment)
                .commit();
    }

    private void loadIngredients() {
        ingredientList = currentRecipe.getIngredients();
        String ingredientJSON = gson.toJson(instructionList);

        Intent sendingIntent = new Intent(DetailActivity.this, ProcedureActivity.class);
        sendingIntent.putExtra("ingredientJSON", ingredientJSON);
        startActivity(sendingIntent);
    }

}
