package joss.jacobo.lol.lcs.provider.tweets;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code tweets} table.
 */
public interface TweetsColumns extends BaseColumns {
    String TABLE_NAME = "tweets";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String TWITTER_HANDLE = "twitter_handle";
    String TWEET_ID = "tweet_id";
    String CREATED_AT = "created_at";
    String USER_DESCRIPTION = "user_description";
    String USER_NAME = "user_name";
    String USER_IMAGE_URL = "user_image_url";
    String SCREEN_NAME = "screen_name";
    String TEXT = "text";

    String DEFAULT_ORDER = _ID;

    // @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            TWITTER_HANDLE,
            TWEET_ID,
            CREATED_AT,
            USER_DESCRIPTION,
            USER_NAME,
            USER_IMAGE_URL,
            SCREEN_NAME,
            TEXT
    };
    // @formatter:on
}