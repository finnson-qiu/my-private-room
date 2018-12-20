package com.my_private_room.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.my_private_room.android.db.MemorialDbOp;
import com.my_private_room.android.db.MyDatabaseHelper;
import com.my_private_room.android.util.MemorialDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MemorialPublish extends AppCompatActivity {

    TextView textView;
    private TimePickerView pvTime;
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_publish);

        final EditText editText=(EditText)findViewById(R.id.memorial_name);
        textView=(TextView)findViewById(R.id.memorial_date);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
            }
        });
        ImageView imageView=(ImageView) findViewById(R.id.memorial_publish);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    String date=textView.getText().toString();
                    String name=editText.getText().toString();
                    if(name.length()!=0){
                        MyDatabaseHelper dbHelper=new MyDatabaseHelper(MemorialPublish.this,"PrivateRoom.db",null,3);
                        MemorialDay memorialDay=new MemorialDay(name,date);
                        MemorialDbOp dp=new MemorialDbOp(memorialDay,dbHelper);
                        dp.MemorialInsert();
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    else{
                        Toast.makeText(MemorialPublish.this,"纪念日内容不能为空",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MemorialPublish.this,"纪念日日期不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView imageView1=(ImageView)findViewById(R.id.memorial_publish_cancel);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void initView() {
                initTimePicker();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
}

    private void initTimePicker(){

        Calendar selectedDate=Calendar.getInstance();
        Calendar startDate=Calendar.getInstance();
        Calendar endDate=Calendar.getInstance();

        startDate.set(1990,0,1);
        endDate.set(2099,11,31);

        pvTime =new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                textView.setText(getTime(date));
                flag=1;
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .setSubmitText("确认")
                .setCancelText("取消")
                .setSubCalSize(18)
                .isCenterLabel(false)
                .setDividerColor(Color.parseColor("#6ed4f7"))
                .setTextColorCenter(Color.WHITE)//设置选中项的颜色
                .setTextColorOut(Color.parseColor("#899494"))//设置没有被选中项的颜色
                .setContentSize(21)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(-10, 0,10, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setTitleBgColor(0xFF333333)
                .setBgColor(0xFF000000)
                .setDecorView(null)
                .build();

    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onBackPressed() {       //重写系统返回键,未确认发表，传回取消结果，告知上一个活动
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
