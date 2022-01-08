package com.shreyansh.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView countDownText;
    SeekBar seekBar;
    Button button;
    CountDownTimer countDownTimer;
    boolean active = false;

    public void startTimer(View view) {

        if(active) {
            active = false;
            countDownText.setText("0:30");
            countDownTimer.cancel();
            seekBar.setEnabled(true);
            seekBar.setProgress(30);
            button.setText("GO!");
        }
        else {
            seekBar.setEnabled(false);
            button.setText("STOP!");
            active = true;

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    update((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.buzzer);
                    player.start();
                    button.setText("RESET!");
                }
            };
            countDownTimer.start();
        }
    }

    public void update(int left) {
        String minutes = (left / 60) + "";
        String seconds = (left % 60) + "";

        if(seconds.length() == 1)
            seconds = "0" + seconds;

        countDownText.setText(minutes + ":" + seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        button = findViewById(R.id.button);

        countDownText = findViewById(R.id.countdownText);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}