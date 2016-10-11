package com.example.etorunski.inclassexamples;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by etorunski on 2016-09-27.
 */

public class ListActivity extends Activity {

    private boolean recycleViews;
protected SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);


        MyDatabaseHelper dbHelper = new MyDatabaseHelper( this );
        db = dbHelper.getWritableDatabase();

        final ListView theList = (ListView) findViewById(R.id.thelist);
        final Button listButton = (Button)findViewById(R.id.listButton);
        recycleViews = false;

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViews = ! recycleViews;
                if(recycleViews){
                    ContentValues newValues = new ContentValues();
                    newValues.put("Price", 6);
                    newValues.put("Name", "Something");


                    db.insert(MyDatabaseHelper.TABLENAME, "MISSING DATA", newValues);

              Cursor results1 =      db.query(MyDatabaseHelper.TABLENAME, new String[] {"Price" },
                            "? < ?", new String[] {"Price", "7", "ignore", "ignore"},
                            null, null, null, null);
String columnNames [] =results1.getColumnNames(); // String[] { "Price" };

                    for(String colName : columnNames)
                    {
                        System.out.println("Name: " + colName);
                    }
theList.setAdapter(
        SimpleCursorAdapter (ListActivity.this, R.layout.inner_cell_layout,results1,
                new String[]{"Price", "Name"},
                new int[] {R.id.stringlocation, R.id.stringnames},0)


                    );
Cursor results2 = db.rawQuery("Select * from PRICES where Price < 7", null);
results2.getColumnNames(); // IDS, Price, Name

                    results1.moveToFirst();
                    int priceCOlumnIndex = results1.getColumnIndex("Price");
                    int nameCOlumnIndex = results1.getColumnIndex("Name");

                    while(!results1.isAfterLast())
                    {
                        String thisName = results1.getString(nameCOlumnIndex);
                        int thisPrice = results1.getInt(nameCOlumnIndex);
                    }


                    int numResults = results2.getCount(); //number of matches

                    listButton.setText( R.string.initialize_on );
                }
                else
                    listButton.setText( R.string.initialize_off );
            }
        });


        theList.setAdapter(new MyAdapter( this ));
        //Show a message at the "Debug" logging level
        Log.d("ListActivity", "In OnCreate()");

    }

    private class MyAdapter extends ArrayAdapter<String>
    {
        Context myContext;
        public MyAdapter(Context ctx)
        {
            super(ctx, 0);
            myContext = ctx;
        }

        public View getView(int position, View convertView, ViewGroup parent) {


            View inflated = convertView;

            if(inflated == null) {
                inflated = getLayoutInflater().inflate(R.layout.inner_cell_layout, null);
            }

            if(recycleViews) {
                TextView tv = (TextView) inflated.findViewById(R.id.stringlocation);
                String myMessage = getItem(position);
                tv.setText(myMessage);
            }

            return inflated;
        }

        public int getCount() {
            return 400;
        }

        public String getItem(int position){
            switch (position)
            {
                case 0:
                    return "zero";
                case 1:
                    return "one";
                case 2:
                    return "two";
                case 3:
                    return "Three";
            }
            return "Out of range" + position;
        }
    }


}
