package com.parse.starter;

/**
 * Created by Rakesh Balan on 11/2/2015.
 */
public class Message {

    String fName,messageVal,date,email, objectID;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }


    public String getMessageVal() {
        return messageVal;
    }

    public void setMessageVal(String messageVal) {
        this.messageVal = messageVal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public Message(String fName, String messageVal, String date, String email, String objectID) {
        this.fName = fName;
        this.messageVal = messageVal;
        this.date = date;
        this.email = email;
        this.objectID = objectID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Message{" +
                "fName='" + fName + '\'' +
                ", messageVal='" + messageVal + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
