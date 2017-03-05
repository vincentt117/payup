package com.cuhacking.payup;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import static android.graphics.Color.WHITE;

public class DisplayTable extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_table);
        tableLayout = (TableLayout)findViewById(R.id.table_layout);
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

        roomButton.setText(roomName);

        tr.addView(roomView);
        tr.addView(roomButton);
        tl.addView(tr);

    }
}
