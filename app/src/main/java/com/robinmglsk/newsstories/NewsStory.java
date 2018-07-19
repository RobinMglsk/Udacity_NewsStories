package com.robinmglsk.newsstories;

public class NewsStory {

    private String title;
    private String sectionName;
    private String url;
    private String auhtor;
    private long publishedAt;

    public NewsStory(String title, String sectionName, String url){
        this.title = title;
        this.sectionName = sectionName;
        this.url = url;
    }

    public NewsStory(String title, String sectionName, String url, long publishedAt){
        this.title = title;
        this.sectionName = sectionName;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    public String getTitle(){
        return this.title;
    }

    public String getSectionName(){
        return this.sectionName;
    }

    public String getUrl(){
        return this.url;
    }

    public String getAuhtor(){
        return this.auhtor;
    }

    public long getPublishedAt(){
        return this.publishedAt;
    }

}
