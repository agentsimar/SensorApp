package com.example.akal.sensorapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MySensorActivity extends AppCompatActivity implements SensorEventListener {
    @InjectView(R.id.textViewSensor)
    TextView txtsensordata;
    @InjectView(R.id.buttonActivate)
    Button btnActivate;
    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sensor);
        ButterKnife.inject(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        btnActivate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sensorManager.registerListener(MySensorActivity.this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
                    }
                }

        );
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float cal = (x*x)+(y*y)+(z*z) / (SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);

        if(cal>3){
            txtsensordata.setText("shake detected");
            String phone = "+91 95697 94546";
            String msg = "Device shake done!";
            SmsManager smsManager =SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,msg,null,null);
            sensorManager.unregisterListener(this);
        }
        else{
            txtsensordata.setText(x+"-"+y+"-"+z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
