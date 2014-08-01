package joss.jacobo.lol.lcs.provider.news;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code news} table.
 */
public interface NewsColumns extends BaseColumns {
    String TABLE_NAME = "news";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String NEWS_ID = "news_id";
    String CATEGORY = "category";
    String TITLE = "title";
    String LINK = "link";
    String IMAGE = "image";
    String AUTHOR = "author";
    String DESCRIPTION = "description";
    String CONTENT = "content";
    String LASTUPDATED = "lastupdated";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            NEWS_ID,
            CATEGORY,
            TITLE,
            LINK,
            IMAGE,
            AUTHOR,
            DESCRIPTION,
            CONTENT,
            LASTUPDATED
    };
    // @formatter:on
}