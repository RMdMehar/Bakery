package com.example.bakery.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakery.R;
import com.example.bakery.activity.MainActivity;
import com.example.bakery.model.Instruction;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class InstructionsFragment extends Fragment {
    public InstructionsFragment() {}

    private View rootView;

    PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    String videoURL;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer(videoURL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if (Util.SDK_INT < 24 || simpleExoPlayer == null) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        String instructionJSON = args.getString("instructionJSON");
        rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        Gson gson = new Gson();
        Instruction currentInstruction = gson.fromJson(instructionJSON, Instruction.class);

        TextView description = rootView.findViewById(R.id.description);
        description.setText(currentInstruction.getDescription());

        videoURL = currentInstruction.getVideoURL();
        playerView = rootView.findViewById(R.id.exoplayer);
        if (videoURL != null) {
            playerView.setVisibility(View.VISIBLE);
        } else {
            playerView.setVisibility(View.GONE);
        }
        return rootView;
    }

    private void initializePlayer(String videoURL) {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(rootView.getContext());
        playerView.setPlayer(simpleExoPlayer);
        Uri uri = Uri.parse(videoURL);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
        simpleExoPlayer.prepare(mediaSource, false, false);
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(rootView.getContext(), "exoplayer-bakery");
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