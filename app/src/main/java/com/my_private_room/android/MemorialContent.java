package com.my_private_room.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my_private_room.android.db.DiaryDbOp;
import com.my_private_room.android.db.MemorialDbOp;
import com.my_private_room.android.db.MyDatabaseHelper;

public class MemorialContent extends AppCompatActivity {

    private String memorial_name;
    private String memorial_date;
    private int memorial_id;
    private int memorial_days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_content);
        Intent intent=getIntent();
        memorial_date=intent.getStringExtra("memorial_date");
        memorial_name=intent.getStringExtra("memorial_name");
        memorial_days=intent.getIntExtra("memorial_days",0);
        memorial_id=intent.getIntExtra("memorial_id",0);
        TextView textView1=(TextView)findViewById(R.id.dis_memorial_name);
        int temp;
        if(memorial_days<0) {
            textView1.setText("距离 "+memorial_name+" 还有");
            temp=-memorial_days;
        }
        else{
            textView1.setText(memorial_name);
            temp=memorial_days;
        }
        TextView textView2=(TextView)findViewById(R.id.dis_memorial_date);
        textView2.setText("起始日： "+memorial_date);
        TextView textView3=(TextView)findViewById(R.id.dis_memorial_days);
        String temp1=""+temp;
        textView3.setText(temp1+" Days");

        final ImageView imageView=(ImageView)findViewById(R.id.memorial_delete);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyonClick(imageView);
            }
        });

        ImageView imageView1=(ImageView)findViewById(R.id.memorial_content_back);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    public void MyonClick(View view){
        switch (view.getId()){
            case R.id.memorial_delete:
                AlertDialog.Builder dialog=new AlertDialog.Builder(MemorialContent.this);      //弹出提示框请求确认
                dialog.setTitle("提示");
                dialog.setMessage("删除纪念日将不可恢复，是否确定删除");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper dbHelper=new MyDatabaseHelper(MemorialContent.this,"PrivateRoom.db",null,3);
                        MemorialDbOp dp=new MemorialDbOp(dbHelper);
                        dp.DeleteAMemorial(memorial_id);
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        Toast.makeText(MemorialContent.this,"删除成功！",Toast.LENGTH_SHORT).show();
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

