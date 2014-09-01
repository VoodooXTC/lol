package joss.jacobo.lol.lcs.provider.replays;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code replays} table.
 */
public class ReplaysSelection extends AbstractSelection<ReplaysSelection> {
    @Override
    public Uri uri() {
        return ReplaysColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code ReplaysCursor} object, which is positioned before the first entry, or null.
     */
    public ReplaysCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new ReplaysCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public ReplaysCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public ReplaysCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public ReplaysSelection id(long... value) {
        addEquals(ReplaysColumns._ID, toObjectArray(value));
        return this;
    }

    public ReplaysSelection kind(String... value) {
        addEquals(ReplaysColumns.KIND, value);
        return this;
    }
    
    public ReplaysSelection kindNot(String... value) {
        addNotEquals(ReplaysColumns.KIND, value);
        return this;
    }


    public ReplaysSelection etag(String... value) {
        addEquals(ReplaysColumns.ETAG, value);
        return this;
    }
    
    public ReplaysSelection etagNot(String... value) {
        addNotEquals(ReplaysColumns.ETAG, value);
        return this;
    }


    public ReplaysSelection youtubeId(String... value) {
        addEquals(ReplaysColumns.YOUTUBE_ID, value);
        return this;
    }
    
    public ReplaysSelection youtubeIdNot(String... value) {
        addNotEquals(ReplaysColumns.YOUTUBE_ID, value);
        return this;
    }


    public ReplaysSelection publishedAt(String... value) {
        addEquals(ReplaysColumns.PUBLISHED_AT, value);
        return this;
    }
    
    public ReplaysSelection publishedAtNot(String... value) {
        addNotEquals(ReplaysColumns.PUBLISHED_AT, value);
        return this;
    }


    public ReplaysSelection channelId(String... value) {
        addEquals(ReplaysColumns.CHANNEL_ID, value);
        return this;
    }
    
    public ReplaysSelection channelIdNot(String... value) {
        addNotEquals(ReplaysColumns.CHANNEL_ID, value);
        return this;
    }


    public ReplaysSelection title(String... value) {
        addEquals(ReplaysColumns.TITLE, value);
        return this;
    }
    
    public ReplaysSelection titleNot(String... value) {
        addNotEquals(ReplaysColumns.TITLE, value);
        return this;
    }


    public ReplaysSelection description(String... value) {
        addEquals(ReplaysColumns.DESCRIPTION, value);
        return this;
    }
    
    public ReplaysSelection descriptionNot(String... value) {
        addNotEquals(ReplaysColumns.DESCRIPTION, value);
        return this;
    }


    public ReplaysSelection thumbnails(String... value) {
        addEquals(ReplaysColumns.THUMBNAILS, value);
        return this;
    }
    
    public ReplaysSelection thumbnailsNot(String... value) {
        addNotEquals(ReplaysColumns.THUMBNAILS, value);
        return this;
    }


    public ReplaysSelection channelTitle(String... value) {
        addEquals(ReplaysColumns.CHANNEL_TITLE, value);
        return this;
    }
    
    public ReplaysSelection channelTitleNot(String... value) {
        addNotEquals(ReplaysColumns.CHANNEL_TITLE, value);
        return this;
    }


    public ReplaysSelection type(String... value) {
        addEquals(ReplaysColumns.TYPE, value);
        return this;
    }
    
    public ReplaysSelection typeNot(String... value) {
        addNotEquals(ReplaysColumns.TYPE, value);
        return this;
    }


    public ReplaysSelection duration(String... value) {
        addEquals(ReplaysColumns.DURATION, value);
        return this;
    }
    
    public ReplaysSelection durationNot(String... value) {
        addNotEquals(ReplaysColumns.DURATION, value);
        return this;
    }


    public ReplaysSelection dimension(String... value) {
        addEquals(ReplaysColumns.DIMENSION, value);
        return this;
    }
    
    public ReplaysSelection dimensionNot(String... value) {
        addNotEquals(ReplaysColumns.DIMENSION, value);
        return this;
    }


    public ReplaysSelection definition(String... value) {
        addEquals(ReplaysColumns.DEFINITION, value);
        return this;
    }
    
    public ReplaysSelection definitionNot(String... value) {
        addNotEquals(ReplaysColumns.DEFINITION, value);
        return this;
    }


    public ReplaysSelection caption(String... value) {
        addEquals(ReplaysColumns.CAPTION, value);
        return this;
    }
    
    public ReplaysSelection captionNot(String... value) {
        addNotEquals(ReplaysColumns.CAPTION, value);
        return this;
    }


    public ReplaysSelection licensedcontent(Boolean... value) {
        addEquals(ReplaysColumns.LICENSEDCONTENT, value);
        return this;
    }
    
    public ReplaysSelection licensedcontentNot(Boolean... value) {
        addNotEquals(ReplaysColumns.LICENSEDCONTENT, value);
        return this;
    }


    public ReplaysSelection viewcount(Integer... value) {
        addEquals(ReplaysColumns.VIEWCOUNT, value);
        return this;
    }
    
    public ReplaysSelection viewcountNot(Integer... value) {
        addNotEquals(ReplaysColumns.VIEWCOUNT, value);
        return this;
    }

    public ReplaysSelection viewcountGt(int value) {
        addGreaterThan(ReplaysColumns.VIEWCOUNT, value);
        return this;
    }

    public ReplaysSelection viewcountGtEq(int value) {
        addGreaterThanOrEquals(ReplaysColumns.VIEWCOUNT, value);
        return this;
    }

    public ReplaysSelection viewcountLt(int value) {
        addLessThan(ReplaysColumns.VIEWCOUNT, value);
        return this;
    }

    public ReplaysSelection viewcountLtEq(int value) {
        addLessThanOrEquals(ReplaysColumns.VIEWCOUNT, value);
        return this;
    }

    public ReplaysSelection likecount(Integer... value) {
        addEquals(ReplaysColumns.LIKECOUNT, value);
        return this;
    }
    
    public ReplaysSelection likecountNot(Integer... value) {
        addNotEquals(ReplaysColumns.LIKECOUNT, value);
        return this;
    }

    public ReplaysSelection likecountGt(int value) {
        addGreaterThan(ReplaysColumns.LIKECOUNT, value);
        return this;
    }

    public ReplaysSelection likecountGtEq(int value) {
        addGreaterThanOrEquals(ReplaysColumns.LIKECOUNT, value);
        return this;
    }

    public ReplaysSelection likecountLt(int value) {
        addLessThan(ReplaysColumns.LIKECOUNT, value);
        return this;
    }

    public ReplaysSelection likecountLtEq(int value) {
        addLessThanOrEquals(ReplaysColumns.LIKECOUNT, value);
        return this;
    }

    public ReplaysSelection dislikecount(Integer... value) {
        addEquals(ReplaysColumns.DISLIKECOUNT, value);
        return this;
    }
    
    public ReplaysSelection dislikecountNot(Integer... value) {
        addNotEquals(ReplaysColumns.DISLIKECOUNT, value);
        return this;
    }

    public ReplaysSelection dislikecountGt(int value) {
        addGreaterThan(ReplaysColumns.DISLIKECOUNT, value);
        return this;
    }

    public ReplaysSelection dislikecountGtEq(int value) {
        addGreaterThanOrEquals(ReplaysColumns.DISLIKECOUNT, value);
        return this;
    }

    public ReplaysSelection dislikecountLt(int value) {
        addLessThan(ReplaysColumns.DISLIKECOUNT, value);
        return this;
    }

    public ReplaysSelection dislikecountLtEq(int value) {
        addLessThanOrEquals(ReplaysColumns.DISLIKECOUNT, value);
        return this;
    }

    public ReplaysSelection favoritecount(Integer... value) {
        addEquals(ReplaysColumns.FAVORITECOUNT, value);
        return this;
    }
    
    public ReplaysSelection favoritecountNot(Integer... value) {
        addNotEquals(ReplaysColumns.FAVORITECOUNT, value);
        return this;
    }

    public ReplaysSelection favoritecountGt(int value) {
        addGreaterThan(ReplaysColumns.FAVORITECOUNT, value);
        return this;
    }

    public ReplaysSelection favoritecountGtEq(int value) {
        addGreaterThanOrEquals(ReplaysColumns.FAVORITECOUNT, value);
        return this;
    }

    public ReplaysSelection favoritecountLt(int value) {
        addLessThan(ReplaysColumns.FAVORITECOUNT, value);
        return this;
    }

    public ReplaysSelection favoritecountLtEq(int value) {
        addLessThanOrEquals(ReplaysColumns.FAVORITECOUNT, value);
        return this;
    }

    public ReplaysSelection commentcount(Integer... value) {
        addEquals(ReplaysColumns.COMMENTCOUNT, value);
        return this;
    }
    
    public ReplaysSelection commentcountNot(Integer... value) {
        addNotEquals(ReplaysColumns.COMMENTCOUNT, value);
        return this;
    }

    public ReplaysSelection commentcountGt(int value) {
        addGreaterThan(ReplaysColumns.COMMENTCOUNT, value);
        return this;
    }

    public ReplaysSelection commentcountGtEq(int value) {
        addGreaterThanOrEquals(ReplaysColumns.COMMENTCOUNT, value);
        return this;
    }

    public ReplaysSelection commentcountLt(int value) {
        addLessThan(ReplaysColumns.COMMENTCOUNT, value);
        return this;
    }

    public ReplaysSelection commentcountLtEq(int value) {
        addLessThanOrEquals(ReplaysColumns.COMMENTCOUNT, value);
        return this;
    }
}
