package com.example.bakery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakery.R;
import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.example.bakery.model.Recipe;
import com.example.bakery.utilities.InstructionsAdapter;
import com.google.gson.Gson;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements InstructionsAdapter.InstructionClickListener {
    Gson gson;
    Recipe currentRecipe;
    List<Instruction> instructionList;
    List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gson = new Gson();
        Intent receivingIntent = getIntent();
        String recipeJSON = receivingIntent.getStringExtra("recipeJSON");
        currentRecipe = gson.fromJson(recipeJSON, Recipe.class);
        instructionList = currentRecipe.getInstructions();

        /*RecyclerView instructionsRecyclerView = findViewById(R.id.instructions_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        instructionsRecyclerView.setLayoutManager(linearLayoutManager);
        instructionsRecyclerView.setHasFixedSize(false);

        InstructionsAdapter instructionsAdapter = new InstructionsAdapter(instructionList, this);
        instructionsRecyclerView.setAdapter(instructionsAdapter);*/
    }

    @Override
    public void onInstructionClick(int clickedItemIndex) {
        Instruction currentInstruction = instructionList.get(clickedItemIndex);
        String instructionJSON = gson.toJson(currentInstruction);

        Intent sendingIntent = new Intent(DetailActivity.this, ProcedureActivity.class);
        sendingIntent.putExtra("instructionJSON", instructionJSON);
        startActivity(sendingIntent);
    }

    private void loadIngredients() {
        ingredientList = currentRecipe.getIngredients();
        String ingredientJSON = gson.toJson(instructionList);

        Intent sendingIntent = new Intent(DetailActivity.this, ProcedureActivity.class);
        sendingIntent.putExtra("ingredientJSON", ingredientJSON);
        startActivity(sendingIntent);
    }

}
