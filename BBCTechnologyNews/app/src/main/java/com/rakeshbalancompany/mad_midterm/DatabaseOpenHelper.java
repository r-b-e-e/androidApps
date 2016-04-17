package com.rakeshbalancompany.mad_midterm;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "NEWSFEED.db";
    static final int VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("demo", "open");
        NewsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        NewsTable.onUpgrade(db, oldVersion, newVersion);
    }
}
