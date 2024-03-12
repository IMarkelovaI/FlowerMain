package com.example.floweraplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.hardware.Sensor;
import android.hardware.SensorManager;
public class LightingActivity extends AppCompatActivity {

    private SensorManager sm;
    private Sensor s;
    private ImageView im;
    private TextView tv;
    private SensorEventListener sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting);
        tv = findViewById(R.id.tv);
        im = findViewById(R.id.im);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sm !=null)s = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sv = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {


                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix, event.values);
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_X,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);

// Convert to orientations
                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);
                for(int i = 0; i < 3; i++) {
                    orientations[i] = (float)(Math.toDegrees(orientations[i]));
                }


                tv.setText(String.valueOf((int)orientations[2]));
                im.setRotation(-orientations[2]);


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };



    }
    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sv,s,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sv);
    }
}