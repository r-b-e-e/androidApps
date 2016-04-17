package com.parse.starter;

import com.parse.ParseUser;

import java.util.List;

// Created by Rakesh Balan Lingakumar

public class Album {
    String albumTitle;
    ParseUser albumOwnerName;
    List<User> invitedUsers;
    List<User> sharedUsers;
    List<AlbumImage> imageURL;
    String accesslevel;

    public Album() {
    }

    public Album(String albumTitle, ParseUser albumOwnerName, List<User> invitedUsers, List<User> sharedUsers,
                 List<AlbumImage> imageURL, String accesslevel) {
        this.albumTitle = albumTitle;
        this.albumOwnerName = albumOwnerName;
        this.invitedUsers = invitedUsers;
        this.sharedUsers = sharedUsers;
        this.imageURL = imageURL;
        this.accesslevel = accesslevel;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public ParseUser getAlbumOwnerName() {
        return albumOwnerName;
    }

    public void setAlbumOwnerName(ParseUser albumOwnerName) {
        this.albumOwnerName = albumOwnerName;
    }

    public List<User> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(List<User> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public List<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public List<AlbumImage> getImageURL() {
        return imageURL;
    }

    public void setImageURL(List<AlbumImage> imageURL) {
        this.imageURL = imageURL;
    }

    public String getAccesslevel() {
        return accesslevel;
    }

    public void setAccesslevel(String accesslevel) {
        this.accesslevel = accesslevel;
    }

}
