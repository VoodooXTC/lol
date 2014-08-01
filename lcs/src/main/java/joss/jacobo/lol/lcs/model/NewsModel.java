package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.News.News;
import joss.jacobo.lol.lcs.provider.news.NewsCursor;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model object for the {@code news}.
 */
public class NewsModel{
    public Integer newsId;
    public String category;
    public String title;
    public String link;
    public String image;
    public String author;
    public String description;
    public String content;
    public Integer lastupdated;

    public NewsModel(){}

    public NewsModel(NewsCursor cursor){
        this.newsId = cursor.getNewsId();
        this.category = cursor.getCategory();
        this.title = cursor.getTitle();
        this.link = cursor.getLink();
        this.image = cursor.getImage();
        this.author = cursor.getAuthor();
        this.description = cursor.getDescription();
        this.content = cursor.getContent();
        this.lastupdated = cursor.getLastupdated();
    }

    public NewsModel(News news) {
        this.newsId = news.id;
        this.category = news.category;
        this.title = news.title;
        this.link = news.link;
        this.image = news.image;
        this.author = news.author;
        this.description = news.description;
        this.content = news.content;
        this.lastupdated = news.lastupdated;
    }

    public static List<NewsModel> getList(List<News> newses) {
        List<NewsModel> items = new ArrayList<NewsModel>();
        for(News news : newses){
            items.add(new NewsModel(news));
        }
        return items;
    }
}