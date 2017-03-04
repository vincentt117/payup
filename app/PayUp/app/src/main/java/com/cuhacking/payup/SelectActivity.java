package com.cuhacking.payup;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
       // Button takePicture = (Button)findViewById(R.id.take_receipt_picture);
       // Button viewBill = (Button)findViewById(R.id.view_bill);
    }


    public void takePicture(View v){
        //TODO go to vincent
        Log.d("TAG", "takePicture: Clicked");
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);

    }
    public void viewBill(View v){
        Log.d("TAG", "viewBill: Clicked");
        Intent intent = new Intent(this, ViewBill.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
