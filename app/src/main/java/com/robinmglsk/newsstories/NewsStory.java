package com.robinmglsk.newsstories;

public class NewsStory {

    private String title;
    private String sectionName;
    private String url;
    private String author;
    private long publishedAt;

    public NewsStory(String title, String sectionName, String url, String author, long publishedAt){
        this.title = title;
        this.sectionName = sectionName;
        this.url = url;
        this.publishedAt = publishedAt;
        this.author = author;
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

    public String getAuthor(){
        return this.author;
    }

    public long getPublishedAt(){
        return this.publishedAt;
    }

}
