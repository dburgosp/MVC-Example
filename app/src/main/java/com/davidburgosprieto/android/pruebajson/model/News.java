package com.davidburgosprieto.android.pruebajson.model;

import java.util.Date;

/**
 * Class for storing news from https://api.myjson.com/bins/8p7vg
 */
public class News {
    private String title;
    private String newsTicker;
    private Date date;
    private String header;
    private String url;
    private String image;
    private int imageHeight;
    private int ImageWidth;

    /**
     * Constructor for objects of this class.
     *
     * @param title       is the title of the news.
     * @param newsTicker  is the sentence on top of the news headline.
     * @param date        is the publication date of the news.
     * @param header      is the news headline.
     * @param image       is the image that illustrates the news.
     * @param imageHeight is the height of the image that illustrates the news.
     * @param ImageWidth  is the height of the image that illustrates the news.
     * @param url         is the url of the web page with the news.
     */
    public News(String title, String newsTicker, Date date, String header, String image,
                int imageHeight, int ImageWidth, String url) {
        this.title = title;
        this.newsTicker = newsTicker;
        this.date = date;
        this.header = header;
        this.image = image;
        this.imageHeight = imageHeight;
        this.ImageWidth = ImageWidth;
        this.url = url;
    }

    /**
     * Getters and setters.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsTicker() {
        return newsTicker;
    }

    public void setNewsTicker(String newsTicker) {
        this.newsTicker = newsTicker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return ImageWidth;
    }

    public void setImageWidth(int imageWidth) {
        ImageWidth = imageWidth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
