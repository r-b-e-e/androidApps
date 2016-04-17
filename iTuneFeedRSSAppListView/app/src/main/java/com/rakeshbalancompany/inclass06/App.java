package com.rakeshbalancompany.inclass06;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rakeshbalan on 10/5/2015.
 */
public class App implements Serializable{
    String appName;
    String developerName;
    String releaseDate;
    String category;
    String price;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    String currency;
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAppName() {
        return appName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
