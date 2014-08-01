package joss.jacobo.lol.lcs.provider.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.NewsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code news} table.
 */
public class NewsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return NewsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, NewsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public NewsContentValues putNewsId(Integer value) {
        mContentValues.put(NewsColumns.NEWS_ID, value);
        return this;
    }

    public NewsContentValues putNewsIdNull() {
        mContentValues.putNull(NewsColumns.NEWS_ID);
        return this;
    }


    public NewsContentValues putCategory(String value) {
        mContentValues.put(NewsColumns.CATEGORY, value);
        return this;
    }

    public NewsContentValues putCategoryNull() {
        mContentValues.putNull(NewsColumns.CATEGORY);
        return this;
    }


    public NewsContentValues putTitle(String value) {
        mContentValues.put(NewsColumns.TITLE, value);
        return this;
    }

    public NewsContentValues putTitleNull() {
        mContentValues.putNull(NewsColumns.TITLE);
        return this;
    }


    public NewsContentValues putLink(String value) {
        mContentValues.put(NewsColumns.LINK, value);
        return this;
    }

    public NewsContentValues putLinkNull() {
        mContentValues.putNull(NewsColumns.LINK);
        return this;
    }


    public NewsContentValues putImage(String value) {
        mContentValues.put(NewsColumns.IMAGE, value);
        return this;
    }

    public NewsContentValues putImageNull() {
        mContentValues.putNull(NewsColumns.IMAGE);
        return this;
    }


    public NewsContentValues putAuthor(String value) {
        mContentValues.put(NewsColumns.AUTHOR, value);
        return this;
    }

    public NewsContentValues putAuthorNull() {
        mContentValues.putNull(NewsColumns.AUTHOR);
        return this;
    }


    public NewsContentValues putDescription(String value) {
        mContentValues.put(NewsColumns.DESCRIPTION, value);
        return this;
    }

    public NewsContentValues putDescriptionNull() {
        mContentValues.putNull(NewsColumns.DESCRIPTION);
        return this;
    }


    public NewsContentValues putContent(String value) {
        mContentValues.put(NewsColumns.CONTENT, value);
        return this;
    }

    public NewsContentValues putContentNull() {
        mContentValues.putNull(NewsColumns.CONTENT);
        return this;
    }


    public NewsContentValues putLastupdated(Integer value) {
        mContentValues.put(NewsColumns.LASTUPDATED, value);
        return this;
    }

    public NewsContentValues putLastupdatedNull() {
        mContentValues.putNull(NewsColumns.LASTUPDATED);
        return this;
    }


    public static ContentValues[] getContentValues(List<NewsModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(NewsModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(NewsModel item){
        NewsContentValues values = new NewsContentValues();
        values.putNewsId(item.newsId);
        values.putCategory(item.category);
        values.putTitle(item.title);
        values.putLink(item.link);
        values.putImage(item.image);
        values.putAuthor(item.author);
        values.putDescription(item.description);
        values.putContent(item.content);
        values.putLastupdated(item.lastupdated);
        return values.values();
    }
}
