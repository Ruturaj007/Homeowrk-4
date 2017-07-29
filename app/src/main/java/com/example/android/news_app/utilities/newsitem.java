package com.example.android.news_app.utilities;

/**
 * Created by Welcome To Future on 6/29/2017.
 */

public class newsitem
{

    public newsitem(String source,String title, String description, String url, String author, String properties, String urlToImage, String publishedAt) {
        this.title = title;
        this.source=source;
        this.description = description;
        this.url = url;
        this.author = author;
        this.properties = properties;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    String title;
    String description;
    String url;
    String author;
    String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    String properties;
    String urlToImage;
    String publishedAt;
    public newsitem(){}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getProperties() {
        return properties;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }



}
