package com.example.journeyjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME =  "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(email TEXT primary key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");

    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result==-1) return false;
        else
            return true;
    }

    public Boolean updatepassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Password", password);
        long result = MyDB.update("users", contentValues, "email = ?", new String[] {email});
        if (result==-1) return false;
        else
            return true;
    }

    public Boolean checkemail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[]{email});
                if (cursor.getCount()>0)
                    return true;
                else
                    return false;
    }

    public Boolean checkemailpassword ( String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where Email = ? and password = ?",new String[] {email,password} );
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }






}
