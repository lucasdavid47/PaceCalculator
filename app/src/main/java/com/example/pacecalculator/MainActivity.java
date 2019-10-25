package com.example.pacecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button_start_training;
    private Button button_time_picker;
    private EditText distanceSelector, distancePaceSelector;
    public static boolean predictionNotNull;


    public static void setPredictionNotNull(boolean predictionNotNull) {
        MainActivity.predictionNotNull = predictionNotNull;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        predictionNotNull = false;
        button_start_training = (findViewById(R.id.button_start_training));
        button_time_picker = (findViewById(R.id.predicateTime));
        distanceSelector = (findViewById(R.id.distanceSelector));
        distancePaceSelector = findViewById(R.id.distancePaceSelector);


        button_time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerDialogFragment().show(getFragmentManager(), "dialog");
            }
        });

        button_start_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (distanceSelector.getText().toString() != null && predictionNotNull == true && distancePaceSelector.getText().toString() != null) {
                    RunningActivity.distance = Long.valueOf(distanceSelector.getText().toString());
                    RunningActivity.distancePace = Long.valueOf(distancePaceSelector.getText().toString());
                    Intent openTrainingActivity = new Intent(getApplicationContext(), RunningActivity.class);
                    startActivity(openTrainingActivity);
                    finish();
                } else if (distanceSelector.getText() == null){
                    Toast.makeText(getApplicationContext(),"Merci d'entrer une distance pour votre entraînement.",Toast.LENGTH_LONG).show();
               }  else if(predictionNotNull == false){
                    Toast.makeText(getApplicationContext(), "Merci d'entrer votre prédiction", Toast.LENGTH_LONG).show();
                }else if(distancePaceSelector.toString() == null){
                    Toast.makeText(getApplicationContext(), "Merci D'entrer une distance de passage.", Toast.LENGTH_LONG).show();
                }
         }
        });

    }
}
