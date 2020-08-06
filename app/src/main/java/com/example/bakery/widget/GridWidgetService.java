package com.example.bakery.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakery.R;
import com.example.bakery.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    List<Ingredient> ingredientList;

    public GridRemoteViewsFactory(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String lastSeenIngredientsJSON = sharedPreferences.getString("lastSeenIngredientsJSON", "");
        Gson gson = new Gson();
        ingredientList = gson.fromJson(lastSeenIngredientsJSON, new TypeToken<List<Ingredient>>(){}.getType());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredientList == null) {
            return 0;
        } else {
            return ingredientList.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Ingredient currentIngredient = ingredientList.get(i);
        String ingredient = currentIngredient.getIngredient();
        String quantity = String.valueOf(currentIngredient.getQuantity());
        String measure = currentIngredient.getMeasure();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_item_widget);
        remoteViews.setTextViewText(R.id.widget_ingredient, ingredient);
        remoteViews.setTextViewText(R.id.widget_quantity, quantity);
        remoteViews.setTextViewText(R.id.widget_measure, measure);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
