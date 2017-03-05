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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SendTo extends AppCompatActivity {
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to);
        // Find the last picture
        String[] projection = new String[]{
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
            //final ImageView imageView = (ImageView) findViewById(R.id.pictureView);
            String imageLocation = cursor.getString(1);
            File imageFile = new File(imageLocation);
            if (imageFile.exists()) {   // TODO: is there a better way to do this?
                Bitmap bm = BitmapFactory.decodeFile(imageLocation);
                //imageView.setImageBitmap(bm);
            }
        }

        tableLayout = (TableLayout)findViewById(R.id.send_to_table_layout);
        ArrayList<String> roomList = new ArrayList<String>(Arrays.asList("A Room", "B Room", "C Room"));
        // roomList must contain elements to run
        for (int i = 0; i < roomList.size(); i++) {
            addRow(tableLayout, this, roomList.get(i));
        }






    }



    public void addRow(TableLayout tl, Context context, String roomName) {
        TableRow tr = new TableRow(context);
        TextView roomView = new TextView(context);
        Button roomButton = new Button(context);

        roomView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f));
        roomView.setText(roomName);

        roomButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f ));

        roomButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            }
        });
        roomButton.setText("Send");
        tr.addView(roomView);
        tr.addView(roomButton);
        tl.addView(tr);
    }
    public void joinRoom(View v){
        new Operation().execute("joinRoom");
    }
    public void createRoom(View v){
        new Operation().execute("createRoom");
    }



    private class Operation extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            String s = "";
            for(int i =  0 ; i < strings.length ; i++){
                if(strings[0].equals("joinRoom")){

                }else if(strings[0].equals("createRoom")){
                    s = middleProcess.createRoom(SelectActivity.username);
                }
            }
            Log.d("String Params:", strings[0]);

//            String s = middleProcess.createRoom("Username1");
//            String g = middleProcess.getRooms("Username1").toString();
//            Log.d("Create Room: ", s);
//            Log.d("Get Room: ", g);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OnPostExecute: ", s);

            addRow(tableLayout, SendTo.this, s);

        }
    }
    public void join(View v){

        Intent intent = new Intent(this, DisplayTable.class);
        startActivity(intent);
    }
}
