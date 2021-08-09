package com.example.cuetintra_examquestion.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtil extends SQLiteOpenHelper {
    List<Pair<String, String>> PathList = new ArrayList<>();
    public static final String DATABASE_NAME = "Bookmark.db";
    public static final String TABLE_NAME = "Bookmark_table";
    public static final String COL_1 = "TITLE";
    public static final String COL_2 = "PATH";
    public SQLiteUtil(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT, PATH TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String title, String path){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,title);
        contentValues.put(COL_2, path);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else return true;
    }
    public boolean deleteData(String path){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "DELETE FROM Favourite_table WHERE PATH = '"+path+"'";
        Integer numrows = db.delete(TABLE_NAME, "PATH = '"+path+"'", null);
        if(numrows>0)
            return true;
        else
            return false;
    }
    public boolean getData(String path){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Bookmark_table WHERE PATH = '"+path+"'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public List<Pair<String, String>> getPathList(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor != null){
            while (cursor.isAfterLast() == false){
                PathList.add(new Pair<>(cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
        }
        return PathList;
    }
}
