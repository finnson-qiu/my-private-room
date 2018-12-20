package com.my_private_room.android.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.my_private_room.android.R;
import com.my_private_room.android.util.MemorialDay;

import java.util.List;

public class MemorialAdapter extends ArrayAdapter<MemorialDay> {
    private int resourceid;

    public MemorialAdapter(Context context, int textViewResourceId,  List<MemorialDay> objects) {
        super(context, textViewResourceId, objects);
        resourceid=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){          //memorialday适配器用于显示listview每一项内容
        MemorialDay memorialDay=getItem(position);
        View view;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resourceid, parent, false);
        }
        else{
            view=convertView;
        }
        TextView memorialdayname=(TextView)view.findViewById(R.id.memorial_item_name);
        TextView memorialdaydate=(TextView)view.findViewById(R.id.memorial_item_date);
        TextView memorialdaydays=(TextView)view.findViewById(R.id.memorial_item_days);
        if(memorialDay.getDays()<0) {
            memorialdayname.setText(" 距离"+memorialDay.getName()+"还有");
            memorialdaydays.setText(-memorialDay.getDays()+"天");
        }
        else{
            memorialdayname.setText(" "+memorialDay.getName());
            memorialdaydays.setText(memorialDay.getDays()+"天");
        }
        memorialdaydate.setText(memorialDay.getDate());

        return view;
    }
}
