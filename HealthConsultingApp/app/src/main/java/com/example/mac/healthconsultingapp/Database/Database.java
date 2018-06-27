package com.example.mac.healthconsultingapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "SmartDevice.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // create database
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // table create
        String sql = "create table user(_id INTEGER PRIMARY KEY autoincrement, username text not null, fullname text not null, password text not null)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS user";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);


    }
}
