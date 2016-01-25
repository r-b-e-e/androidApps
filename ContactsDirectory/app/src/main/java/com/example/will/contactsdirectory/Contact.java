package com.example.will.contactsdirectory;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Homework 2
 * Contact
 * Ganesh Ramamoorthy, Rakesh Balan,William Rosmon
 */
public class Contact implements Parcelable, Serializable {

    private String name;
    private String eMail;

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", picture=" + picture +
                ", chkContact='" + chkContact + '\'' +
                '}';
    }

    private String phoneNumber;
    private Uri picture;
    private String chkContact;

    public Contact(String name, String eMail, String phoneNumber, Uri picture) {
        this.name = name;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
    }

    public Contact(String name, String eMail, String phoneNumber, Uri picture, String chkContact) {
        this.name = name;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.chkContact = chkContact;
    }

    public String getChkContact() {
        return chkContact;
    }

    public void setChkContact(String chkContact) {
        this.chkContact = chkContact;
    }

    public Contact(Parcel in) {
        name = in.readString();
        eMail = in.readString();
        phoneNumber = in.readString();
        picture = Uri.parse(in.readString());
        chkContact = in.readString();
    }

    public int compareTo(Contact other) {
        return this.name.compareTo(other.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Uri getPicture() {
        return picture;
    }

    public String geteMail() {
        return eMail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(eMail);
        dest.writeString(phoneNumber);
        dest.writeString(picture.toString());
        dest.writeString(chkContact);

    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
