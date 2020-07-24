package com.example.bakery.fragment;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailFragment extends Fragment implements InstructionsAdapter.InstructionClickListener {
    private List<Instruction> mInstructions;
    private List<Ingredient> mIngredients;
    private View rootView;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    OnIngredientItemClickListener mIngredientCallback;
    OnInstructionItemClickListener mInstructionCallback;

    public DetailFragment() {}

    public interface OnIngredientItemClickListener {
        void onIngredientSelected(String selectedIngredientJSON);
    }

    public interface OnInstructionItemClickListener {
        void onInstructionSelected(Instruction selectedInstruction);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mIngredientCallback = (OnIngredientItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnIngredientItemClickListener");
        }

        try {
            mInstructionCallback = (OnInstructionItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnInstructionItemClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String instructionJSON = bundle.getString("instructionJSON");
        String ingredientJSON = bundle.getString("ingredientJSON");

        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Gson gson = new Gson();

        mInstructions = gson.fromJson(instructionJSON, new TypeToken<List<Instruction>>(){}.getType());




        RecyclerView instructionsRecyclerView = rootView.findViewById(R.id.instructions_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
        instructionsRecyclerView.setLayoutManager(linearLayoutManager);
        instructionsRecyclerView.setHasFixedSize(false);

        InstructionsAdapter instructionsAdapter = new InstructionsAdapter(mInstructions, this);
        instructionsRecyclerView.setAdapter(instructionsAdapter);

        TextView ingredientsItem = rootView.findViewById(R.id.ingredients_item);
        ingredientsItem.setOnClickListener(view -> mIngredientCallback.onIngredientSelected(ingredientJSON));

        return rootView;
    }

    @Override
    public void onInstructionClick(int clickedItemIndex) {
        Instruction currentInstruction = mInstructions.get(clickedItemIndex);
        mInstructionCallback.onInstructionSelected(currentInstruction);
    }

    private void loadIngredients(String ingredientJSON) {
        Intent sendingIntent = new Intent(getContext(), ProcedureActivity.class);
        sendingIntent.putExtra("ingredientJSON", ingredientJSON);
        startActivity(sendingIntent);
    }
}
