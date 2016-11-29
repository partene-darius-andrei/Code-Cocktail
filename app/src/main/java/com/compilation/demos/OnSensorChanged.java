package com.compilation.demos;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.compilation.R;

public class OnSensorChanged extends Activity {


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private double sensitivity = 0.35;
    private boolean isRecording = false;
    private int frequency = 50;

    private SensorEventListener sensorEventListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_sensor_changed);

        final TextView taps = (TextView) findViewById(R.id.taps);

        final TextView sensitivityTextView = (TextView) findViewById(R.id.sensitivity);
        final TextView frequencyTextView = (TextView) findViewById(R.id.frequency);

        sensitivityTextView.setText(String.valueOf(sensitivity));
        frequencyTextView.setText(String.valueOf(frequency));
        findViewById(R.id.decrease_sensitivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensitivity -= 0.05;
                sensitivityTextView.setText(String.format("%.2f", sensitivity));
            }
        });
        findViewById(R.id.increase_sensitivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensitivity += 0.05;
                sensitivityTextView.setText(String.format("%.2f", sensitivity));
            }
        });

        findViewById(R.id.decrease_frequency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequency -= 10;
                frequencyTextView.setText(String.valueOf(frequency));
            }
        });
        findViewById(R.id.increase_frequency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frequency += 10;
                frequencyTextView.setText(String.valueOf(frequency));
            }
        });

        final Button startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording) {
                    isRecording = false;
                    sensorManager.unregisterListener(sensorEventListener);
                    startButton.setText("Play");
                } else {
                    isRecording = true;
                    sensorManager.registerListener(sensorEventListener, accelerometer, 5);
                    startButton.setText("Pause");
                }
            }
        });

        sensorEventListener = new SensorEventListener() {

            float previous = 0;
            private int tapCount = 0;
            String tapIntensity = "";
            boolean canDetect = true;

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                final float current = sensorEvent.values[2];

                if (canDetect){
                    if (current - previous > sensitivity) {
                        canDetect = false;
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                canDetect= true;
                            }
                        }, frequency);
                        tapIntensity += "= ";
                        tapCount++;
                        taps.setText(String.valueOf(tapCount));
                    }
                    else {
                        tapIntensity = "";
                    }
                    log(tapIntensity);
                    previous = current;

                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            //we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        }
    }

    public void log(String message) {
        Log.i("SENSOR", message);
    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        if (isRecording) sensorManager.registerListener(sensorEventListener, accelerometer, 5);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

}

