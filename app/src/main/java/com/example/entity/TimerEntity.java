package com.example.entity;

/**
 * Created by HP on 2017/1/13.
 */

public class TimerEntity {
    private int resourceId;
    private String title;
    private String date;

    public TimerEntity(String date,String title ){
        this.resourceId=0;
        this.title=title;
        this.date=date;
    }
    public void setResourceId(int id){
        this.resourceId=id;
    }
    public String getTitle(){
        return title;
    }

    public int getResourceId(){
        return resourceId;
    }

    public String getDate() {
        return date;
    }
}
