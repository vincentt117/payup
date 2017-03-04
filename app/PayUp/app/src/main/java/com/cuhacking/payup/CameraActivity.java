package com.cuhacking.payup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CameraActivity extends AppCompatActivity {
    // Assume thisActivity is the current activity
    int permissionCheck = ContextCompat.checkSelfPermission(CameraActivity.this,
            Manifest.permission.WRITE_CALENDAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }
}
