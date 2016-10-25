package com.example.etorunski.inclassexamples;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by etorunski on 2016-10-11.
 */

public class CursorListActivity extends Activity {



    protected SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);


        MyDatabaseHelper dbHelper = new MyDatabaseHelper( this );
        db = dbHelper.getWritableDatabase();

        final ListView theList = (ListView) findViewById(R.id.thelist);
        final Button listButton = (Button)findViewById(R.id.listButton);

        listButton.setText("Add data to database");
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues newValues = new ContentValues();
                newValues.put("PRICE", (int)(Math.random()*40));
                newValues.put("NAME", "Something");


                db.insert(MyDatabaseHelper.TABLENAME, "MISSING DATA", newValues);


                //Only want results where price is less than 30, for example:
                Cursor results1 = db.query(MyDatabaseHelper.TABLENAME, new String[]{"_id", "PRICE", "NAME"},
                        "PRICE < ?", new String[] {" 40" },
                        null, null, null, null);


                //Same query but as rawQuery:
                Cursor results2=   db.rawQuery("select _id, PRICE, NAME from PRICES where PRICE < ?", new String[] { "30"});

                String columnNames[] = results1.getColumnNames();

                for (String colName : columnNames) {
                    System.out.println("Name: " + colName);
                }


                theList.setAdapter(new SimpleCursorAdapter(CursorListActivity.this, R.layout.cursor_cell_layout, results1,
                                new String[]{"PRICE", "NAME"},
                                new int[]{R.id.stringlocation, R.id.stringnames}, 0));



                results1.moveToFirst();
                int priceColumnIndex = results1.getColumnIndex("PRICE");
                int nameColumnIndex = results1.getColumnIndex("NAME");


                while (!results1.isAfterLast()) {
                    String thisName = results1.getString(nameColumnIndex);
                    int thisPrice = results1.getInt(nameColumnIndex);
                    System.out.println(thisName + " " + thisPrice);

                    results1.moveToNext(); //move the cursor to the next row
                }
                 results2 = db.rawQuery("select PRICE, NAME from PRICES where _id > ?", new String[] { "40"});

                results2.getColumnNames(); // IDS, Price, Name

                int numResults = results2.getCount(); //number of matching rows

            }



        });

        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.delete(MyDatabaseHelper.TABLENAME, "_id = ? ", new String[] { Long.toString(id)});
                Toast.makeText(CursorListActivity.this, "Deleted position:" + position + " id:" + id, Toast.LENGTH_SHORT).show();


                //Now that the database has changed, need to reload the query:
                Cursor results1 = db.query(MyDatabaseHelper.TABLENAME, new String[]{"_id", "PRICE", "NAME"},
                        null, null,
                        null, null, null, null);
                theList.setAdapter(new SimpleCursorAdapter(CursorListActivity.this, R.layout.cursor_cell_layout, results1,
                                new String[]{"PRICE", "NAME"},
                                new int[]{R.id.stringlocation, R.id.stringnames}, 0));

            }
        });
        //Show a message at the "Debug" logging level
        Log.d("ListActivity","In OnCreate()");
    }

}
