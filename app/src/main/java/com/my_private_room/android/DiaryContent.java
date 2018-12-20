package com.my_private_room.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my_private_room.android.db.DiaryDbOp;
import com.my_private_room.android.db.MyDatabaseHelper;
import com.my_private_room.android.util.Diary;

public class DiaryContent extends AppCompatActivity {
        private int id;
        private  String diary_name;
        private  String diary_content;
        private  String diary_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_content);
        Intent intent=getIntent();                                  //获得上个活动对应列表项数据库的diary对象数据
        this.diary_name=intent.getStringExtra("diary_name");
        this.diary_content=intent.getStringExtra("diary_content");
        this.diary_date=intent.getStringExtra("diary_date");
        this.id=intent.getIntExtra("diary_id",0);       //靠id来寻找数据库表中对应行删除
        TextView textView1=(TextView)findViewById(R.id.dis_diary_name);        //日记名text框
        textView1.setText(diary_name);
        TextView textView2=(TextView)findViewById(R.id.dis_diary_content);      //日记内容text框
        textView2.setText(diary_content);
        textView2.setMovementMethod(new ScrollingMovementMethod());
        TextView textView3=(TextView)findViewById(R.id.dis_diary_date);         //日记日期text框
        textView3.setText(diary_date);
        ImageView imageView=(ImageView)findViewById(R.id.diary_content_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                //返回功能iamge点击事件
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        final ImageView imageView1=(ImageView) findViewById(R.id.diary_delete);       //删除按钮
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){             //点击删除按钮监听事件
                MyonClick(imageView1);
            }
        });
        ImageView imageView2=(ImageView) findViewById(R.id.diary_edit);       //修改日记键监听
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiaryContent.this,ChangeDiaryActivity.class);
                intent.putExtra("diary_name",diary_name);
                intent.putExtra("diary_content",diary_content);
                intent.putExtra("diary_date",diary_date);
                intent.putExtra("diary_id",id);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onBackPressed() {       //重写系统返回键
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    public void MyonClick(View view){
        switch (view.getId()){
            case R.id.diary_delete:
                AlertDialog.Builder dialog=new AlertDialog.Builder(DiaryContent.this);      //弹出提示框请求确认
                dialog.setTitle("提示");
                dialog.setMessage("删除日记将不可恢复，是否确定删除");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper dbHelper=new MyDatabaseHelper(DiaryContent.this,"PrivateRoom.db",null,3);
                        DiaryDbOp dp=new DiaryDbOp(dbHelper);
                        dp.DelectADiary(id);
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        Toast.makeText(DiaryContent.this,"删除成功！",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
                break;
                default:
                    break;
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){       //重写函数使活动从发表活动回到这个活动时可以重新把数据库里的东西拿出来
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK) {
                    diary_name=data.getStringExtra("return_name");
                    diary_content=data.getStringExtra("return_content");
                    TextView textViewname=(TextView)findViewById(R.id.dis_diary_name);
                    TextView textViewcontent=(TextView)findViewById(R.id.dis_diary_content);
                    TextView textViewdate=(TextView)findViewById(R.id.dis_diary_date);
                    textViewname.setText(diary_name);
                    textViewcontent.setText(diary_content);
                    textViewdate.setText(diary_date);
                }
                if (resultCode == RESULT_CANCELED){

                }
                    break;
            default:
                break;

        }
    }
}
