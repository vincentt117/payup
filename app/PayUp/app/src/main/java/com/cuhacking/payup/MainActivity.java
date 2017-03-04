package com.cuhacking.payup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText cameraText = (EditText)findViewById(R.id.cameraText);
        Button camera = (Button)findViewById(R.id.cameraButton);
    }

    public void useCamera(View v){
        EditText ct = (EditText)findViewById(R.id.cameraText);
        ct.setText("Vincent is a front-end trash");

    }
}
