package com.cuhacking.payup;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
        addRow(tableLayout, this, "Cheese Burger", "$14");
        addRow(tableLayout, this, "Cheese Burger", "$14");
        addRow(tableLayout, this, "Cheese Burger", "$14");
        addRow(tableLayout, this, "Cheese Burger", "$14");
        addRow(tableLayout, this, "Cheese Burger", "$14");
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
        tl.addView(tr);
    }
}
