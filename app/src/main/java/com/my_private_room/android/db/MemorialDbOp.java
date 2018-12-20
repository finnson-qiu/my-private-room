package com.my_private_room.android.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.my_private_room.android.util.MemorialDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemorialDbOp {
    private MemorialDay memorialDay;
    private MyDatabaseHelper dbHelper;

    public MemorialDbOp(){
    }
    public MemorialDbOp(MemorialDay memorialDay,MyDatabaseHelper dbHelper){
        this.memorialDay=memorialDay;
        this.dbHelper=dbHelper;
    }

    public MemorialDbOp(MyDatabaseHelper dbHelper){
        this.dbHelper=dbHelper;
    }

    public void MemorialInsert(){           //Memorialday表数据插入
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",memorialDay.getName());
        values.put("date",memorialDay.getDate());
        String pastdate=memorialDay.getDate();
        long pTndays=charge(pastdate);
        values.put("days",pTndays);
        db.insert("Memorialday",null,values);
    }
    private long charge(String pastdate){            //计算记录时间和当前时间差的天数
        long charge=0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
              try {
            Date d1=sdf.parse(pastdate);
            Date curDate = new Date(System.currentTimeMillis());
            charge=(curDate.getTime()-d1.getTime())/(24*60*60*1000);
         } catch (ParseException e) {
                 e.printStackTrace();
      }
       return charge;
    }
    public Cursor MemorialRetrieve(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("Memorialday",null,null,null,null,null,null);
        return cursor;
    }

    public void DeleteAMemorial(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String str[]=new String[1];
        str[0]="";
        str[0]=id+"";
        db.delete("Memorialday","id =?",str);
    }
}
