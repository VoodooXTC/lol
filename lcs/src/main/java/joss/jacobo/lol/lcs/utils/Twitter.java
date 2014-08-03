package joss.jacobo.lol.lcs.utils;

import java.util.List;

import twitter4j.Query;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Joss on 7/26/2014
 */
public class Twitter {

    private static final String CONSUMER_KEY = "dnQsLfMhbHvhpw2uI47DABTIS";
    private static final String CONSUMER_SECRET = "fz4GBTTIiziPKxMjwxZNl7TCnjzKT2dlo3eWB0XwpXbrCjZBur";
    private static final String ACCESS_TOKEN = "147068854-R2gZalMYPvEGSJJ3NqJ3dZ96HDSfKyd0SJQz2Dor";
    private static final String ACCESS_TOKEN_SECRET = "p6fUO0KKOcrwqVX134opoyP1Nl3Qksk5a0EzZxjUuRbtK";

    public static void getUsersTimeline(String username, TwitterCallback callback){

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();

        try {
            ResponseList<Status> statuses = twitter.getUserTimeline(username);

            if(statuses != null && statuses.size() > 0) {
                callback.onSuccess(statuses);
                return;
            }else{
                callback.onError("No Tweets Found");
                return;
            }

        } catch (TwitterException e) {
            e.printStackTrace();
        }

        callback.onError("An Error Occurred");
    }

    public static void getLCSTweets(TwitterHashTagCallback callback){

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();

        try {
            List<Status> statuses = twitter.search().search(new Query("#LCS")).getTweets();

            if(statuses != null && statuses.size() > 0) {
                callback.onSuccess(statuses);
                return;
            }else{
                callback.onError("No Tweets Found");
                return;
            }

        } catch (TwitterException e) {
            e.printStackTrace();
        }

        callback.onError("An Error Occurred");
    }

    public interface TwitterCallback{
        void onSuccess(ResponseList<Status> statuses);
        void onError(String errorMessage);
    }

    public interface TwitterHashTagCallback{
        void onSuccess(List<Status> statuses);
        void onError(String errorMessage);
    }
}
