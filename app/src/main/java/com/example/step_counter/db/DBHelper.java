package com.example.step_counter.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.step_counter.FragmentMain;

import java.util.Objects;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable FragmentMain context) {
        super(Objects.requireNonNull(context).getContext(), DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.TABLE_STRUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBConstants.DROP_TABLE);
        onCreate(db);
    }
}
