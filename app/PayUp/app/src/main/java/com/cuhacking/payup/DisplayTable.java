package com.cuhacking.payup;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static android.graphics.Color.WHITE;

public class DisplayTable extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_table);
        tableLayout = (TableLayout)findViewById(R.id.table_layout);
        addRow(tableLayout, this, "Cheese Burger", "$14");
        addRow(tableLayout, this, "Cheese Burger", "$14");
        addRow(tableLayout, this, "Cheese Burger", "$14");

    }

    public void addRow(TableLayout tl, Context context, String prod, String price){
        TableRow tr = new TableRow(context);
        tr.setLayoutParams(tl.getLayoutParams());
        TextView productView = new TextView(context);
        TextView priceView = new TextView(context);

        productView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f ));
        productView.setText(prod);

        priceView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f ));
        priceView.setText(price);
        tr.addView(productView);
        tr.addView(priceView);
        tr.setClickable(true);

        final PopupWindow pw = new PopupWindow(context);
        TextView info = new TextView(context);
        TableLayout popupTable = new TableLayout(context);
        TableRow popupTableRow = new TableRow(context);
        info.setText("Testing..asdfasdfasdfasdfasdfasdf.");
        info.setBackgroundColor(WHITE);
        popupTableRow.addView(info);
        pw.setContentView(popupTableRow);

        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pw.isShowing()){
                    pw.dismiss();
                }else{
                    pw.showAsDropDown(view);
                }
            }
        });


        tl.addView(tr);
    }
    public PopupMenu makePopupMenu(Context context, View anchor){
        PopupMenu pm = new PopupMenu(context, anchor);

        return pm;
    }
}
