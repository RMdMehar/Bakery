package com.example.bakery.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bakery.R;
import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.example.bakery.utilities.IngredientsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class IngredientsFragment extends Fragment {

    private static final String LOG_TAG = IngredientsFragment.class.getSimpleName();
    public IngredientsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        String ingredientJSON = args.getString("ingredientJSON");
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);



        Gson gson = new Gson();
        List<Ingredient> ingredientList = gson.fromJson(ingredientJSON, new TypeToken<List<Ingredient>>(){}.getType());
        ListView ingredientsListView = rootView.findViewById(R.id.ingredients_list);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(rootView.getContext(), ingredientList);
        ingredientsListView.setAdapter(ingredientsAdapter);

        return rootView;
    }
}
