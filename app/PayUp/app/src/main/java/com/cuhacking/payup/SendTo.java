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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class SendTo extends AppCompatActivity {
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to);
        tableLayout = (TableLayout)findViewById(R.id.send_to_table_layout);
        new Operation(this).execute("getRoom");
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
            //final ImageView imageView = (ImageView) findViewById(R.id.imageView1);
            String imageLocation = cursor.getString(1);
            File imageFile = new File(imageLocation);

            OutputStream outStream = null;
            //return imageFile;
            if (imageFile.exists()) {   // TODO: is there a better way to do this? 

                //bm = Bitmap.createScaledBitmap(bm, bm.getWidth()/50, bm.getHeight()/50, true);
                try{
                    Bitmap bm = BitmapFactory.decodeFile(imageLocation);
                    outStream = new FileOutputStream(imageFile);
                    bm.compress(Bitmap.CompressFormat.JPEG, 65, outStream);
                    outStream.flush();
                    outStream.close();
                }catch(Exception e){

                }
                //imageView.setImageBitmap(bm);
                return imageFile;
            }
        }
        Log.d("attempt", "yes");
        return null;
    }


    public void addRow(TableLayout tl, final Context context, final String roomName) {
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
                //middleProcess.update(SelectActivity.username, roomName);
                new Operation(context).execute("send", roomName);
                finish();
            }
        });
        roomButton.setText("Send");
        tr.addView(roomView);
        tr.addView(roomButton);
        tl.addView(tr);
    }
    public void joinRoom(View v){
        EditText roomName = (EditText)findViewById(R.id.room_name);
        new Operation(SendTo.this).execute("joinRoom", roomName.getText().toString());
    }
    public void createRoom(View v){
        new Operation(SendTo.this).execute("createRoom");
    }



    private class Operation extends AsyncTask<String, String, String> {

        private Context mContext;

        public Operation(Context context){
            mContext = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            String s = "getRoom,";
            if(strings.length != 0){
                if(strings[0].equals("joinRoom")){
                    s = middleProcess.joinRoom(strings[1], SelectActivity.username);
                }else if(strings[0].equals("createRoom")){
                    s = middleProcess.createRoom(SelectActivity.username);
                }else if(strings[0].equals("getRoom")){
                    ArrayList<String> roomNames = middleProcess.getRooms(SelectActivity.username);
                    // roomList must contain elements to run
                    for (int i = 0; i < roomNames.size(); i++) {
                       // addRow(tableLayout, mContext, roomNames.get(i));
                        s = s + roomNames.get(i) + ",";
                    }
                    return s;
                }else{
                    middleProcess.sendOk(getLastPhoto(),SelectActivity.username, strings[1]);
                }
            }


            Log.d("String Params:", strings[0]);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OnPostExecute: ", s);
            String[] parsed = s.split(",");
            if(parsed[0].equals("getRoom")){
                for (int i = 1; i < parsed.length; i++) {
                    addRow(tableLayout, mContext, parsed[i]);
                }
            }else{

            }
        }
    }
    public void join(View v){
        Intent intent = new Intent(this, DisplayTable.class);
        startActivity(intent);
    }



}
