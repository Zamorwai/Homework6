package com.karome.homework6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    private Button buttonPause;
    private Button buttonStop;
    private Button buttonRevers;
    private TextView stopwatchOut;

    private long startTime = 0L;
    private long timeInMilliSeconds = 0L;
    private long timePause = 0L;
    private long reversTime = 0L;
    private long updatedTime = 0L;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonStop = findViewById(R.id.buttonStop);
        buttonStart = findViewById(R.id.buttonStart);
        buttonRevers = findViewById(R.id.buttonRevers);
        stopwatchOut = findViewById(R.id.stopwatchOut);

        buttonStart.setOnClickListener(listener);
        buttonStop.setOnClickListener(listener);
        buttonPause.setOnClickListener(listener);

        buttonRevers.setOnClickListener(event -> {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(updateReversTimeThread, 0);
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonStart:
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimeThread, 0);
                    break;
                case R.id.buttonPause:
                    timePause += timeInMilliSeconds;
                    handler.removeCallbacks(updateTimeThread);
                    handler.removeCallbacks(updateReversTimeThread);
                    break;
                case R.id.buttonStop:
                    startTime = 0L;
                    handler.removeCallbacks(updateTimeThread);
                    handler.removeCallbacks(updateReversTimeThread);
                    stopwatchOut.setText(getString(R.string.messageStop));
                    break;
            }
        }
    };

    private Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timePause + timeInMilliSeconds;

            int miliseconds = (int) (updatedTime % 1000);
            int second = (int) (updatedTime / 1000);
            int minute = second / 60;
            int hour = minute / 60;

            stopwatchOut.setText("" + hour + ":" + "" + minute + ":" + String.format("%02d", second) + ":" + String.format("%03d", miliseconds));
            handler.postDelayed(this, 0);
        }
    };

    private Runnable updateReversTimeThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timePause - timeInMilliSeconds;

            int miliseconds = (int) (updatedTime % 1000);
            int second = (int) (updatedTime / 1000);
            int minute = second / 60;
            int hour = minute / 60;

            stopwatchOut.setText("" + hour + ":" + "" + minute + ":" + String.format("%02d", second) + ":" + String.format("%03d", miliseconds));
            handler.postDelayed(this, 0);
        }
    };
}