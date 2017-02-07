package com.example.shara.newsapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shara on 2/6/2017.
 */

public class News {
    String title, author, description, url, urlToImage;
    String publishedAt;

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {

        return urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public News createFromJSON (JSONObject json) throws JSONException, ParseException {
        News news = new News();
        news.setAuthor(json.getString("author"));
        news.setTitle(json.getString("title"));
        news.setDescription(json.getString("description"));
        news.setUrl(json.getString("url"));
        news.setUrlToImage(json.getString("urlToImage"));
        news.setPublishedAt(json.getString("publishedAt"));
        return news;
    }
}
