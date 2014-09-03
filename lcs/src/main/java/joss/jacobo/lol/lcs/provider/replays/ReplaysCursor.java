package joss.jacobo.lol.lcs.provider.replays;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.api.model.Replays.Replay;
import joss.jacobo.lol.lcs.api.model.Replays.Replays;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code replays} table.
 */
public class ReplaysCursor extends AbstractCursor {
    public ReplaysCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code kind} value.
     * Can be {@code null}.
     */
    public String getKind() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.KIND);
        return getString(index);
    }

    /**
     * Get the {@code etag} value.
     * Can be {@code null}.
     */
    public String getEtag() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.ETAG);
        return getString(index);
    }

    /**
     * Get the {@code youtube_id} value.
     * Can be {@code null}.
     */
    public String getYoutubeId() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.YOUTUBE_ID);
        return getString(index);
    }

    /**
     * Get the {@code published_at} value.
     * Can be {@code null}.
     */
    public String getPublishedAt() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.PUBLISHED_AT);
        return getString(index);
    }

    /**
     * Get the {@code channel_id} value.
     * Can be {@code null}.
     */
    public String getChannelId() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.CHANNEL_ID);
        return getString(index);
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    public String getTitle() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.TITLE);
        return getString(index);
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    public String getDescription() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.DESCRIPTION);
        return getString(index);
    }

    /**
     * Get the {@code thumbnails} value.
     * Can be {@code null}.
     */
    public String getThumbnails() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.THUMBNAILS);
        return getString(index);
    }

    /**
     * Get the {@code channel_title} value.
     * Can be {@code null}.
     */
    public String getChannelTitle() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.CHANNEL_TITLE);
        return getString(index);
    }

    /**
     * Get the {@code type} value.
     * Can be {@code null}.
     */
    public String getType() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.TYPE);
        return getString(index);
    }

    /**
     * Get the {@code duration} value.
     * Can be {@code null}.
     */
    public String getDuration() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.DURATION);
        return getString(index);
    }

    /**
     * Get the {@code dimension} value.
     * Can be {@code null}.
     */
    public String getDimension() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.DIMENSION);
        return getString(index);
    }

    /**
     * Get the {@code definition} value.
     * Can be {@code null}.
     */
    public String getDefinition() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.DEFINITION);
        return getString(index);
    }

    /**
     * Get the {@code caption} value.
     * Can be {@code null}.
     */
    public String getCaption() {
        Integer index = getCachedColumnIndexOrThrow(ReplaysColumns.CAPTION);
        return getString(index);
    }

    /**
     * Get the {@code licensedcontent} value.
     * Can be {@code null}.
     */
    public Boolean getLicensedcontent() {
        return getBoolean(ReplaysColumns.LICENSEDCONTENT);
    }

    /**
     * Get the {@code viewcount} value.
     * Can be {@code null}.
     */
    public Integer getViewcount() {
        return getIntegerOrNull(ReplaysColumns.VIEWCOUNT);
    }

    /**
     * Get the {@code likecount} value.
     * Can be {@code null}.
     */
    public Integer getLikecount() {
        return getIntegerOrNull(ReplaysColumns.LIKECOUNT);
    }

    /**
     * Get the {@code dislikecount} value.
     * Can be {@code null}.
     */
    public Integer getDislikecount() {
        return getIntegerOrNull(ReplaysColumns.DISLIKECOUNT);
    }

    /**
     * Get the {@code favoritecount} value.
     * Can be {@code null}.
     */
    public Integer getFavoritecount() {
        return getIntegerOrNull(ReplaysColumns.FAVORITECOUNT);
    }

    /**
     * Get the {@code commentcount} value.
     * Can be {@code null}.
     */
    public Integer getCommentcount() {
        return getIntegerOrNull(ReplaysColumns.COMMENTCOUNT);
    }

    public List<Replay> getList() {
        List<Replay> items = new ArrayList<Replay>();
        if(moveToFirst()){
            while (!isAfterLast()){
                items.add(new Replay(this));
                moveToNext();
            }
        }
        return items;
    }
}
