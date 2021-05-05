package tdc.edu.vn.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometter;
    private Sensor sensorMagnetic;
    private float[] mGravity = new float[3];
    private float[] valueAccelerometer = new float[3];
    private float[] rotationMatrix = new float[9];
    private float lastDirectionInDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img_compass);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorAccelerometter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            calculateRotation(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateRotation(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                valueAccelerometer = event.values.clone();
                        break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGravity = event.values.clone();
                break;
        }

        boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, valueAccelerometer, mGravity);
        if(success){
            float[] orientation = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientation);
            float azimuth = (float) Math.toDegrees(-orientation[0]);
            RotateAnimation rotateAnimation = new RotateAnimation(
                    lastDirectionInDegree,
                    azimuth,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );

            rotateAnimation.setDuration(50);
            rotateAnimation.setFillAfter(true);
            img.startAnimation(rotateAnimation);
            lastDirectionInDegree = azimuth;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(sensorAccelerometter != null && sensorMagnetic != null){
            sensorManager.registerListener(sensorEventListener, sensorAccelerometter,SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(sensorEventListener, sensorMagnetic,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(this, "Error! Sensor Accelerometer / Magnetic not found. ",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(sensorAccelerometter != null && sensorMagnetic != null){
            sensorManager.unregisterListener(sensorEventListener);

        }else{
            Toast.makeText(this, "Error! Sensor Accelerometer / Magnetic not found. ",Toast.LENGTH_LONG).show();
        }


    }
}


