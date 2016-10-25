package com.example.etorunski.inclassexamples;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by etorunski on 2016-10-11.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    static final protected String DATABASE_NAME = "ADatabase.db";
    static final int VERSION_NUM = 1;
    static final public String TABLENAME = "PRICES";

    public MyDatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLENAME +
                " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, NAME text, PRICE INTEGER);"  );

    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        this.onCreate(db);
    }

    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        Log.d("OnOpen", "onOpen: hi");
    }


    public void onDowngrade(SQLiteDatabase db, int oldver, int newVer)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        this.onCreate(db);
    }
}
