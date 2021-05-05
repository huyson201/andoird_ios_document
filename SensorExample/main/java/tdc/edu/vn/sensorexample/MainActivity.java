package tdc.edu.vn.sensorexample;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor = null;
    private EditText editText;
    private ListView listView;
    private ArrayList<String> sensorList;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] sensorDatas = event.values;
            float x = sensorDatas[0];
            float y = sensorDatas[1];
            float z = sensorDatas[2];

            float value = (x*x + y*y + z*z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            boolean activeAdmin = policyManager.isAdminActive(componentName);

            if(activeAdmin){
                if(value > 2){
                    policyManager.lockNow();
                }
            }else {
                Toast.makeText(MainActivity.this, "Admin isn't Active Permission", Toast.LENGTH_LONG).show();
            }

            editText.setText(value + "");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // nothing to do now
        }
    };

    // admin permission
    private DevicePolicyManager policyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // police permission
        policyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this,Admin.class );
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        startActivity(intent);

        // init
        editText = findViewById(R.id.edtText);
        listView = findViewById(R.id.list_view);
        // init sensor

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // init list view data source
        sensorList  = new ArrayList<String>();

        // add data to sensorList

        for(Sensor sensor : sensors){
            sensorList.add(sensor.getName() + ": " + sensor.getVendor());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sensorList);
        listView.setAdapter(arrayAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensor != null){
            sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(this,"Your devices not supported this sensor",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onStop() {
        if(sensor != null){
            sensorManager.unregisterListener(sensorEventListener, sensor);
        }else{
            Toast.makeText(this,"Your devices not supported this sensor",Toast.LENGTH_LONG).show();
        }

        super.onStop();

    }
}

