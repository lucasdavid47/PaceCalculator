package com.example.pacecalculator;



import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

public class PickerDialogFragment extends TimeDurationPickerDialogFragment {



    @Override
    protected long getInitialDuration() {
        return 0;
    }


    @Override
    protected int setTimeUnits() {
        return TimeDurationPicker.HH_MM_SS;
    }


    @Override
    public void onDurationSet(TimeDurationPicker view, long duration) {

        MainActivity.setPredictionNotNull(true);

        RunningActivity.predictionTimeinMillis = duration;



    }
}

