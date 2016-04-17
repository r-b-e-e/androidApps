package com.priyanka.itunefeedrssapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rakesh Balan on 9/30/2015.
 */
public class Media implements Parcelable {

    String price;
    String currency;
    String small_image;
    String large_image;
    String artist;
    String title;
    String link_to_media;
    String category;
    String releaseDate;
    String duration;
    String summary;

    public Media (){};

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getLarge_image() {
        return large_image;
    }

    public void setLarge_image(String large_image) {
        this.large_image = large_image;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink_to_media() {
        return link_to_media;
    }

    public void setLink_to_media(String link_to_media) {
        this.link_to_media = link_to_media;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    @Override
    public String toString() {
        return "Media{" +
                "price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                ", small_image='" + small_image + '\'' +
                ", large_image='" + large_image + '\'' +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", link_to_media='" + link_to_media + '\'' +
                ", category='" + category + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration='" + duration + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( price);
        dest.writeString( currency);
        dest.writeString( small_image);
        dest.writeString( large_image);
        dest.writeString( artist);
        dest.writeString( title);
        dest.writeString( link_to_media);
        dest.writeString( category);
        dest.writeString( releaseDate);
        dest.writeString( duration);
        dest.writeString( summary);
    }

    public static final Parcelable.Creator<Media> CREATOR
            = new Parcelable.Creator<Media>() {
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    private Media(Parcel in) {
        //moving to Parcel - the sequence of write and read variables should be same
        this.price = in.readString();
        this.currency = in.readString();
        this.small_image = in.readString();
        this.large_image = in.readString();
        this.artist = in.readString();
        this.title = in.readString();
        this.link_to_media = in.readString();
        this.category = in.readString();
        this.releaseDate = in.readString();
        this.duration = in.readString();
        this.summary = in.readString();
    }

}
