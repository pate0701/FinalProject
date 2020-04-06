package com.example.finalproject;

public class NewsResponse {
    String date;
    String description;
    String title;
    String hdurl;
    String path;
    long id;


    public NewsResponse(long id, String title, String description, String url) {
        setTitle(title);
        setExplanation(description);
        setHdurl(url);
        setId(id);
    }

    public NewsResponse(String date, String explanation, String title, String hdurl, String url) {
        this.date = date;
        this.description = explanation;
        this.title = title;
        this.hdurl = hdurl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return description;
    }

    public void setExplanation(String explanation) {
        this.description = explanation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
