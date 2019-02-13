package com.zp.myhomemanager.database;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;

import java.io.Serializable;

public class MemoData extends SugarRecord implements Serializable {

    @Expose
    private String title; //标题

    @Expose
    private String con;   //内容

    @Expose
    private String user;  //创建者

    @Expose
    private long time;    //提醒时间

    @Expose
    private long createtime;    //创建时间

    @Expose
    private int type;     //提醒类型

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toSpeakString()
    {
        return "提醒，"+title+"，内容，"+con;
    }
}
