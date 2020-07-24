package com.example.bakery.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bakery.R;
import com.example.bakery.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IngredientsAdapter extends ArrayAdapter<Ingredient> {
    public IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        super(context, 0, ingredients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View instructionListItemView = convertView;
        if (instructionListItemView == null) {
            instructionListItemView = LayoutInflater.from(getContext()).inflate(R.layout.ingredients_item, parent, false);
        }
        Ingredient currentIngredient = getItem(position);
        TextView quantity = instructionListItemView.findViewById(R.id.quantity);
        quantity.setText(String.valueOf(currentIngredient.getQuantity()));

        TextView measure = instructionListItemView.findViewById(R.id.measure);
        measure.setText(currentIngredient.getMeasure());

        TextView ingredient = instructionListItemView.findViewById(R.id.ingredient);
        ingredient.setText(currentIngredient.getIngredient());

        return instructionListItemView;
    }
}
