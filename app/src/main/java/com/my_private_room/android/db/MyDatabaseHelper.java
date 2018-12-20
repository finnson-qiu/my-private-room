package com.my_private_room.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
   public static final String Create_Diary="create table Diary("
           +"id integer primary key autoincrement,"
           +"name text,"
           +"content text,"
           +"date text)";
   public static final String Create_Memorialday="create table Memorialday("
           +"id integer primary key autoincrement,"
           +"name text,"
           +"date text,"
           +"days integer)";

   private Context mContext;

    public MyDatabaseHelper(Context context,String name,
                            SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, null, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Create_Diary);
        db.execSQL(Create_Memorialday);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Diary");
        db.execSQL("drop table if exists Memorialday");
        onCreate(db);
    }
}
