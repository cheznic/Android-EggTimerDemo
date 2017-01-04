package com.whimsygames.eggtimerdemo;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int time = 0;
    private TextView textView;
    CountDownTimer timer;
    Button goButton;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        goButton = (Button) findViewById(R.id.go);
        goButton.setEnabled(false);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
        SeekBar timeAdjustSlide = (SeekBar) findViewById(R.id.seekBar);

        timeAdjustSlide.setMax(600 / 5);

        timeAdjustSlide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                time = i * 5;
                String result = String.format("%02d:%02d", time / 60, time % 60);
                textView.setText(result);

                if (time > 0)
                    goButton.setEnabled(true);
                else
                    goButton.setEnabled(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected void go(View view) {

        if (timer == null) {
            timer = new CountDownTimer(time * 1000, 1000) {
                public void onTick(long milliSecondsUntilDone) {
                    String result = String.format("%02d:%02d", milliSecondsUntilDone / 1000 / 60, milliSecondsUntilDone / 1000 % 60);
                    textView.setText(result);
                }

                public void onFinish() {
                    goButton.setText("Go!");
                    timer = null;
                    mediaPlayer.start();
                    String result = String.format("%02d:%02d", time / 60, time % 60);
                    textView.setText(result);
                }
            }.start();

            goButton.setText("Stop");
        } else {
            timer.cancel();
            timer = null;
            goButton.setText("Go!");
            String result = String.format("%02d:%02d", time / 60, time % 60);
            textView.setText(result);
        }
    }

}
