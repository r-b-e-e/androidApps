package com.rakeshbalancompany.mad_midterm;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NewsTable {

    static final String TABLE_NAME="NEWSFEED";
    static final String COLUMN_TITLE="title";
    static final String COLUMN_PUBDATE="pubdate";
    static final String COLUMN_IMGLINK="imagelink";
    static final String COLUMN_NEWSLINK="newslink";


    public static void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME +" (");
        sb.append(COLUMN_TITLE + " text not null, ");
        sb.append(COLUMN_PUBDATE + " text not null, ");
        sb.append(COLUMN_IMGLINK + " text not null, ");
        sb.append(COLUMN_NEWSLINK + " text not null);");

        try {
            db.execSQL(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        NewsTable.onCreate(db);
    }
}
