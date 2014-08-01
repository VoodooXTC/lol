package joss.jacobo.lol.lcs.provider.news;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code news} table.
 */
public class NewsSelection extends AbstractSelection<NewsSelection> {
    @Override
    public Uri uri() {
        return NewsColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code NewsCursor} object, which is positioned before the first entry, or null.
     */
    public NewsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new NewsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public NewsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public NewsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public NewsSelection id(long... value) {
        addEquals(NewsColumns._ID, toObjectArray(value));
        return this;
    }

    public NewsSelection newsId(Integer... value) {
        addEquals(NewsColumns.NEWS_ID, value);
        return this;
    }
    
    public NewsSelection newsIdNot(Integer... value) {
        addNotEquals(NewsColumns.NEWS_ID, value);
        return this;
    }

    public NewsSelection newsIdGt(int value) {
        addGreaterThan(NewsColumns.NEWS_ID, value);
        return this;
    }

    public NewsSelection newsIdGtEq(int value) {
        addGreaterThanOrEquals(NewsColumns.NEWS_ID, value);
        return this;
    }

    public NewsSelection newsIdLt(int value) {
        addLessThan(NewsColumns.NEWS_ID, value);
        return this;
    }

    public NewsSelection newsIdLtEq(int value) {
        addLessThanOrEquals(NewsColumns.NEWS_ID, value);
        return this;
    }

    public NewsSelection category(String... value) {
        addEquals(NewsColumns.CATEGORY, value);
        return this;
    }
    
    public NewsSelection categoryNot(String... value) {
        addNotEquals(NewsColumns.CATEGORY, value);
        return this;
    }


    public NewsSelection title(String... value) {
        addEquals(NewsColumns.TITLE, value);
        return this;
    }
    
    public NewsSelection titleNot(String... value) {
        addNotEquals(NewsColumns.TITLE, value);
        return this;
    }


    public NewsSelection link(String... value) {
        addEquals(NewsColumns.LINK, value);
        return this;
    }
    
    public NewsSelection linkNot(String... value) {
        addNotEquals(NewsColumns.LINK, value);
        return this;
    }


    public NewsSelection image(String... value) {
        addEquals(NewsColumns.IMAGE, value);
        return this;
    }
    
    public NewsSelection imageNot(String... value) {
        addNotEquals(NewsColumns.IMAGE, value);
        return this;
    }


    public NewsSelection author(String... value) {
        addEquals(NewsColumns.AUTHOR, value);
        return this;
    }
    
    public NewsSelection authorNot(String... value) {
        addNotEquals(NewsColumns.AUTHOR, value);
        return this;
    }


    public NewsSelection description(String... value) {
        addEquals(NewsColumns.DESCRIPTION, value);
        return this;
    }
    
    public NewsSelection descriptionNot(String... value) {
        addNotEquals(NewsColumns.DESCRIPTION, value);
        return this;
    }


    public NewsSelection content(String... value) {
        addEquals(NewsColumns.CONTENT, value);
        return this;
    }
    
    public NewsSelection contentNot(String... value) {
        addNotEquals(NewsColumns.CONTENT, value);
        return this;
    }


    public NewsSelection lastupdated(Integer... value) {
        addEquals(NewsColumns.LASTUPDATED, value);
        return this;
    }
    
    public NewsSelection lastupdatedNot(Integer... value) {
        addNotEquals(NewsColumns.LASTUPDATED, value);
        return this;
    }

    public NewsSelection lastupdatedGt(int value) {
        addGreaterThan(NewsColumns.LASTUPDATED, value);
        return this;
    }

    public NewsSelection lastupdatedGtEq(int value) {
        addGreaterThanOrEquals(NewsColumns.LASTUPDATED, value);
        return this;
    }

    public NewsSelection lastupdatedLt(int value) {
        addLessThan(NewsColumns.LASTUPDATED, value);
        return this;
    }

    public NewsSelection lastupdatedLtEq(int value) {
        addLessThanOrEquals(NewsColumns.LASTUPDATED, value);
        return this;
    }
}
