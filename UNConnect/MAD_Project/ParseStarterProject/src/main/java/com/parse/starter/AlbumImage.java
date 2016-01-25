package com.parse.starter;

/**
 * Created by Priyanka on 12/9/2015.
 */
public class AlbumImage {

    String imageUrl;
    String albumName;
    String userEmail;

    public AlbumImage() {
    }

    public AlbumImage(String imageUrl, String albumName, String userEmail) {
        this.imageUrl = imageUrl;
        this.albumName = albumName;
        this.userEmail = userEmail;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
