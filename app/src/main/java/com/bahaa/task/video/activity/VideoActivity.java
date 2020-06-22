package com.bahaa.task.video.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bahaa.task.R;
import com.bahaa.task.core.utils.Utils;
import com.bumptech.glide.util.Util;

public class VideoActivity extends AppCompatActivity {

    private VideoView mainVideoView;
    private ImageView playBtn;
    private TextView currentTimer;
    private TextView durationTimer;
    private ProgressBar currentProgress;
    private ProgressBar bufferProgress;

    private boolean isPlaying;

    private Uri videoUri;
    private int current = 0;
    private int duration = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
    }

    private void init() {
        if (Utils.isNetworkConnected(this)) {

            isPlaying = false;
            mainVideoView = findViewById(R.id.videoView);
            playBtn = findViewById(R.id.play_btn);
            currentProgress = findViewById(R.id.current_progress);
            bufferProgress = findViewById(R.id.buffer_progress);
            currentTimer = findViewById(R.id.current_timer);
            durationTimer = findViewById(R.id.duration_Timer);

            Intent intent = getIntent();
            videoUri = Uri.parse(intent.getStringExtra("url"));

            mainVideoView.setVideoURI(videoUri);
            mainVideoView.requestFocus();
            mainVideoView.setOnPreparedListener(mp -> {
                bufferProgress.setVisibility(View.INVISIBLE);

                new VideoProgress().execute();

                duration = mp.getDuration() / 1000;
                String durationString = String.format("%02d:%02d", duration / 60, duration % 60);
                durationTimer.setText(durationString);


            });

            mainVideoView.start();
            isPlaying = true;
            playBtn.setImageResource(R.drawable.ic_pause_icon);

            playBtn.setOnClickListener(v -> {
                if (isPlaying) {
                    mainVideoView.pause();
                    isPlaying = false;
                    playBtn.setImageResource(R.drawable.ic_play_icon);

                } else {
                    mainVideoView.start();
                    isPlaying = true;
                    playBtn.setImageResource(R.drawable.ic_pause_icon);
                }
            });

        } else {
            Toast.makeText(this, getString(R.string.internet_check), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isPlaying = false;
        playBtn.setImageResource(R.drawable.ic_play_icon);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isPlaying = false;
        playBtn.setImageResource(R.drawable.ic_play_icon);
    }

    public class VideoProgress extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            do {
                if (isPlaying) {
                    current = mainVideoView.getCurrentPosition() / 1000;
                    publishProgress(current);
                }
            } while (currentProgress.getProgress() <= 100);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                int currentPercent = values[0] * 100 / duration;
                currentProgress.setProgress(currentPercent);
                String currentString = String.format("%02d:%02d", values[0] / 60, values[0] % 60);
                currentTimer.setText(currentString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
