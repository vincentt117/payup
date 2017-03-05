package com.cuhacking.payup;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewBill extends AppCompatActivity {
    TextView textView;
    private ListView mListView;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        tableLayout = (TableLayout)findViewById(R.id.view_bill_table_layout);
        textView = (TextView)findViewById(R.id.textView);
        new Operation(this).execute();
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
                   // middleProcess.sendOk(getLastPhoto(),SelectActivity.username, strings[1]);
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
                new Operation(context).execute("getInformation", roomName);

            }
        });
        roomButton.setText("Send");
        tr.addView(roomView);
        tr.addView(roomButton);
        tl.addView(tr);
    }
    public void join(View v){
        Intent intent = new Intent(this, DisplayTable.class);
        startActivity(intent);
    }
}
