package com.example.bakery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.bakery.R;
import com.example.bakery.fragment.DetailFragment;
import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.example.bakery.model.Recipe;
import com.example.bakery.utilities.InstructionsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnInstructionItemClickListener, DetailFragment.OnIngredientItemClickListener {
    Gson gson = new Gson();
    Recipe currentRecipe;
    List<Instruction> instructionList = new ArrayList<>();
    List<Ingredient> ingredientList = new ArrayList<>();
    String instructionJSON;
    String ingredientJSON;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent receivingIntent = getIntent();
        String recipeJSON = receivingIntent.getStringExtra("recipeJSON");
        currentRecipe = gson.fromJson(recipeJSON, Recipe.class);
        instructionList = currentRecipe.getInstructions();
        ingredientList = currentRecipe.getIngredients();

        instructionJSON = gson.toJson(instructionList);

        ingredientJSON = gson.toJson(ingredientList);
        Bundle bundle = new Bundle();
        bundle.putString("instructionJSON", instructionJSON);
        bundle.putString("ingredientJSON", ingredientJSON);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment detailPortraitFragment = new DetailFragment();
        detailPortraitFragment.setArguments(bundle);

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.detail_portrait_fragment, detailPortraitFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_portrait_fragment, detailPortraitFragment)
                    .commit();
        }

    }

    @Override
    public void onInstructionSelected(Instruction selectedInstruction) {
        String instructionJSON = gson.toJson(selectedInstruction);

        Intent sendingIntent = new Intent(DetailActivity.this, ProcedureActivity.class);
        sendingIntent.putExtra("instructionJSON", instructionJSON);
        startActivity(sendingIntent);
    }

    @Override
    public void onIngredientSelected(String selectedIngredientJSON) {
        Intent sendingIntent = new Intent(DetailActivity.this, ProcedureActivity.class);
        sendingIntent.putExtra("ingredientJSON", selectedIngredientJSON);
        startActivity(sendingIntent);
    }
}
