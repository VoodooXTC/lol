package joss.jacobo.lol.lcs.provider.replays;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code replays} table.
 */
public interface ReplaysColumns extends BaseColumns {
    String TABLE_NAME = "replays";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String KIND = "kind";
    String ETAG = "etag";
    String YOUTUBE_ID = "youtube_id";
    String PUBLISHED_AT = "published_at";
    String CHANNEL_ID = "channel_id";
    String TITLE = "title";
    String DESCRIPTION = "description";
    String THUMBNAILS = "thumbnails";
    String CHANNEL_TITLE = "channel_title";
    String TYPE = "type";
    String DURATION = "duration";
    String DIMENSION = "dimension";
    String DEFINITION = "definition";
    String CAPTION = "caption";
    String LICENSEDCONTENT = "licensedcontent";
    String VIEWCOUNT = "viewcount";
    String LIKECOUNT = "likecount";
    String DISLIKECOUNT = "dislikecount";
    String FAVORITECOUNT = "favoritecount";
    String COMMENTCOUNT = "commentcount";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            KIND,
            ETAG,
            YOUTUBE_ID,
            PUBLISHED_AT,
            CHANNEL_ID,
            TITLE,
            DESCRIPTION,
            THUMBNAILS,
            CHANNEL_TITLE,
            TYPE,
            DURATION,
            DIMENSION,
            DEFINITION,
            CAPTION,
            LICENSEDCONTENT,
            VIEWCOUNT,
            LIKECOUNT,
            DISLIKECOUNT,
            FAVORITECOUNT,
            COMMENTCOUNT
    };
    // @formatter:on
}