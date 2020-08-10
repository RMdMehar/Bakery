package com.example.bakery.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.bakery.R;
import com.example.bakery.model.Recipe;
import com.example.bakery.utilities.MainAdapter;
import com.example.bakery.utilities.NetworkUtils;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainAdapter.CardClickListener {

    private MainAdapter mainAdapter;
    private RecyclerView mainRecyclerView;
    private boolean mTwoPane;
    public static boolean backgroundWorkDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.grid_recycler_view) != null) {
            mTwoPane = true;

            mainRecyclerView = findViewById(R.id.grid_recycler_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mainRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            mTwoPane = false;

            mainRecyclerView = findViewById(R.id.main_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mainRecyclerView.setLayoutManager(linearLayoutManager);
        }
        mainRecyclerView.setHasFixedSize(true);
        mainAdapter = new MainAdapter(new ArrayList<Recipe>(), this);
        mainRecyclerView.setAdapter(mainAdapter);
        makeQuery();
    }

    public void makeQuery() {
        Uri builtUri = Uri.parse(NetworkUtils.baseURL);
        URL url = NetworkUtils.buildUrl(builtUri.toString());
        new RecipeTask().execute(url);
    }

    @Override
    public void onCardClick(int clickedItemIndex) {
        Recipe currentRecipe = mainAdapter.getRecipeByPosition(clickedItemIndex);
        Gson gson = new Gson();
        String recipeJSON = gson.toJson(currentRecipe);
        Intent intent;
        if(mTwoPane) {
            intent = new Intent(MainActivity.this, DetailsAndProcedureActivity.class);
        } else {
            intent = new Intent(MainActivity.this, DetailActivity.class);
        }
        intent.putExtra("recipeJSON", recipeJSON);
        startActivity(intent);
    }

    public class RecipeTask extends AsyncTask<URL, Void, List<Recipe>> {
        @Override
        protected List<Recipe> doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            return NetworkUtils.extractRecipe(searchUrl);
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
            mainAdapter.setRecipeList(recipeList);
            mainRecyclerView.setAdapter(mainAdapter);
            backgroundWorkDone = true;
            super.onPostExecute(recipeList);
        }
    }
}
