package com.example.bakery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bakery.R;
import com.example.bakery.fragment.IngredientsFragment;
import com.example.bakery.fragment.InstructionsFragment;
import com.example.bakery.model.Ingredient;
import com.example.bakery.model.Instruction;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ProcedureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedure);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        if (intent.hasExtra("instructionJSON")) {
            String instructionJSON = intent.getStringExtra("instructionJSON");

            Bundle args = new Bundle();
            args.putString("instructionJSON", instructionJSON);
            InstructionsFragment instructionsFragment = new InstructionsFragment();
            instructionsFragment.setArguments(args);

            fragmentManager.beginTransaction()
                    .add(R.id.procedure_portrait_fragment, instructionsFragment)
                    .commit();

        } else if (intent.hasExtra("ingredientJSON")) {
            String ingredientJSON = intent.getStringExtra("ingredientJSON");

            Bundle args = new Bundle();
            args.putString("ingredientJSON", ingredientJSON);
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(args);

            fragmentManager.beginTransaction()
                    .add(R.id.procedure_portrait_fragment, ingredientsFragment)
                    .commit();
        }
    }
}