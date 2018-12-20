package com.my_private_room.android;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.my_private_room.android.Adapter.MemorialAdapter;
import com.my_private_room.android.db.MemorialDbOp;
import com.my_private_room.android.db.MyDatabaseHelper;
import com.my_private_room.android.util.MemorialDay;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MemorialActivity extends AppCompatActivity {

    private List<MemorialDay>memorialDayList=new ArrayList<>();
    private MemorialDay memorialday;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial);

        this.GetViewList();     //进入活动得到listview界面

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemorialDay memorialDay=memorialDayList.get(position);
                Intent intent=new Intent(MemorialActivity.this,MemorialContent.class);
                intent.putExtra("memorial_name",memorialDay.getName());
                intent.putExtra("memorial_days",memorialDay.getDays());
                intent.putExtra("memorial_date",memorialDay.getDate());
                intent.putExtra("memorial_id",memorialDay.getId());
                startActivityForResult(intent,2);
            }
        });

        ImageView imageView=(ImageView)findViewById(R.id.memorial_add);         //添加纪念日监听事件
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MemorialActivity.this,MemorialPublish.class);
                startActivityForResult(intent,1);
            }
        });
        ImageView imageView1=(ImageView)findViewById(R.id.memorial_back);       //返回键监听事件
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void GetViewList(){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(this,"PrivateRoom.db",null,3);       // 把数据库里的数据取出，并且靠memorial适配器显示在listview上
        MemorialDbOp dp=new MemorialDbOp(dbHelper);
        Cursor cursor=dp.MemorialRetrieve();
        if(cursor.moveToFirst()){           //把数据库的数据存到字符串,并且送到对象
            do{
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                int days=cursor.getInt(cursor.getColumnIndex("days"));
                memorialday=new MemorialDay(id,name,date,days);
                memorialDayList.add(memorialday);
            }while(cursor.moveToNext());
        }
        cursor.close();

        MemorialAdapter adapter=new MemorialAdapter(MemorialActivity.this,R.layout.memorial_item,memorialDayList);      //用适配器把每一项对应的显示出来
        listView=(ListView)findViewById(R.id.memorial_list);
        listView.setAdapter(adapter);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){       //重写函数使活动从发表活动回到这个活动时可以重新把数据库里的东西拿出来
        switch(requestCode){
            case 1:
                memorialDayList=new ArrayList<>();
                if(resultCode==RESULT_OK){
                    this.GetViewList();
                }
                break;
            case 2:
                memorialDayList=new ArrayList<>();
                if(resultCode==RESULT_OK){
                    this.GetViewList();
                }
                break;
            default:

        }
    }
}
