package com.example.myapplication;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class AccMeter extends AppCompatActivity implements SensorEventListener {
    private ImageView box_img;
    private TextView txt_x_cord;
    private TextView txt_y_cord;
    private TextView txt_z_cord;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mRotationV;
    private boolean haveSensor = false;
    private boolean haveSensor2 = false;
    private float x;
    private float y;
    private float z;
    public static final int COIN = R.raw.smb3_coin;
    public static final int FIREBALL = R.raw.smb3_fireball;

    private MediaPlayer mpCoin;
    private MediaPlayer mpFire;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_meter);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        txt_x_cord = (TextView) findViewById(R.id.x_text);
        txt_y_cord = (TextView) findViewById(R.id.y_text);
        txt_z_cord = (TextView) findViewById(R.id.z_text);
        mpCoin = MediaPlayer.create(this,COIN );
        mpFire = MediaPlayer.create(this,FIREBALL );
        MediaPlayer.create(this, R.raw.smb3_powerup).start();


        start();
    }

    private void start() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
            if ((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) || (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null)) {
                noSensorsAlert();
            }
            else {
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                haveSensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
            }
        }
        else{
            mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            haveSensor = mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private void noSensorsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Compass.")
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER && x<10 && y<15) {
            return;
        }
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        if(x > 10){
            mpFire.start();
        }

        if(y > 15){
            mpCoin.start();

        }

        txt_x_cord.setText("X: " + x);
        txt_y_cord.setText("Y: " + y);
        txt_z_cord.setText("Z: " + z);

    }
}


