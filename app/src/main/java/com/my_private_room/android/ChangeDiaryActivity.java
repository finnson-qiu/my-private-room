package com.my_private_room.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.my_private_room.android.db.DiaryDbOp;
import com.my_private_room.android.db.MyDatabaseHelper;
import com.my_private_room.android.util.Diary;

import java.util.ArrayList;

public class ChangeDiaryActivity extends AppCompatActivity {

    private String diary_name;
    private String  diary_content;
    private String diary_date;
    private int diary_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_diary);
        final EditText editText1=(EditText) findViewById(R.id.chge_diary_name);
        final EditText editText2=(EditText) findViewById(R.id.chge_diary_content);
        TextView textView=(TextView)findViewById(R.id.chge_diary_date);
        final Intent intent=getIntent();
        diary_name=intent.getStringExtra("diary_name");
        diary_content=intent.getStringExtra("diary_content");
        diary_date=intent.getStringExtra("diary_date");
        diary_id=intent.getIntExtra("diary_id",0);
        editText1.setText(diary_name);
        editText2.setText(diary_content);
        textView.setText(diary_date);
        final TextView textView11=(TextView)findViewById(R.id.diary_change_confirm);
        textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyonClick(textView11);
            }
        });
        TextView textView2=(TextView)findViewById(R.id.diary_change_cancel);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent();
                setResult(RESULT_CANCELED,intent2);
                finish();
            }
        });
    }

    public void MyonClick(View view){
        switch (view.getId()){
            case R.id.diary_change_confirm:
                AlertDialog.Builder dialog=new AlertDialog.Builder(ChangeDiaryActivity.this);      //弹出提示框请求确认
                dialog.setTitle("提示");
                dialog.setMessage("是否确定修改");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ChangeDiaryActivity.this,"PrivateRoom.db",null,3);
                        EditText editText11=(EditText)findViewById(R.id.chge_diary_name);
                        EditText editText22=(EditText)findViewById(R.id.chge_diary_content);
                        diary_name=editText11.getText().toString();
                        diary_content=editText22.getText().toString();
                        Diary diary=new Diary(diary_id,diary_name,diary_content,diary_date);
                        DiaryDbOp dp=new DiaryDbOp(diary,dbhelper);
                        dp.ChangeDiary();
                        Intent intent1=new Intent();
                        intent1.putExtra("return_name",diary_name);
                        intent1.putExtra("return_content",diary_content);
                        setResult(RESULT_OK,intent1);
                        Toast.makeText(ChangeDiaryActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
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
}
