package com.parse.starter;

import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by Rakesh Balan on 12/9/2015.
 */
public class Message {

    ParseUser toUser;
    ParseUser fromUser;
    String subject;
    String body;
    String image_url;
    Date datetime;
    boolean read;

    public Message() {
    }

    public Message(ParseUser toUser, ParseUser fromUser, String subject, String body, String image_url, Date datetime, boolean read) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.subject = subject;
        this.body = body;
        this.image_url = image_url;
        this.datetime = datetime;
        this.read = read;
    }

    public ParseUser getToUser() {
        return toUser;
    }

    public void setToUser(ParseUser toUser) {
        this.toUser = toUser;
    }

    public ParseUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(ParseUser fromUser) {
        this.fromUser = fromUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
