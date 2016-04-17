package com.parse.starter;

/**
 * Created by Rakesh Balan on 12/9/2015.
 */
public class ImageURLHolderClass {

    private static ImageURLHolderClass dataobject = null;
    private String ImageURL;
    private User user;
    private String title;
    int position;
    private ImageURLHolderClass() {
    }

    public static ImageURLHolderClass getInstance() {
        if (dataobject == null)
            dataobject = new ImageURLHolderClass();
        return dataobject;
    }

    public static void setDataobject(ImageURLHolderClass dataobject) {
        ImageURLHolderClass.dataobject = dataobject;
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
