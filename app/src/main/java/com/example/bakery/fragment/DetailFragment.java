package com.example.bakery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakery.R;
import com.example.bakery.activity.DetailActivity;
import com.example.bakery.activity.ProcedureActivity;
import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.example.bakery.utilities.InstructionsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailFragment extends Fragment implements InstructionsAdapter.InstructionClickListener {
    private List<Instruction> mInstructions;
    private List<Ingredient> mIngredients;
    private View rootView;

    public DetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle bundle = getArguments();
        Gson gson = new Gson();
        String instructionJSON = bundle.getString("instructionJSON");
        String ingredientJSON = bundle.getString("ingredientJSON");
        mInstructions = gson.fromJson(instructionJSON, new TypeToken<List<Instruction>>(){}.getType());
        mIngredients = gson.fromJson(ingredientJSON, new TypeToken<List<Ingredient>>(){}.getType());

        RecyclerView instructionsRecyclerView = rootView.findViewById(R.id.instructions_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        instructionsRecyclerView.setLayoutManager(linearLayoutManager);
        instructionsRecyclerView.setHasFixedSize(false);

        InstructionsAdapter instructionsAdapter = new InstructionsAdapter(mInstructions, this);
        instructionsRecyclerView.setAdapter(instructionsAdapter);

        TextView ingredientsItem = rootView.findViewById(R.id.ingredients_item);
        /*ingredientsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadIngredients();
            }
        });*/


        return rootView;
    }

    public void setInstructions(List<Instruction> instructions) {
        mInstructions = instructions;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public void onInstructionClick(int clickedItemIndex) {
        Instruction currentInstruction = mInstructions.get(clickedItemIndex);
        Gson gson = new Gson();
        String instructionJSON = gson.toJson(currentInstruction);

        Intent sendingIntent = new Intent(rootView.getContext(), ProcedureActivity.class);
        sendingIntent.putExtra("instructionJSON", instructionJSON);
        startActivity(sendingIntent);
    }

    private void loadIngredients() {
        Gson gson = new Gson();
        String ingredientJSON = gson.toJson(mIngredients);

        Intent sendingIntent = new Intent(getContext(), ProcedureActivity.class);
        sendingIntent.putExtra("ingredientJSON", ingredientJSON);
        startActivity(sendingIntent);
    }
}
