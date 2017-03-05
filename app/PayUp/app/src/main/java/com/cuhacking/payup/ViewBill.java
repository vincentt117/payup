package com.cuhacking.payup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewBill extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        mListView = (ListView) findViewById(R.id.view_bill_list);
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
    }


    public void join(View v){

        Intent intent = new Intent(this, DisplayTable.class);
        startActivity(intent);
    }
}
