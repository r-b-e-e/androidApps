package com.parse.starter;

import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by rakeshbalan on 11/6/2015.
 */
public class Message {
    String name;
    String msg;
    String date;
    String objectId;
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Message(String name, String msg, String date, String objectId, String userName) {
        this.userName = userName;
        this.objectId = objectId;
        this.date = date;
        this.msg = msg;
        this.name = name;
    }

    public String getObjectId() {

        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
