package com.my_private_room.android.util;

public class MemorialDay {
    private int id;
    private String name;
    private String date;
    private int days;

    public MemorialDay(){
    }
    public MemorialDay(int id,String name,String date,int days){
        this.id=id;
        this.name=name;
        this.date=date;
        this.days=days;
    }
    public MemorialDay(String name,String date){
        this.name=name;
        this.date=date;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    public void setDays(int days){
        this.days=days;
    }

    public int getDays() {
        return days;
    }
}
