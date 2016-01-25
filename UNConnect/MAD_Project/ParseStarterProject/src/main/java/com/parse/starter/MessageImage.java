package com.parse.starter;

import com.parse.ParseUser;

/**
 * Created by Priyanka on 12/10/2015.
 */
public class MessageImage {
    String imageUrl;
    ParseUser sender;
    String message_subject;
    ParseUser receiver;

    public MessageImage(){}

    public MessageImage(String imageUrl, ParseUser sender, String message_subject, ParseUser receiver) {
        this.imageUrl = imageUrl;
        this.sender = sender;
        this.message_subject = message_subject;
        this.receiver = receiver;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ParseUser getSender() {
        return sender;
    }

    public void setSender(ParseUser sender) {
        this.sender = sender;
    }

    public String getMessage_subject() {
        return message_subject;
    }

    public void setMessage_subject(String message_subject) {
        this.message_subject = message_subject;
    }

    public ParseUser getReceiver() {
        return receiver;
    }

    public void setReceiver(ParseUser receiver) {
        this.receiver = receiver;
    }
}
