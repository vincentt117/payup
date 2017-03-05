package com.cuhacking.payup;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewBill extends AppCompatActivity {
    TextView textView;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        //mListView = (ListView) findViewById(R.id.view_bill_list);
//// 1
//        ArrayList<String> list = new ArrayList<String>();
//// 2
//        String[] listItems = new String[3];
//// 3
//        list.add("First");
//        listItems[0]="Another";
//// 4
//        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_view_bill, listItems);
//        ArrayAdapter adapter = new ArrayAdapter();
    //        mListView.setAdapter(adapter);

        textView = (TextView)findViewById(R.id.textView);
        new Operation().execute();
    }


    private class Operation extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected String doInBackground(String... strings) {
            String s = middleProcess.createRoom("Username1");
            String g = middleProcess.getRooms("Username1").toString();
            Log.d("Create Room: ", s);
            Log.d("Get Room: ", g);
            return s + " " + g;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OnPostExecute: ", s);
            textView.setText("Executed");

        }
    }
    public void join(View v){

        Intent intent = new Intent(this, DisplayTable.class);
        startActivity(intent);
    }
}
