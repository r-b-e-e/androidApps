package com.example.group10b_hw05;

import java.io.Serializable;
/**
 * Ganesh Ramamoorthy
 * Rakesh Balan
 * Priyanka
 */
public class TedShow implements Serializable {
    String title;
    String description;
    String image;
    String duration;
    String publicationDate;
    String audio;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", duration='" + duration + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", audio='" + audio + '\'' +
                '}';
    }
}
