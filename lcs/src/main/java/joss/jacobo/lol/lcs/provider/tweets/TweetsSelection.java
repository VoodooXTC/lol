package joss.jacobo.lol.lcs.provider.tweets;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code tweets} table.
 */
public class TweetsSelection extends AbstractSelection<TweetsSelection> {
    @Override
    public Uri uri() {
        return TweetsColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code TweetsCursor} object, which is positioned before the first entry, or null.
     */
    public TweetsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new TweetsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public TweetsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public TweetsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public TweetsSelection id(long... value) {
        addEquals(TweetsColumns._ID, toObjectArray(value));
        return this;
    }

    public TweetsSelection twitterHandle(String... value) {
        addEquals(TweetsColumns.TWITTER_HANDLE, value);
        return this;
    }
    
    public TweetsSelection twitterHandleNot(String... value) {
        addNotEquals(TweetsColumns.TWITTER_HANDLE, value);
        return this;
    }


    public TweetsSelection createdAt(Long... value) {
        addEquals(TweetsColumns.CREATED_AT, value);
        return this;
    }
    
    public TweetsSelection createdAtNot(Long... value) {
        addNotEquals(TweetsColumns.CREATED_AT, value);
        return this;
    }

    public TweetsSelection createdAtGt(long value) {
        addGreaterThan(TweetsColumns.CREATED_AT, value);
        return this;
    }

    public TweetsSelection createdAtGtEq(long value) {
        addGreaterThanOrEquals(TweetsColumns.CREATED_AT, value);
        return this;
    }

    public TweetsSelection createdAtLt(long value) {
        addLessThan(TweetsColumns.CREATED_AT, value);
        return this;
    }

    public TweetsSelection createdAtLtEq(long value) {
        addLessThanOrEquals(TweetsColumns.CREATED_AT, value);
        return this;
    }

    public TweetsSelection userDescription(String... value) {
        addEquals(TweetsColumns.USER_DESCRIPTION, value);
        return this;
    }
    
    public TweetsSelection userDescriptionNot(String... value) {
        addNotEquals(TweetsColumns.USER_DESCRIPTION, value);
        return this;
    }


    public TweetsSelection userName(String... value) {
        addEquals(TweetsColumns.USER_NAME, value);
        return this;
    }
    
    public TweetsSelection userNameNot(String... value) {
        addNotEquals(TweetsColumns.USER_NAME, value);
        return this;
    }


    public TweetsSelection userImageUrl(String... value) {
        addEquals(TweetsColumns.USER_IMAGE_URL, value);
        return this;
    }
    
    public TweetsSelection userImageUrlNot(String... value) {
        addNotEquals(TweetsColumns.USER_IMAGE_URL, value);
        return this;
    }


    public TweetsSelection screenName(String... value) {
        addEquals(TweetsColumns.SCREEN_NAME, value);
        return this;
    }
    
    public TweetsSelection screenNameNot(String... value) {
        addNotEquals(TweetsColumns.SCREEN_NAME, value);
        return this;
    }


    public TweetsSelection text(String... value) {
        addEquals(TweetsColumns.TEXT, value);
        return this;
    }
    
    public TweetsSelection textNot(String... value) {
        addNotEquals(TweetsColumns.TEXT, value);
        return this;
    }

}
