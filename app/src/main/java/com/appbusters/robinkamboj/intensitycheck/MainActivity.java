package com.appbusters.robinkamboj.intensitycheck;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.github.yongjhih.mismeter.MisMeter;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Sensor sensor;
    private SensorManager sensorManager;
    private MisMeter percentageMeter;
    private TextView currentIntensity, maxIntensity, percentageIntensity, status;
    private CardView intensityColor;

    private float max, perc;
    private HashMap<String, Integer> colorTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        colorTypes = new HashMap<>();
        colorTypes.put("0/10", R.color.colorIntensity1);
        colorTypes.put("11/20", R.color.colorIntensity2);
        colorTypes.put("21/30", R.color.colorIntensity3);
        colorTypes.put("31/40", R.color.colorIntensity4);
        colorTypes.put("41/50", R.color.colorIntensity5);
        colorTypes.put("51/60", R.color.colorIntensity6);
        colorTypes.put("61/70", R.color.colorIntensity7);
        colorTypes.put("71/80", R.color.colorIntensity8);
        colorTypes.put("81/90", R.color.colorIntensity9);
        colorTypes.put("91/100", R.color.colorIntensity10);

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


            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
