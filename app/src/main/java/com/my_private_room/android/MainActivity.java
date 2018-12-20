package com.my_private_room.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1=(Button)findViewById(R.id.mabt1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,MemorialActivity.class);
                startActivity(intent1);
            }
        });
        Button button2=(Button)findViewById(R.id.mabt2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,DailyActivity.class);
                startActivity(intent2);
            }
        });
        Button button3=(Button)findViewById(R.id.mabt3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(MainActivity.this,DiaryActivity.class);
                startActivity(intent3);
            }
        });
        Button button4=(Button)findViewById(R.id.mabt4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(MainActivity.this,AlbumActivity.class);
                startActivity(intent4);
            }
        });
        ImageView image=(ImageView)findViewById(R.id.main_back);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"再按一次退出",
                Toast.LENGTH_SHORT).show();
                ImageView image1=(ImageView)findViewById(R.id.main_back) ;
                image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
    }
}
