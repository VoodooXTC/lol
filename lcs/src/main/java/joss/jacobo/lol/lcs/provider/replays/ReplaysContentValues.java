package joss.jacobo.lol.lcs.provider.replays;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.ReplaysModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code replays} table.
 */
public class ReplaysContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ReplaysColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, ReplaysSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ReplaysContentValues putKind(String value) {
        mContentValues.put(ReplaysColumns.KIND, value);
        return this;
    }

    public ReplaysContentValues putKindNull() {
        mContentValues.putNull(ReplaysColumns.KIND);
        return this;
    }


    public ReplaysContentValues putEtag(String value) {
        mContentValues.put(ReplaysColumns.ETAG, value);
        return this;
    }

    public ReplaysContentValues putEtagNull() {
        mContentValues.putNull(ReplaysColumns.ETAG);
        return this;
    }


    public ReplaysContentValues putYoutubeId(String value) {
        mContentValues.put(ReplaysColumns.YOUTUBE_ID, value);
        return this;
    }

    public ReplaysContentValues putYoutubeIdNull() {
        mContentValues.putNull(ReplaysColumns.YOUTUBE_ID);
        return this;
    }


    public ReplaysContentValues putPublishedAt(String value) {
        mContentValues.put(ReplaysColumns.PUBLISHED_AT, value);
        return this;
    }

    public ReplaysContentValues putPublishedAtNull() {
        mContentValues.putNull(ReplaysColumns.PUBLISHED_AT);
        return this;
    }


    public ReplaysContentValues putChannelId(String value) {
        mContentValues.put(ReplaysColumns.CHANNEL_ID, value);
        return this;
    }

    public ReplaysContentValues putChannelIdNull() {
        mContentValues.putNull(ReplaysColumns.CHANNEL_ID);
        return this;
    }


    public ReplaysContentValues putTitle(String value) {
        mContentValues.put(ReplaysColumns.TITLE, value);
        return this;
    }

    public ReplaysContentValues putTitleNull() {
        mContentValues.putNull(ReplaysColumns.TITLE);
        return this;
    }


    public ReplaysContentValues putDescription(String value) {
        mContentValues.put(ReplaysColumns.DESCRIPTION, value);
        return this;
    }

    public ReplaysContentValues putDescriptionNull() {
        mContentValues.putNull(ReplaysColumns.DESCRIPTION);
        return this;
    }


    public ReplaysContentValues putThumbnails(String value) {
        mContentValues.put(ReplaysColumns.THUMBNAILS, value);
        return this;
    }

    public ReplaysContentValues putThumbnailsNull() {
        mContentValues.putNull(ReplaysColumns.THUMBNAILS);
        return this;
    }


    public ReplaysContentValues putChannelTitle(String value) {
        mContentValues.put(ReplaysColumns.CHANNEL_TITLE, value);
        return this;
    }

    public ReplaysContentValues putChannelTitleNull() {
        mContentValues.putNull(ReplaysColumns.CHANNEL_TITLE);
        return this;
    }


    public ReplaysContentValues putType(String value) {
        mContentValues.put(ReplaysColumns.TYPE, value);
        return this;
    }

    public ReplaysContentValues putTypeNull() {
        mContentValues.putNull(ReplaysColumns.TYPE);
        return this;
    }


    public ReplaysContentValues putDuration(String value) {
        mContentValues.put(ReplaysColumns.DURATION, value);
        return this;
    }

    public ReplaysContentValues putDurationNull() {
        mContentValues.putNull(ReplaysColumns.DURATION);
        return this;
    }


    public ReplaysContentValues putDimension(String value) {
        mContentValues.put(ReplaysColumns.DIMENSION, value);
        return this;
    }

    public ReplaysContentValues putDimensionNull() {
        mContentValues.putNull(ReplaysColumns.DIMENSION);
        return this;
    }


    public ReplaysContentValues putDefinition(String value) {
        mContentValues.put(ReplaysColumns.DEFINITION, value);
        return this;
    }

    public ReplaysContentValues putDefinitionNull() {
        mContentValues.putNull(ReplaysColumns.DEFINITION);
        return this;
    }


    public ReplaysContentValues putCaption(String value) {
        mContentValues.put(ReplaysColumns.CAPTION, value);
        return this;
    }

    public ReplaysContentValues putCaptionNull() {
        mContentValues.putNull(ReplaysColumns.CAPTION);
        return this;
    }


    public ReplaysContentValues putLicensedcontent(Boolean value) {
        mContentValues.put(ReplaysColumns.LICENSEDCONTENT, value);
        return this;
    }

    public ReplaysContentValues putLicensedcontentNull() {
        mContentValues.putNull(ReplaysColumns.LICENSEDCONTENT);
        return this;
    }


    public ReplaysContentValues putViewcount(Integer value) {
        mContentValues.put(ReplaysColumns.VIEWCOUNT, value);
        return this;
    }

    public ReplaysContentValues putViewcountNull() {
        mContentValues.putNull(ReplaysColumns.VIEWCOUNT);
        return this;
    }


    public ReplaysContentValues putLikecount(Integer value) {
        mContentValues.put(ReplaysColumns.LIKECOUNT, value);
        return this;
    }

    public ReplaysContentValues putLikecountNull() {
        mContentValues.putNull(ReplaysColumns.LIKECOUNT);
        return this;
    }


    public ReplaysContentValues putDislikecount(Integer value) {
        mContentValues.put(ReplaysColumns.DISLIKECOUNT, value);
        return this;
    }

    public ReplaysContentValues putDislikecountNull() {
        mContentValues.putNull(ReplaysColumns.DISLIKECOUNT);
        return this;
    }


    public ReplaysContentValues putFavoritecount(Integer value) {
        mContentValues.put(ReplaysColumns.FAVORITECOUNT, value);
        return this;
    }

    public ReplaysContentValues putFavoritecountNull() {
        mContentValues.putNull(ReplaysColumns.FAVORITECOUNT);
        return this;
    }


    public ReplaysContentValues putCommentcount(Integer value) {
        mContentValues.put(ReplaysColumns.COMMENTCOUNT, value);
        return this;
    }

    public ReplaysContentValues putCommentcountNull() {
        mContentValues.putNull(ReplaysColumns.COMMENTCOUNT);
        return this;
    }


    public static ContentValues[] getContentValues(List<ReplaysModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(ReplaysModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(ReplaysModel item){
        ReplaysContentValues values = new ReplaysContentValues();
        values.putKind(item.kind);
        values.putEtag(item.etag);
        values.putYoutubeId(item.youtubeId);
        values.putPublishedAt(item.publishedAt);
        values.putChannelId(item.channelId);
        values.putTitle(item.title);
        values.putDescription(item.description);
        values.putThumbnails(item.thumbnails);
        values.putChannelTitle(item.channelTitle);
        values.putType(item.type);
        values.putDuration(item.duration);
        values.putDimension(item.dimension);
        values.putDefinition(item.definition);
        values.putCaption(item.caption);
        values.putLicensedcontent(item.licensedcontent);
        values.putViewcount(item.viewcount);
        values.putLikecount(item.likecount);
        values.putDislikecount(item.dislikecount);
        values.putFavoritecount(item.favoritecount);
        values.putCommentcount(item.commentcount);
        return values.values();
    }
}
