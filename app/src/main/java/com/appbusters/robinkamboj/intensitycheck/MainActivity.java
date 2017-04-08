package com.appbusters.robinkamboj.intensitycheck;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.yongjhih.mismeter.MisMeter;

public class MainActivity extends AppCompatActivity {

    private Sensor sensor;
    private SensorManager sensorManager;
    private MisMeter percentageMeter;
    private TextView currentIntensity, maxIntensity, percentageIntensity;
    private Boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        currentIntensity = (TextView) findViewById(R.id.current);
        maxIntensity = (TextView) findViewById(R.id.maximum);
        percentageIntensity = (TextView) findViewById(R.id.percentage);
        percentageMeter = (MisMeter) findViewById(R.id.percentage_meter);
    }
}
