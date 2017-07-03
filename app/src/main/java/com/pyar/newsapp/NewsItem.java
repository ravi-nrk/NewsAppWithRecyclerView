package com.pyar.newsapp;

import java.util.PriorityQueue;

/**
 * Created by user1 on 30-Jun-17.
 */

public class NewsItem {
    private String author,title,description,url,urlToImage,publishedAt;

   public  NewsItem(String Author,String Title,String Description,String Url,String UrlToImage,String PublishedAt)
    {
        this.author=Author;
        this.title=Title;
        this.description=Description;
        this.url=Url;
        this.urlToImage=UrlToImage;
        this.publishedAt= PublishedAt;
    }
    public String getAuthor()
    {
     return author;
    }
    public String getItemTitle()
    {
        return title;
    }
    public String getItemDescription()
    {
        return description;
    }
    public String getItemUrl()
    {
        return url;
    }
    public String getItemUrlToImage()
    {
        return urlToImage;
    }
    public String getPublishedAt()
    {
        return publishedAt;
    }

}
