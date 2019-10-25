package com.example.pacecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RunningActivity extends AppCompatActivity {

    Button btnStart, btnStop, btnLap, btnReset;
    TextView txtTimer,objectiveDisplay;
    Handler customHandler = new Handler();
    LinearLayout container;
    boolean isRunning;
    public static Long distancePace;
    public static long predictionTimeinMillis, distance;
    long lastLap,buffer;
    /** The number of milliseconds within a second. */
    public static final int MILLIS_PER_SECOND = 1000;
    /** The number of milliseconds within a minute. */
    public static final int MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /** The number of milliseconds within an hour. */
    public static final int MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    Date date;


    long startTime = 0L, timeInMilliseconds=0L, timeSwapBuff=0L,updateTime=0L;


    @Override
    public void onBackPressed() {
        Intent openMainActivty = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(openMainActivty);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        buffer=0;
        txtTimer = findViewById(R.id.timerValue);
        btnStart = findViewById(R.id.button_start_chronometer);
        btnLap = findViewById(R.id.button_lap_chronometer);
        btnStop = findViewById(R.id.button_stop_chronometer);
        btnReset = findViewById(R.id.button_reset_chronometer);
        container = findViewById(R.id.container);
        objectiveDisplay = findViewById(R.id.objective);


            final Long timeAtPace = predictionTimeinMillis /(distance/distancePace);
            System.out.println(distancePace);

            //Displaying objective pace
            int secs = (int) (timeAtPace / 1000);
            int min = secs / 60;
            secs %= 60;
            int milliseconds = (int) (timeAtPace % 1000);
            TextView objective = findViewById(R.id.objective);
            objective.setText("Voici le temps de passage au " + distancePace.toString() + "m a respecter: " + String.format("%02d" + "''",secs) +":" + String.format("%03d" + "'''", milliseconds));






        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning =true;
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    customHandler.removeCallbacks(updateTimerThread);
                    customHandler.postDelayed(updateTimerThread, 2000);
                    txtTimer.setText("00:00:000");
                    container.removeAllViews();
                    startTime = 0L;
                    buffer=0;
                    timeInMilliseconds=0L;
                    timeSwapBuff=0L;
                    updateTime=0L;
                }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff = timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                isRunning = false;
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning) {
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = inflater.inflate(R.layout.row, null);
                    TextView txtValue = addView.findViewById(R.id.txtContent);
                    lastLap=updateTime-buffer;
                    DateFormat format = new SimpleDateFormat("mm:ss:SSS");
                    if(predictionTimeinMillis/(distance/distancePace)-lastLap >=0) {
                        date = new Date(predictionTimeinMillis/(distance/distancePace) - lastLap);
                    }if(lastLap >= predictionTimeinMillis/(distance/distancePace)){
                        date = new Date(lastLap-predictionTimeinMillis/(distance/distancePace));
                    }
                        if (predictionTimeinMillis/(distance/distancePace) < lastLap) {

                            txtValue.setText(txtTimer.getText() + " En retard: +" + format.format(date));
                            container.addView(addView);
                            buffer=updateTime;
                        } else if (predictionTimeinMillis/(distance/distancePace) > lastLap) {
                            txtValue.setText(txtTimer.getText() + " En avance: -" + format.format(date));
                            container.addView(addView);
                            buffer=updateTime;
                        }

                }else{
                    Toast.makeText(RunningActivity.this, "Vous devez d'abord lancer le chronomètre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updateTime / 1000);
            int min = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            txtTimer.setText(String.format("%02d", min) + ":" + String.format("%02d",secs) +":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    public long convertStringToDate(String date){
        long dateInMilliseconds=0;
        SimpleDateFormat format = new SimpleDateFormat("m:ss.SSS");
        try {
            dateInMilliseconds = format.parse(date).getTime();
        }catch(ParseException e){
            e.printStackTrace();
        }
        return dateInMilliseconds;
    }
}
