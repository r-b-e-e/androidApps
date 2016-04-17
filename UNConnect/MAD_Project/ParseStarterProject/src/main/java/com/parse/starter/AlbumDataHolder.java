package com.parse.starter;

import com.parse.ParseObject;

// Created by Rakesh Balan Lingakumar

public class AlbumDataHolder {

    private static AlbumDataHolder dataobject = null;
    private ParseObject album;

    private AlbumDataHolder() {
    }

    public static AlbumDataHolder getInstance() {
        if (dataobject == null)
            dataobject = new AlbumDataHolder();
        return dataobject;
    }

    public static void setDataobject(AlbumDataHolder dataobject) {
        AlbumDataHolder.dataobject = dataobject;
    }

    public static AlbumDataHolder getDataobject() {
        return dataobject;
    }

    public ParseObject getAlbum() {
        return album;
    }

    public void setAlbum(ParseObject album) {
        this.album = album;
    }
}
