package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.provider.tweets.TweetsCursor;
import twitter4j.ResponseList;
import twitter4j.Status;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model object for the {@code tweets}.
 */
public class TweetsModel{
    public String twitterHandle;
    public Long tweetId;
    public Long createdAt;
    public String userDescription;
    public String userName;
    public String userImageUrl;
    public String screenName;
    public String text;

    public TweetsModel(){}

    public TweetsModel(TweetsCursor cursor){
        this.twitterHandle = cursor.getTwitterHandle();
        this.tweetId = cursor.getTweetId();
        this.createdAt = cursor.getCreatedAt();
        this.userDescription = cursor.getUserDescription();
        this.userName = cursor.getUserName();
        this.userImageUrl = cursor.getUserImageUrl();
        this.screenName = cursor.getScreenName();
        this.text = cursor.getText();
    }

    public TweetsModel(Status status) {
        this.twitterHandle = status.getUser().getScreenName();
        this.tweetId = status.getId();
        this.createdAt = status.getCreatedAt().getTime();
        this.userDescription = status.getUser().getDescription();
        this.userName = status.getUser().getName();
        this.userImageUrl = status.getUser().getOriginalProfileImageURL();
        this.screenName = status.getUser().getScreenName();
        this.text = status.getText();
    }

    public static List<TweetsModel> getList(ResponseList<Status> statuses) {
        List<TweetsModel> items = new ArrayList<TweetsModel>();
        for(Status status : statuses){
            items.add(new TweetsModel(status));
        }
        return items;
    }

    public static List<TweetsModel> getList(List<Status> statuses) {
        List<TweetsModel> items = new ArrayList<TweetsModel>();
        for(Status status : statuses){
            items.add(new TweetsModel(status));
        }
        return items;
    }
}