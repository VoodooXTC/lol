package joss.jacobo.lol.lcs.provider.tweets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code tweets} table.
 */
public class TweetsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TweetsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, TweetsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public TweetsContentValues putTwitterHandle(String value) {
        mContentValues.put(TweetsColumns.TWITTER_HANDLE, value);
        return this;
    }

    public TweetsContentValues putTwitterHandleNull() {
        mContentValues.putNull(TweetsColumns.TWITTER_HANDLE);
        return this;
    }


    public TweetsContentValues putTweetId(Long value) {
        mContentValues.put(TweetsColumns.TWEET_ID, value);
        return this;
    }

    public TweetsContentValues putTweetIdNull() {
        mContentValues.putNull(TweetsColumns.TWEET_ID);
        return this;
    }


    public TweetsContentValues putCreatedAt(Long value) {
        mContentValues.put(TweetsColumns.CREATED_AT, value);
        return this;
    }

    public TweetsContentValues putCreatedAtNull() {
        mContentValues.putNull(TweetsColumns.CREATED_AT);
        return this;
    }


    public TweetsContentValues putUserDescription(String value) {
        mContentValues.put(TweetsColumns.USER_DESCRIPTION, value);
        return this;
    }

    public TweetsContentValues putUserDescriptionNull() {
        mContentValues.putNull(TweetsColumns.USER_DESCRIPTION);
        return this;
    }


    public TweetsContentValues putUserName(String value) {
        mContentValues.put(TweetsColumns.USER_NAME, value);
        return this;
    }

    public TweetsContentValues putUserNameNull() {
        mContentValues.putNull(TweetsColumns.USER_NAME);
        return this;
    }


    public TweetsContentValues putUserImageUrl(String value) {
        mContentValues.put(TweetsColumns.USER_IMAGE_URL, value);
        return this;
    }

    public TweetsContentValues putUserImageUrlNull() {
        mContentValues.putNull(TweetsColumns.USER_IMAGE_URL);
        return this;
    }


    public TweetsContentValues putScreenName(String value) {
        mContentValues.put(TweetsColumns.SCREEN_NAME, value);
        return this;
    }

    public TweetsContentValues putScreenNameNull() {
        mContentValues.putNull(TweetsColumns.SCREEN_NAME);
        return this;
    }


    public TweetsContentValues putText(String value) {
        mContentValues.put(TweetsColumns.TEXT, value);
        return this;
    }

    public TweetsContentValues putTextNull() {
        mContentValues.putNull(TweetsColumns.TEXT);
        return this;
    }


    public static ContentValues[] getContentValues(List<TweetsModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(TweetsModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(TweetsModel item){
        TweetsContentValues values = new TweetsContentValues();
        values.putTwitterHandle(item.twitterHandle);
        values.putTweetId(item.tweetId);
        values.putCreatedAt(item.createdAt);
        values.putUserDescription(item.userDescription);
        values.putUserName(item.userName);
        values.putUserImageUrl(item.userImageUrl);
        values.putScreenName(item.screenName);
        values.putText(item.text);
        return values.values();
    }
}
