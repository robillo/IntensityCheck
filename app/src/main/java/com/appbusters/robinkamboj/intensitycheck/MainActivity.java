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
    private HashMap<String, String> colorTypes;
    private String[] colors, colorHeaders;
    private String CIC = "0/10"; //Current Intensity Color

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors = getResources().getStringArray(R.array.intensity_shades);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        colorHeaders = new String[]{"0/10", "11/20", "21/30", "31/40", "41/50", "51/60", "61/70", "71/80", "81/90", "91/100"};
        colorTypes = new HashMap<>();
        addToColorTypes();

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

            for(String tempString: colorTypes.keySet()){
                if(CIC.equals(tempString)){
                    CIC = tempString;
                    return;
                }
            }

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

    private void addToColorTypes(){
        for(int i=0; i<10; i++){
            colorTypes.put( colorHeaders[i], colors[i]);
        }
    }
}
