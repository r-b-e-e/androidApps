package com.parse.starter;

/**
 * Created by Priyanka on 11/19/2015.
 */
public class User {

    String fname,lname, email, password, datetime, gender, photoUrl;
    boolean isProfilePrivate,isMesgAllowed, isNotification;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public User(){}

    public User(String fname, String lname, String email, String password,
                String datetime, String gender, String photoUrl, boolean isProfilePrivate,
                boolean isMesgAllowed, boolean isNotification) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.datetime = datetime;
        this.gender = gender;
        this.photoUrl = photoUrl;
        this.isProfilePrivate = isProfilePrivate;
        this.isMesgAllowed = isMesgAllowed;
        this.isNotification = isNotification;
    }

    public User(String fname, String lname, String email, String password,String gender, String photoUrl) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.photoUrl = photoUrl;
    }

    public User(String fname, String lname, String email, String password, String datetime, String gender, String photoUrl) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.datetime = datetime;
        this.gender = gender;
        this.photoUrl = photoUrl;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public boolean isProfilePrivate() {
        return isProfilePrivate;
    }

    public void setIsProfilePrivate(boolean isProfilePrivate) {
        this.isProfilePrivate = isProfilePrivate;
    }

    public boolean isMesgAllowed() {
        return isMesgAllowed;
    }

    public void setIsMesgAllowed(boolean isMesgAllowed) {
        this.isMesgAllowed = isMesgAllowed;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setIsNotification(boolean isNotification) {
        this.isNotification = isNotification;
    }
}
