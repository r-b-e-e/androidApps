package com.rakeshbalancompany.mad_midterm;

/**
 * Created by rakeshbalan on 10/26/2015.
 */
public class News {
    String title, pub_date, thumb_image, news_link;

//    public News(String title, String pub_date, String thumb_image, String news_link)
//    {
//        this.title= title;
//        this.pub_date= pub_date;
//        this.thumb_image= thumb_image;
//        this.news_link= news_link;
//    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getNews_link() {
        return news_link;
    }

    public void setNews_link(String news_link) {
        this.news_link = news_link;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", pub_date='" + pub_date + '\'' +
                ", thumb_image='" + thumb_image + '\'' +
                ", news_link='" + news_link + '\'' +
                '}';
    }


}
