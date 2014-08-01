package joss.jacobo.lol.lcs.provider.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.model.NewsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code news} table.
 */
public class NewsCursor extends AbstractCursor {
    public NewsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code news_id} value.
     * Can be {@code null}.
     */
    public Integer getNewsId() {
        return getIntegerOrNull(NewsColumns.NEWS_ID);
    }

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    public String getCategory() {
        Integer index = getCachedColumnIndexOrThrow(NewsColumns.CATEGORY);
        return getString(index);
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    public String getTitle() {
        Integer index = getCachedColumnIndexOrThrow(NewsColumns.TITLE);
        return getString(index);
    }

    /**
     * Get the {@code link} value.
     * Can be {@code null}.
     */
    public String getLink() {
        Integer index = getCachedColumnIndexOrThrow(NewsColumns.LINK);
        return getString(index);
    }

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    public String getImage() {
        Integer index = getCachedColumnIndexOrThrow(NewsColumns.IMAGE);
        return getString(index);
    }

    /**
     * Get the {@code author} value.
     * Can be {@code null}.
     */
    public String getAuthor() {
        Integer index = getCachedColumnIndexOrThrow(NewsColumns.AUTHOR);
        return getString(index);
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    public String getDescription() {
        Integer index = getCachedColumnIndexOrThrow(NewsColumns.DESCRIPTION);
        return getString(index);
    }

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    public String getContent() {
        Integer index = getCachedColumnIndexOrThrow(NewsColumns.CONTENT);
        return getString(index);
    }

    /**
     * Get the {@code lastupdated} value.
     * Can be {@code null}.
     */
    public Integer getLastupdated() {
        return getIntegerOrNull(NewsColumns.LASTUPDATED);
    }

    public List<NewsModel> getList() {
        List<NewsModel> items = new ArrayList<NewsModel>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new NewsModel(this));
                moveToNext();
            }
        }
        return items;
    }
}
