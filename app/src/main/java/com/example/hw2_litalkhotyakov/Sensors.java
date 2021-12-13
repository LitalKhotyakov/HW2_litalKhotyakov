package com.example.hw2_litalkhotyakov;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw2_litalkhotyakov.R;
import com.example.hw2_litalkhotyakov.activities.GameActivity;
import com.example.hw2_litalkhotyakov.fragments.callBacks.SensorCallBack;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;

public class Sensors {

    public static final String SENSOR_TYPE = "SENSOR_TYPE";


    private SensorManager sensorManager;
    private android.hardware.Sensor accSensor;
    private SensorCallBack sensorCallBack;
    private  Context mContext;

    public Sensors() {}

    public Sensors(Context mContext) {
        this.mContext = mContext;
    }

    private void initSensor() {
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);
    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            DecimalFormat df = new DecimalFormat("##.##");
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if (event.values[0] < -2) {
                sensorCallBack.rightDirection();
            }
            if (event.values[0] > 2) {
                sensorCallBack.leftDirection();
            }
            if (event.values[1] < -2){
                sensorCallBack.goFaster();

            }
        }

        @Override
        public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

        }
    };

    public void registerListener(){
        sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener(){
        sensorManager.unregisterListener(accSensorEventListener);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        sensorManager.unregisterListener(accSensorEventListener);
//    }

    public boolean isSensorExist(int sensorType) {
        return (sensorManager.getDefaultSensor(sensorType) != null);
    }


}
