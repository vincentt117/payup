package com.cuhacking.payup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TakeReceiptPic extends AppCompatActivity {
    Button accept;
    Button retry;
    static final int REQUEST_IMAGE_CAPTURE = 0;
    protected void onCreate(Bundle savedInstanceState) {
        boolean dataInsuff = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_receipt_pic);
        accept = (Button) findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SendTo.class);
                startActivity(intent);
            }
        });


        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent refershed = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(refershed, REQUEST_IMAGE_CAPTURE);
            }
        });

        //send file to server to process image
        new Operation().execute();

        }// end

    public void retry(View v) {
        Intent refreshed = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(refreshed, REQUEST_IMAGE_CAPTURE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent(this, TakeReceiptPic.class);
        startActivity(intent);
    }

    private File getLastPhoto(){
        // Find the last picture  
        String[] projection = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        final Cursor cursor = getApplicationContext().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
        // Put it in the image view 
        if (cursor.moveToFirst()) {
            final ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            String imageLocation = cursor.getString(1);
            File imageFile = new File(imageLocation);

            return imageFile;
//            if (imageFile.exists()) {   // TODO: is there a better way to do this? 
//                Bitmap bm = BitmapFactory.decodeFile(imageLocation);
//                imageView.setImageBitmap(bm);
//            }
        }
        Log.d("attempt", "yes");
        return null;
    }


    private class Operation extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            Log.d("doInBackground", "Running");
            ArrayList<GenericTwo> gt = middleProcess.sendFile(getLastPhoto(), SelectActivity.username);
            String s = "";
            for(int i = 0 ; i < gt.size() ; i++){
                s = s + gt.get(i).getName() + "," + gt.get(i).getMoney();
            }
            Log.d("doInBackground", "Finished" + s);
            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("OnPostExecute: ", s);
        }
    }
    public void join(View v){
        Intent intent = new Intent(this, DisplayTable.class);
        startActivity(intent);
    }

}


