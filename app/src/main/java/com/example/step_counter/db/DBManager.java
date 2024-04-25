package com.example.step_counter.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }
    public void openDB(){
        db = dbHelper.getWritableDatabase();
    }
    public void insertToDB(String date, int steps){
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.DATE, date);
        cv.put(DBConstants.STEPS, steps);
        db.insert(DBConstants.TABLE_NAME, null, cv);
    }
    @SuppressLint("Range")
    public List<String> readFromDB(){
        List <String> list = new ArrayList<>();
        Cursor cursor = db.query(
                DBConstants.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
                );
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(DBConstants.DATE)));
        }
        cursor.close();
        return list;
    }
    public void closeDB(){
        dbHelper.close();
    }
}
