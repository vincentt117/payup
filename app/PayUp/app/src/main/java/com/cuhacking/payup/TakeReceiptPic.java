package com.cuhacking.payup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
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

public class TakeReceiptPic extends AppCompatActivity {
    TableLayout tableLayout;
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
        tableLayout = (TableLayout)findViewById(R.id.take_receipt_table);

        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent refershed = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(refershed, REQUEST_IMAGE_CAPTURE);
            }
        });

        //send file to server to process image
        new Operation(this).execute();

        }// end

    public void retry(View v) {
        Intent refreshed = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(refreshed, REQUEST_IMAGE_CAPTURE);
    }

    public void accept(View v){
        Intent intent = new Intent(this, SendTo.class);
        startActivity(intent);
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



    private class Operation extends AsyncTask<String, String, String>{
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
            Log.d("doInBackground", "Running");
            ArrayList<GenericTwo> gt = middleProcess.sendFile(getLastPhoto(), SelectActivity.username);
            String s = "";
            for(int i = 0 ; i < gt.size() ; i++){
                s = s + gt.get(i).getName() + "," + gt.get(i).getMoney() + ",";
            }
            Log.d("doInBackground", "Finished" + s);
            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String[] receipt = s.split(",");
            Log.d("OnPostExecute: ", s);
            for(int i = 0 ; i < receipt.length ; i+=2){
                if(i+1 < receipt.length){
                    addRow(tableLayout, mContext, receipt[i], receipt[i+1]);
                    Log.d("ADD ROW", "added" + receipt[i] + "  " + receipt[i+1]);
                    Log.d("ADD ROW 2", " " + i);
                }
            }
        }
    }
    public void join(View v){
//        Intent intent = new Intent(this, DisplayTable.class);
//        startActivity(intent);
    }



    public void addRow(TableLayout tl, Context context, String name, String price) {
        TableRow tr = new TableRow(context);
        TextView textName = new TextView(context);
        TextView textPrice = new TextView(context);

        textName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textName.setText(name);

        textPrice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textPrice.setText(price);

        tr.addView(textName);
        tr.addView(textPrice);
        tl.addView(tr);

    }

}


