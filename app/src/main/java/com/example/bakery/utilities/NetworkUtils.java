package com.example.bakery.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.example.bakery.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    public static final String baseURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static List<Recipe> extractRecipe(URL requestURL) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(requestURL);
            Log.v(LOG_TAG, "JSON TEST:"+jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Custom Log: Error making HTTP request", e);
        }
        return extractRecipeFromJSON(jsonResponse);
    }

    public static List<Recipe> extractRecipeFromJSON(String recipeJSON) {
        List<Recipe> recipes = new ArrayList<>();
        if (TextUtils.isEmpty(recipeJSON)) {
            return null;
        }

        try {
            JSONArray root = new JSONArray(recipeJSON);
            int i;
            for (i=0; i<root.length(); i++) {
                JSONObject item = root.getJSONObject(i);
                String name = item.getString("name");

                int j;
                JSONArray ingredientsArray = item.getJSONArray("ingredients");
                List<Ingredient> ingredients = new ArrayList<>();
                for (j=0; j<ingredientsArray.length(); j++) {
                    JSONObject ingredientObject = ingredientsArray.getJSONObject(j);
                    int quantity = ingredientObject.getInt("quantity");
                    String measure = ingredientObject.getString("measure");
                    String ingredient = ingredientObject.getString("ingredient");
                    ingredients.add(new Ingredient(quantity, measure, ingredient));
                }

                JSONArray instructionsArray = item.getJSONArray("steps");
                List<Instruction> instructions = new ArrayList<>();
                for (j=0; j<instructionsArray.length(); j++) {
                    JSONObject instructionObject = instructionsArray.getJSONObject(j);
                    String shortDescription = instructionObject.getString("shortDescription");
                    String description = instructionObject.getString("description");
                    String videoURL = instructionObject.getString("videoURL");
                    if (videoURL.equals("")) {
                        videoURL = instructionObject.getString("thumbnailURL");
                    }
                    instructions.add(new Instruction(shortDescription, description, videoURL));
                }

                recipes.add(new Recipe(name, ingredients, instructions));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Custom log: Error parsing JSON", e);
        }

        return recipes;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Custom Log: Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static URL buildUrl(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Custom Log: Error with creating URL", e);
        }

        return url;
    }
}
