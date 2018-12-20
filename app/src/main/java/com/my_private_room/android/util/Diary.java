package com.my_private_room.android.util;

public class Diary {
    private int id;
    private String name;
    private String content;
    private String date;
    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setDate(String date){

        this.date=date;
    }
    public String getDate(){

        return date;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public Diary(int id,String name, String content,String date){
        this.id=id;
        this.name=name;
        this.content=content;
        this.date=date;
    }
    public Diary(){
    }
}
