package com.master.mipasapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.StringValue;
import com.master.mipasapp.R;
import com.master.mipasapp.model.SensorData;
import com.master.mipasapp.model.User;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SensorDataActivity extends Activity implements SensorEventListener{
    private TextView textViewSensorLight, textViewSensorTemp, textViewSensorProximity, textViewSensorPressure, textViewSensorHumidity;
    private SensorManager sensorManager;
    private List<Sensor> deviceSensors;
    private Sensor LightSensor, TempSensor, ProxSensor, PressureSensor, HumiditySensor;
    private FirebaseUser myCurrentuser;

    private DatabaseReference myReferenceDataBase, myFieldDataSensor;


    private SensorData SensorDataObject ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        textViewSensorLight = findViewById(R.id.textViewSensorLight);
        textViewSensorTemp = findViewById(R.id.textViewSensorTemp);
        textViewSensorProximity = findViewById(R.id.textViewSensorProximity);
        textViewSensorPressure = findViewById(R.id.textViewSensorPressure);
        textViewSensorHumidity = findViewById(R.id.textViewSensorHumidity);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // lista de sensores de las que dispone mi dispositivo
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        // sensor concreto de las que dispone mi dispositivo
        LightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        TempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        ProxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        PressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        HumiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        myReferenceDataBase = FirebaseDatabase.getInstance().getReference();
        myFieldDataSensor = myReferenceDataBase.child("DataSensors");

        Intent i=getIntent();
        myCurrentuser = (FirebaseUser) i.getSerializableExtra("CurrentFireBaseUser");
        SensorDataObject = new SensorData();

        signals();
    }

    private void signals() {

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            //Toast.makeText(this,deviceSensors.toString(),Toast.LENGTH_LONG).show();
            //textViewSensor.setText(deviceSensors.toString());
        } else {
            Toast.makeText(this,"hubo un error, asi que ten cuidado la proxima vez".toString(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float data_sensor = event.values[0];
        //SensorData(int lightSensor, int tempSensor, int proxSensor, int pressureSensor, int humiditySensor)

        if (event.sensor.getType() == Sensor.TYPE_LIGHT){
            textViewSensorLight.setText((String) ("LightSensor: " + data_sensor));
            SensorDataObject.setLightSensor(data_sensor);

        }else if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            textViewSensorTemp.setText((String) ("TempSensor: " + data_sensor));
            SensorDataObject.setTempSensor(data_sensor);

        }else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY ){
            textViewSensorProximity.setText((String) ("ProxSensor: " + data_sensor));
            SensorDataObject.setProxSensor(data_sensor);

        }else if(event.sensor.getType() == Sensor.TYPE_PRESSURE ){
            textViewSensorPressure.setText((String) ("PressureSensor: " + data_sensor));
            SensorDataObject.setPressureSensor(data_sensor);

        }else if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY ){
            textViewSensorHumidity.setText((String) ("HumiditySensor: " + data_sensor));
            SensorDataObject.setHumiditySensor(data_sensor);

        }
        //System.out.println();
        ///myCurrentuser.getUid();
        try {
            myFieldDataSensor.setValue(SensorDataObject);
            Log.i(TAG," Reference is: "+myReferenceDataBase.toString());
            System.out.println("*** " + myReferenceDataBase.toString() + " ***");
        }catch (Throwable e){
            System.out.println("*** " + e + " ***");
            System.out.println("*** " + myReferenceDataBase.toString() + " ***");
        }





    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Toast.makeText(this,"Sensor accuracy changes!!!",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, LightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, TempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, ProxSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, PressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, HumiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}