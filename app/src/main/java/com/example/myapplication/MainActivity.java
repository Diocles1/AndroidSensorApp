package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toCompass(View view){
        Intent intent = new Intent(this, Compass.class);
        startActivity(intent);
    }
    public void toAccMeter(View view){
        Intent intent = new Intent(this, AccMeter.class);
        startActivity(intent);

    }
}
