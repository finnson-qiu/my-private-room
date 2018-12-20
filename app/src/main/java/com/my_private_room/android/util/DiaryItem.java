package com.my_private_room.android.util;

public class DiaryItem {
    private String name;
    Diary diary;
    public DiaryItem(){

    }
    public  DiaryItem(String name,Diary diary){
        this.name=name;
        this.diary=diary;
    }

    public void setName(String name) {
        this.name = name;
    }
    public  String getName(){
        return name;
    }
    public void setDiary(Diary diary){
        this.diary=diary;
    }
    public Diary getDiary(){
        return diary;
    }
}
