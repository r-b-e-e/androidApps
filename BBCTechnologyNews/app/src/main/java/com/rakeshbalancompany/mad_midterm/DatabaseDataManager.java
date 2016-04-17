package com.rakeshbalancompany.mad_midterm;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseDataManager {

    private Context context;
    private NewsDAO newsDAO;
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;

    public DatabaseDataManager(Context context) {
        this.context = context;
        databaseOpenHelper = new DatabaseOpenHelper(this.context);
        db = databaseOpenHelper.getWritableDatabase();
        newsDAO = new NewsDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public NewsDAO getNotesDAO() {
        return this.newsDAO;
    }


    public long saveNote(News news) {
        return getNotesDAO().save(news);
    }


//    public boolean updateNote(Note note){
//        return noteDao.update(note);
//    }


//    public Note getNote(long id){
//        return noteDao.get(id);
//    }


//    public boolean delete(Media note) {
//        return getNotesDAO().delete(note);
//    }
//
//
//
    public boolean mediaExists(String name){
        return getNotesDAO().mediaExists(name);
    }
//
//    public ArrayList<Media> getAll() {
//        return getNotesDAO().getAll();
//    }
}
