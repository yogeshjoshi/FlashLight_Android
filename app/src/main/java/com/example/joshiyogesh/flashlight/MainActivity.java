package com.example.joshiyogesh.flashlight;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.SensorEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton btn;
    android.hardware.Camera camera;
    Camera.Parameters parameters;
    boolean isFlash = false;
    boolean isOn = false;
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (ImageButton)findViewById(R.id.imageButton);
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera = Camera.open();
            parameters = camera.getParameters();
            isFlash = true;
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(isFlash){
                 if(!isOn){
                     btn.setImageResource(R.drawable.unnamed);
                     parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                     camera.setParameters(parameters);
                     camera.startPreview();
                     isOn = true;
                 }
                 else{
                     btn.setImageResource(R.drawable.unna);
                     parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                     camera.setParameters(parameters);
                     camera.stopPreview();
                     isOn = false;

                 }
             }

             else{
                 Toast.makeText(MainActivity.this,"Camera Not detecting",Toast.LENGTH_LONG).show();

             }

            }
        });
        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor==null){
            Toast.makeText(MainActivity.this,"Off!! Your Device Does not Support Light Sensors ",Toast.LENGTH_LONG).show();
        }else{
            sensorManager.registerListener(lightSensorEventListener,lightSensor,sensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float reading = event.values[0];
            if(reading<.5){
                btn.setImageResource(R.drawable.unnamed);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                isOn = true;

            }else{
                btn.setImageResource(R.drawable.unna);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                isOn = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    protected void onStop() {
        super.onStop();
        if(camera!=null){
            camera.release();
            camera = null;
        }
    }
}
