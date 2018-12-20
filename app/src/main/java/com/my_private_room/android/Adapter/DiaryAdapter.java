package com.my_private_room.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.my_private_room.android.R;
import com.my_private_room.android.util.Diary;
import com.my_private_room.android.util.DiaryItem;

import java.util.List;

public class DiaryAdapter extends ArrayAdapter<Diary>{          //泛型为DiaryItem类的自定义适配器
    private int resourceId;
    public DiaryAdapter(Context context, int textViewResourceId, List<Diary> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Diary diary=getItem(position);
        View view;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }
        else{
            view=convertView;
        }
        TextView diaryitemname=(TextView)view.findViewById(R.id.diary_item_name);
        diaryitemname.setText(diary.getName());
        TextView diaryitemcontent=(TextView)view.findViewById(R.id.diary_item_content);
        diaryitemcontent.setText(diary.getContent());
        TextView diaryitemdate=(TextView)view.findViewById(R.id.diary_item_date);
        diaryitemdate.setText(diary.getDate());
        return view;
    }
}
