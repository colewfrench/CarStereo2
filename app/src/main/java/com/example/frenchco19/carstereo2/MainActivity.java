package com.example.frenchco19.carstereo2;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

    private ToggleButton powerButton = null;
    private EditText volumeIndicator = null;
    private EditText stationDisplay = null;

    private Button presetButton1 = null;
    private Button presetButton2 = null;
    private Button presetButton3 = null;
    private Button presetButton4 = null;
    private Button presetButton5 = null;

    private SeekBar tuner = null;

    private Switch amFmSwitch = null;
    private int curAmRangeValue = 530;
    private int curFmRangeValue = 881;

    private boolean radioOn = false;
    private boolean isAm = true;

    private int[] amPresets = {550,600,650,700,750,800};
    private double[] fmPresets = {90.9,92.9,94.9,96.9,98.9,100.9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerButton = (ToggleButton)findViewById(R.id.powerButton);
        powerButton.setOnCheckedChangeListener(new PowerButtonListener());

        volumeIndicator = (EditText)findViewById(R.id.volumeIndicator);
        volumeIndicator.setText("100");

        stationDisplay = (EditText)findViewById(R.id.stationDisplay);
        stationDisplay.setText(curAmRangeValue + " AM");

        presetButton1 = (Button)findViewById(R.id.preset1);
        presetButton2 = (Button)findViewById(R.id.preset2);
        presetButton3 = (Button)findViewById(R.id.preset3);
        presetButton4 = (Button)findViewById(R.id.preset4);
        presetButton5 = (Button)findViewById(R.id.preset5);

        presetButton1.setOnClickListener(new PresetButtonListener());
        presetButton2.setOnClickListener(new PresetButtonListener());
        presetButton3.setOnClickListener(new PresetButtonListener());
        presetButton4.setOnClickListener(new PresetButtonListener());
        presetButton5.setOnClickListener(new PresetButtonListener());

        presetButton1.setOnLongClickListener(new SetPresetListener());
        presetButton2.setOnLongClickListener(new SetPresetListener());
        presetButton3.setOnLongClickListener(new SetPresetListener());
        presetButton4.setOnLongClickListener(new SetPresetListener());
        presetButton5.setOnLongClickListener(new SetPresetListener());

        amFmSwitch = (Switch)findViewById(R.id.amFmSwitch);
        amFmSwitch.setOnCheckedChangeListener(new AmFmSwitchListener());

        tuner = (SeekBar)findViewById(R.id.tuner);
        tuner.setOnSeekBarChangeListener(new TunerListener());
        tuner.setProgress(0);
        tuner.setMax(1170);
    }

    private class PowerButtonListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                powerButton.setTextColor(Color.CYAN);
                volumeIndicator.setTextColor(Color.CYAN);
                stationDisplay.setTextColor(Color.CYAN);
                presetButton1.setTextColor(Color.CYAN);
                radioOn = true;
            }
            else {
                powerButton.setTextColor(Color.BLACK);
                volumeIndicator.setTextColor(Color.BLACK);
                stationDisplay.setTextColor(Color.BLACK);
                presetButton1.setTextColor(Color.BLACK);
                radioOn = false;
            }
        }
    }

    private class AmFmSwitchListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b == true)
            {
                stationDisplay.setText(curFmRangeValue + " MHz FM");
                isAm = false;
            }
            else
            {
                stationDisplay.setText(curAmRangeValue + " kHz AM");
                isAm = true;
            }
        }
    }

    private class TunerListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (radioOn){
                if (amFmSwitch.isChecked()) {
                    tuner.setMax(198);

                    if (i % 2 == 1) {
                        i = i - 1;
                    }

                    i = i + 881;

                    int front = i / 10;
                    int back = i % 10;

                    stationDisplay.setText(front + "." + back + " MHz FM");

                    curFmRangeValue = i;
                } else {
                    tuner.setMax(1170);
                    i = i / 10;
                    i = i * 10 + 530;

                    stationDisplay.setText(i + " kHz AM");
                    curAmRangeValue = i;
                }
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class PresetButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (radioOn == true) {
                if (isAm == true) {
                    if (view == presetButton1) {
                        stationDisplay.setText(amPresets[0] + " kHz AM");
                    } else if (view == presetButton2) {
                        stationDisplay.setText(amPresets[1] + " kHz AM");
                    } else if (view == presetButton2) {
                        stationDisplay.setText(amPresets[2] + " kHz AM");
                    } else if (view == presetButton3) {
                        stationDisplay.setText(amPresets[3] + " kHz AM");
                    } else if (view == presetButton4) {
                        stationDisplay.setText(amPresets[4] + " kHz AM");
                    } else if (view == presetButton5) {
                        stationDisplay.setText(amPresets[5] + " kHz AM");
                    }
                } else {
                    if (view == presetButton1) {
                        stationDisplay.setText(fmPresets[0] + " MHz FM");
                    } else if (view == presetButton2) {
                        stationDisplay.setText(fmPresets[1] + " MHz FM");
                    } else if (view == presetButton2) {
                        stationDisplay.setText(fmPresets[2] + " MHz FM");
                    } else if (view == presetButton3) {
                        stationDisplay.setText(fmPresets[3] + " MHz FM");
                    } else if (view == presetButton4) {
                        stationDisplay.setText(fmPresets[4] + " MHz FM");
                    } else if (view == presetButton5) {
                        stationDisplay.setText(fmPresets[5] + " MHz FM");
                    }
                }
            }
        }
    }

    private class SetPresetListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            if (radioOn == true) {
                if (isAm == true) {
                    if (view == presetButton1) {
                        amPresets[0] = (tuner.getProgress()/10)*10 + 530;
                    } else if (view == presetButton2) {
                        amPresets[1] = (tuner.getProgress()/10)*10  + 530;
                    } else if (view == presetButton2) {
                        amPresets[2] = (tuner.getProgress()/10)*10 + 530;
                    } else if (view == presetButton3) {
                        amPresets[3] = (tuner.getProgress()/10)*10 + 530;
                    } else if (view == presetButton4) {
                        amPresets[4] = (tuner.getProgress()/10)*10 + 530;
                    } else if (view == presetButton5) {
                        amPresets[5] = (tuner.getProgress()/10)*10 + 530;
                    }
                } else {
                    if (view == presetButton1) {
                        fmPresets[0] = tuner.getProgress() + 881;
                    } else if (view == presetButton2) {
                        fmPresets[1] = tuner.getProgress() + 881;
                    } else if (view == presetButton2) {
                        fmPresets[2] = tuner.getProgress() + 881;
                    } else if (view == presetButton3) {
                        fmPresets[3] = tuner.getProgress() + 881;
                    } else if (view == presetButton4) {
                        fmPresets[4] = tuner.getProgress() + 881;
                    } else if (view == presetButton5) {
                        fmPresets[5] = tuner.getProgress() + 881;
                    }
                }
            }
            return false;
        }
    }
}
