package com.example.tesseract_aidl_demo;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Arrays;

import androidx.annotation.Nullable;

public class MyService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;

    private String data;
    private static final int SENSOR_DELAY = 8 * 1000;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        sensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, SENSOR_DELAY);

        return binder;
    }

    private final IOrientation.Stub binder = new IOrientation.Stub() {
        @Override
        public String orientation() throws RemoteException {
            return String.valueOf(data);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor == sensor) {
            data = Arrays.toString(sensorEvent.values);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
