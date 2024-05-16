package com.example.step_counter.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.example.step_counter.FragmentMain;
import com.example.step_counter.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private MainActivity context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private SharedPreferences sharedPreferences;

    public DBManager(MainActivity context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        sharedPreferences = context.getSharedPreferences("mypref", Context.MODE_PRIVATE);
    }
    public void openDB(){
        db = dbHelper.getWritableDatabase();
    }
    public void insertToDB(String date, int steps){
        ContentValues cv = new ContentValues();

        int num = db.delete(DBConstants.TABLE_NAME, "date=?", new String[]{date});
        Log.d("RRR", String.valueOf(num));

        cv.put(DBConstants.DATE, date);
        cv.put(DBConstants.STEPS, steps);
        db.insert(DBConstants.TABLE_NAME, null, cv);
    }
    @SuppressLint("Range")
    public int readLastFromDB(){
        Cursor cursor = db.query(
                DBConstants.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToLast();
        return cursor.getInt(cursor.getColumnIndex(DBConstants.STEPS));
    }
    @SuppressLint("Range")
    public List<Pair<String, Integer>> readFromDB(){
        List <Pair<String, Integer>> list = new ArrayList<>();
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
            Pair<String, Integer> pair = new Pair<>(
                    cursor.getString(cursor.getColumnIndex(DBConstants.DATE)),
                    cursor.getInt(cursor.getColumnIndex(DBConstants.STEPS)));
            list.add(pair);
        }
        cursor.close();
        return list;
    }
    public void setTarget(int target){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("target", target);
        editor.apply();
    }
    public int getTarget(){
        return sharedPreferences.getInt("target", -1);
    }
    public void clearDB(){
        db.execSQL("delete from " + DBConstants.TABLE_NAME);
    }
    public void closeDB(){
        dbHelper.close();
    }
}
