package com.rakeshbalancompany.mad_midterm;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NewsDAO {

    private SQLiteDatabase sqLiteDatabase;

    public NewsDAO(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public long save(News media) {
        ContentValues values = new ContentValues();
        values.put(NewsTable.COLUMN_TITLE, media.getTitle());
        values.put(NewsTable.COLUMN_PUBDATE, media.getPub_date());
        values.put(NewsTable.COLUMN_IMGLINK,media.getThumb_image());
        values.put(NewsTable.COLUMN_NEWSLINK,media.getNews_link());
        return sqLiteDatabase.insert(NewsTable.TABLE_NAME, null, values);
    }


//    public boolean delete(Media media) {
//        return sqLiteDatabase.delete(NewsTable.TABLE_NAME, NewsTable.COLUMN_APPNAME + "=?", new String[]{media.getApp_name() + ""}) > 0;
//    }
//
    public boolean mediaExists(String name) {
        News media = null;
        Cursor cursor = sqLiteDatabase.query(true, NewsTable.TABLE_NAME,
                new String[]{NewsTable.COLUMN_TITLE},
                NewsTable.COLUMN_TITLE + "=?", new String[]{name + ""}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            media = buildNotesFromCursor1(cursor);
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        if(media!=null){
            return true;
        }
        else{
            return false;
        }

    }
//
//
//    public ArrayList<Media> getAll() {
//
//        ArrayList<Media> notesList = new ArrayList<>();
//
//        Cursor cursor = sqLiteDatabase.query(NewsTable.TABLE_NAME,
//                new String[]{NewsTable.COLUMN_APPNAME, NewsTable.COLUMN_DEVNAME, NewsTable.COLUMN_RELEASE, NewsTable.COLUMN_CATEGORY,
//                NewsTable.COLUMN_PRICE, NewsTable.COLUMN_URL}
//                , null, null, null, null, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//
//            do {
//                Media note = buildNotesFromCursor(cursor);
//                if (note != null) {
//                    notesList.add(note);
//                }
//            } while (cursor.moveToNext());
//        }
//        if (!cursor.isClosed()) {
//            cursor.close();
//        }
//        return notesList;
//    }
//
//    private Media buildNotesFromCursor(Cursor c) {
//        Media note = new Media();
//        if (c != null) {
//
//            note.setApp_name(c.getString(0));
//            note.setDev_name(c.getString(1));
//            note.setRelease(c.getString(2));
//            note.setCategory(c.getString(3));
//            note.setPrice(c.getString(4));
//            note.setUrl(c.getString(5));
//        }
//        return note;
//
//    }


//    public boolean update(Note note){
//        ContentValues values = new ContentValues();
//        values.put(NotesTable.Note_SUBJECT, note.getSubject());
//        values.put(NotesTable.Note_TEXT, note.getText());
//        return db.update(NotesTable.TABLE_NAME, values, NotesTable.NOTE_ID+"="+ note.get_id(), null) > 0;
//    }
//
//    public Note get(long id){
//        Note note = null;
//        Cursor c = db.query(true, NotesTable.TABLE_NAME,
//                new String[]{NotesTable.NOTE_ID, NotesTable.Note_SUBJECT, NotesTable.Note_TEXT},
//                NotesTable.NOTE_ID+"="+ id, null, null, null, null, null);
//        if(c != null){
//            c.moveToFirst();
//            note = this.buildNoteFromCursor(c);
//        }
//        if(!c.isClosed()){
//            c.close();
//        }
//        return note;
//    }


    private News buildNotesFromCursor1(Cursor c) {
        News note = new News();
        if (c != null) {

            note.setTitle(c.getString(0));

        }
        return note;

    }


}
