package com.my_private_room.android;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.my_private_room.android.Adapter.DiaryAdapter;
import com.my_private_room.android.db.DiaryDbOp;
import com.my_private_room.android.db.MyDatabaseHelper;
import com.my_private_room.android.util.Diary;
import com.my_private_room.android.util.DiaryItem;

import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {
    private Diary diary;
    private List<Diary>diaryItemList=new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        this.GetViewList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {          //点击listview选择进入一个diarycontent活动
                Diary diary=diaryItemList.get(position);
                Intent intent=new Intent(DiaryActivity.this,DiaryContent.class);
                intent.putExtra("diary_name",diary.getName());
                intent.putExtra("diary_content",diary.getContent());
                intent.putExtra("diary_date",diary.getDate());
                intent.putExtra("diary_id",diary.getId());
                startActivityForResult(intent,2);
            }
        });
        ImageView image=(ImageView)findViewById(R.id.spaceAdd);             //选择发表
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiaryActivity.this,PublishActivity.class);
                startActivityForResult(intent,1);
            }
        });
        ImageView image1=(ImageView)findViewById(R.id.back);                //选择返回
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){       //重写函数使活动从发表活动回到这个活动时可以重新把数据库里的东西拿出来
        switch(requestCode){
            case 1:
                diaryItemList=new ArrayList<>();
                if(resultCode==RESULT_OK){
                    this.GetViewList();
                }
                break;
            case 2:
                diaryItemList=new ArrayList<>();
                if(resultCode==RESULT_OK){
                    this.GetViewList();
                }
                break;
            default:

        }
    }

    public void GetViewList(){          //读数据库里面的东西并且把date和name显示到listview上
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(this,"PrivateRoom.db",null,3);
        DiaryDbOp dp=new DiaryDbOp(diary,dbHelper);
        Cursor cursor=dp.DiaryRetrieve();   //调用日记数据库处理类获得数据
        if(cursor.moveToFirst()){           //把数据库的数据存到字符串数组
            do{
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String content=cursor.getString(cursor.getColumnIndex("content"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String name=cursor.getString(cursor.getColumnIndex("name"));
                diary=new Diary(id,name,content,date);
                diaryItemList.add(diary);
            }while(cursor.moveToNext());
        }
        cursor.close();

            DiaryAdapter adapter=new DiaryAdapter(DiaryActivity.this,R.layout.diary_item,diaryItemList); //把数据库取出来的时间名字数组显示在listview上
            listView = (ListView) findViewById(R.id.diary_list);
            listView.setAdapter(adapter);
        }
}

