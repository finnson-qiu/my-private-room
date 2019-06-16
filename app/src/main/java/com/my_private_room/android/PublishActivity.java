package com.my_private_room.android;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.my_private_room.android.db.DiaryDbOp;
import com.my_private_room.android.db.MyDatabaseHelper;
import com.my_private_room.android.util.Diary;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishActivity extends AppCompatActivity {

    private EditText editText=null;
    private EditText editText1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        editText=(EditText)findViewById(R.id.publishSay);
        editText1=(EditText)findViewById(R.id.diary_name);
        final MyDatabaseHelper dbHelper =new MyDatabaseHelper(this,"PrivateRoom.db",null,3);
        ImageView imageView= (ImageView) findViewById(R.id.diary_publish);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp1=editText.getText().toString();
                String temp2=editText1.getText().toString();
                if(temp1.length()==0&& temp2.length()!=0){
                    Toast.makeText(PublishActivity.this,"日记内容不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                else if(temp2.length()==0&&temp1.length()!=0){
                    Toast.makeText(PublishActivity.this,"日记标题不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                else if(temp1.length()==0&&temp2.length()==0){
                    Toast.makeText(PublishActivity.this,"日记标题和内容不能为空",
                            Toast.LENGTH_SHORT).show();
                }
                else if(temp2.length()>12){
                    Toast.makeText(PublishActivity.this,"标题不大于12个汉字或字母",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper.getWritableDatabase();
                    Diary diary=new Diary();
                    diary.setContent(editText.getText().toString());
                    diary.setName(editText1.getText().toString());
                    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String str = formatter.format(curDate);
                    diary.setDate(str);
                    DiaryDbOp dop=new DiaryDbOp(diary,dbHelper);
                    dop.DiaryInsert();
                    Toast.makeText(PublishActivity.this,"发表成功！",
                    Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("data_return",str);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
        ImageView imageView1=(ImageView)findViewById(R.id.diary_publish_cancel);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    public void onBackPressed() {       //重写系统返回键
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
