package com.example.group10b_hw03;

import java.util.ArrayList;
/**
 * Ganesh Ramamoorthy, Rakesh Balan
 */
public class Question {
    private String qID;
    private String qName;
    private ArrayList<String> opt;
    private String imageURL;

    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public ArrayList<String> getOpt() {
        return opt;
    }

    public void setOpt(ArrayList<String> opt) {
        this.opt = opt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Question(String qID, String qName, ArrayList<String> opt, String imageURL) {
        this.qID = qID;
        this.qName = qName;
        this.opt = opt;
        this.imageURL = imageURL;
    }
}
