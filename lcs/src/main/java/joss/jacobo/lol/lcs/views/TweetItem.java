package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.utils.DateTimeFormatter;

/**
 * Created by Joss on 7/27/2014
 */
public class TweetItem extends LinearLayout {

    Context context;

    ImageView avatar;
    TextView username;
    TextView twitterHandle;
    TextView tweetText;
    TextView createdAt;
    View divider;

    public TweetItem(Context context) {
        this(context, null);
    }

    public TweetItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TweetItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_item_tweet, this, true);
        avatar = (ImageView) findViewById(R.id.tweet_item_avatar);
        username = (TextView) findViewById(R.id.tweet_item_username);
        twitterHandle = (TextView) findViewById(R.id.tweet_item_twitter_handle);
        tweetText = (TextView) findViewById(R.id.tweet_item_content);
        createdAt = (TextView) findViewById(R.id.tweet_item_created_at);
        divider = findViewById(R.id.divider);
    }

    public void setContent(TweetsModel tweet){
        Picasso.with(context).load(tweet.userImageUrl).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(avatar);
        username.setText(tweet.userName);
        twitterHandle.setText("@" + tweet.screenName);
        tweetText.setText(tweet.text);
        linkyfy(tweetText);
        createdAt.setText(DateTimeFormatter.formatMillis(tweet.createdAt));
    }

    private void linkyfy(TextView textView){

        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            public final String transformUrl(final Matcher match, String url) {
                return match.group();
            }
        };

        Pattern mentionPattern = Pattern.compile("@([A-Za-z0-9_-]+)");
        String mentionScheme = "http://www.twitter.com/";
        Linkify.addLinks(textView, mentionPattern, mentionScheme, null, filter);

        Pattern hashtagPattern = Pattern.compile("#([A-Za-z0-9_-]+)");
        String hashtagScheme = "http://www.twitter.com/search/";
        Linkify.addLinks(textView, hashtagPattern, hashtagScheme, null, filter);

        Pattern urlPattern = Patterns.WEB_URL;
        Linkify.addLinks(textView, urlPattern, null, null, filter);
    }
}
