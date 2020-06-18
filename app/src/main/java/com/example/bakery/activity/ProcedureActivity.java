package com.example.bakery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bakery.R;
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
    PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    String videoURL;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer(videoURL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT < 24 || simpleExoPlayer == null)) {
            initializePlayer(videoURL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedure);

        Gson gson = new Gson();

        TextView description = findViewById(R.id.description);
        playerView = findViewById(R.id.video_view);

        Intent intent = getIntent();
        if (intent.hasExtra("instructionJSON")) {
            playerView.setVisibility(View.VISIBLE);
            String instructionJSON = intent.getStringExtra("instructionJSON");
            Instruction currentInstruction = gson.fromJson(instructionJSON, Instruction.class);
            description.setText(currentInstruction.getDescription());

            videoURL = currentInstruction.getVideoURL();



        } else if (intent.hasExtra("ingredientJSON")) {
            playerView.setVisibility(View.GONE);

            String ingredientJSON = intent.getStringExtra("ingredientJSON");
            List<Ingredient> ingredientList = gson.fromJson(ingredientJSON, new TypeToken<List<Ingredient>>(){}.getType());
            StringBuilder ingredients = new StringBuilder("");
            int i;
            for (i=0; i<ingredientList.size(); i++) {
                Ingredient currentIngredient = ingredientList.get(i);
                int quantity = currentIngredient.getQuantity();
                String measure = currentIngredient.getMeasure();
                if (measure.equals("UNIT")) {
                    measure = "";
                }
                String ingredient = currentIngredient.getIngredient();
                ingredients.append(quantity).append(measure).append(ingredient);


            }
        }
    }

    private void initializePlayer(String videoURL) {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(simpleExoPlayer);
        Uri uri = Uri.parse(videoURL);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);
        simpleExoPlayer.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, "exoplayer-bakery");
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}
