package com.example.journeyjournal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBmain extends SQLiteOpenHelper {
    public static final String DBNAME = "journals.db";
    public static final String TABLENAME = "My_Journals";
    public static final int VER = 1;

    public DBmain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //-----------------------
        String query = "create table " + TABLENAME + "(id integer primary key, avatar blob, " +
                "name text, description text, date text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists " + TABLENAME + "";
        db.execSQL(query);
    }
}