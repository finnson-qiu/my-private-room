package com.my_private_room.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.my_private_room.android.util.Diary;

public class DiaryDbOp {
    private Diary diary;
    private MyDatabaseHelper dbHelper;
    private int n;

    public DiaryDbOp(){

    }
    public DiaryDbOp(Diary diary,MyDatabaseHelper dpHelper){
        this.diary=diary;
        this.dbHelper=dpHelper;
    }
    public DiaryDbOp(MyDatabaseHelper dbHelper){
        this.dbHelper=dbHelper;
    }
    public void DiaryInsert(){
        SQLiteDatabase db=this.dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",diary.getName());
        values.put("content",diary.getContent());
        values.put("date",diary.getDate());
        db.insert("Diary",null,values);
    }
    public Cursor DiaryRetrieve(){
        SQLiteDatabase db=this.dbHelper.getWritableDatabase();
        Cursor cursor=db.query("Diary",null,null,null,null,null,null);
        return cursor;
    }
    public void DelectADiary(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String str[]=new String[1];
        str[0]="";
        str[0]=id+"";
        db.delete("Diary","id =?",str);
    }

    public void  ChangeDiary(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("content",diary.getContent());
        values.put("name",diary.getName());
        String str[]=new String[1];
        str[0]=""+diary.getId();
        db.update("Diary",values,"id=?",str);
    }
}
