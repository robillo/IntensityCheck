package com.appbusters.robinkamboj.intensitycheck;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.TextView;

import com.github.yongjhih.mismeter.MisMeter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Sensor sensor;
    private SensorManager sensorManager;
    private MisMeter percentageMeter;
    private TextView currentIntensity, maxIntensity, percentageIntensity, status, userName;
    private CardView intensityColor;

    private float max, perc;
    private int color;

    //Firebase Instance Variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        userName = (TextView) findViewById(R.id.user_name);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            //Not signed in, launch GoogleActivity
            startActivity(new Intent(this, GoogleActivity.class));
            finish();
            return;
        }
        else {
            userName.setText(firebaseUser.getDisplayName());
        }

        currentIntensity = (TextView) findViewById(R.id.current);
        maxIntensity = (TextView) findViewById(R.id.maximum);
        percentageIntensity = (TextView) findViewById(R.id.percentage);
        percentageMeter = (MisMeter) findViewById(R.id.percentage_meter);
        status = (TextView) findViewById(R.id.status);
        intensityColor = (CardView) findViewById(R.id.color);

        max = sensor.getMaximumRange();
        maxIntensity.setText(String.valueOf(max));

        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float curr = sensorEvent.values[0];
            currentIntensity.setText(String.valueOf(curr));
            perc = (curr/max);
            String temp = String.format(Locale.getDefault(), "%.3g", perc*100);
            percentageIntensity.setText(temp);
            percentageMeter.setProgress(perc);

            float percDash = perc*100;

            if(percDash<=10){
                color = getResources().getColor(R.color.colorIntensity1);
            }
            else if(percDash<=20){
                color = getResources().getColor(R.color.colorIntensity2);
            }
            else if(percDash<=30){
                color = getResources().getColor(R.color.colorIntensity3);
            }
            else if(percDash<=40){
                color = getResources().getColor(R.color.colorIntensity4);
            }
            else if(percDash<=50){
                color = getResources().getColor(R.color.colorIntensity5);
            }
            else if(percDash<=60){
                color = getResources().getColor(R.color.colorIntensity6);
            }
            else if(percDash<=70){
                color = getResources().getColor(R.color.colorIntensity7);
            }
            else if(percDash<=80){
                color = getResources().getColor(R.color.colorIntensity8);
            }
            else if(percDash<=90){
                color = getResources().getColor(R.color.colorIntensity9);
            }
            else if(percDash<=100){
                color = getResources().getColor(R.color.colorIntensity10);
            }

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    intensityColor.setCardBackgroundColor(color);
                }
            });
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
