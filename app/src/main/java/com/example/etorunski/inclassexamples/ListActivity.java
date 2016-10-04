package com.example.etorunski.inclassexamples;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

    ListView theList = (ListView) findViewById(R.id.thelist);
    String[] arr = {"Sting 1", "String 2", "String 3"};

   // ArrayAdapter<String> myList = new ArrayAdapter<String>(this, R.layout.list_layout, arr);

    theList.setAdapter(new MyAdapter( this ));
    //Show a message at the "Debug" logging level
    Log.d("ListActivity", "IN OnCreate()");

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


            View inflated =null;
            if(convertView == null) {
                inflated = getLayoutInflater().inflate(R.layout.inner_cell_layout, null);

            }
            else {
                inflated = convertView;
            }

            TextView tv = (TextView)inflated.findViewById(R.id.stringlocation);
            String myMessage = getItem( position );
            tv.setText( myMessage);

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
