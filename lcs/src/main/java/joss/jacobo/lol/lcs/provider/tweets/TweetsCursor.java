package joss.jacobo.lol.lcs.provider.tweets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code tweets} table.
 */
public class TweetsCursor extends AbstractCursor {
    public TweetsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code twitter_handle} value.
     * Can be {@code null}.
     */
    public String getTwitterHandle() {
        Integer index = getCachedColumnIndexOrThrow(TweetsColumns.TWITTER_HANDLE);
        return getString(index);
    }

    /**
     * Get the {@code tweet_id} value.
     * Can be {@code null}.
     */
    public Long getTweetId() {
        return getLongOrNull(TweetsColumns.TWEET_ID);
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    public Long getCreatedAt() {
        return getLongOrNull(TweetsColumns.CREATED_AT);
    }

    /**
     * Get the {@code user_description} value.
     * Can be {@code null}.
     */
    public String getUserDescription() {
        Integer index = getCachedColumnIndexOrThrow(TweetsColumns.USER_DESCRIPTION);
        return getString(index);
    }

    /**
     * Get the {@code user_name} value.
     * Can be {@code null}.
     */
    public String getUserName() {
        Integer index = getCachedColumnIndexOrThrow(TweetsColumns.USER_NAME);
        return getString(index);
    }

    /**
     * Get the {@code user_image_url} value.
     * Can be {@code null}.
     */
    public String getUserImageUrl() {
        Integer index = getCachedColumnIndexOrThrow(TweetsColumns.USER_IMAGE_URL);
        return getString(index);
    }

    /**
     * Get the {@code screen_name} value.
     * Can be {@code null}.
     */
    public String getScreenName() {
        Integer index = getCachedColumnIndexOrThrow(TweetsColumns.SCREEN_NAME);
        return getString(index);
    }

    /**
     * Get the {@code text} value.
     * Can be {@code null}.
     */
    public String getText() {
        Integer index = getCachedColumnIndexOrThrow(TweetsColumns.TEXT);
        return getString(index);
    }

    public List<TweetsModel> getTweets() {
        List<TweetsModel> items = new ArrayList<TweetsModel>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new TweetsModel(this));
                moveToNext();
            }
        }
        return items;
    }
}
