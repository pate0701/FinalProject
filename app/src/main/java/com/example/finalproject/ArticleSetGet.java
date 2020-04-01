package com.example.finalproject;
import java.io.Serializable;

/**
 * This class holds the details of an articleSetGet
 *
 * To pass it through activity using intents it is implementing
 * Serializable interface
 * */
class ArticleSetGet implements Serializable {

    private String id;
    private String title;
    private String url;
    private String sectionName;



    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }
    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    String getSectionName() {
        return sectionName;
    }

    void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }


}