package joss.jacobo.lol.lcs.provider.teams;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code teams} table.
 */
public class TeamsSelection extends AbstractSelection<TeamsSelection> {
    @Override
    public Uri uri() {
        return TeamsColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code TeamsCursor} object, which is positioned before the first entry, or null.
     */
    public TeamsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new TeamsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public TeamsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public TeamsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public TeamsSelection id(long... value) {
        addEquals(TeamsColumns._ID, toObjectArray(value));
        return this;
    }

    public TeamsSelection teamId(Integer... value) {
        addEquals(TeamsColumns.TEAM_ID, value);
        return this;
    }
    
    public TeamsSelection teamIdNot(Integer... value) {
        addNotEquals(TeamsColumns.TEAM_ID, value);
        return this;
    }

    public TeamsSelection teamIdGt(int value) {
        addGreaterThan(TeamsColumns.TEAM_ID, value);
        return this;
    }

    public TeamsSelection teamIdGtEq(int value) {
        addGreaterThanOrEquals(TeamsColumns.TEAM_ID, value);
        return this;
    }

    public TeamsSelection teamIdLt(int value) {
        addLessThan(TeamsColumns.TEAM_ID, value);
        return this;
    }

    public TeamsSelection teamIdLtEq(int value) {
        addLessThanOrEquals(TeamsColumns.TEAM_ID, value);
        return this;
    }

    public TeamsSelection teamAbrev(String... value) {
        addEquals(TeamsColumns.TEAM_ABREV, value);
        return this;
    }
    
    public TeamsSelection teamAbrevNot(String... value) {
        addNotEquals(TeamsColumns.TEAM_ABREV, value);
        return this;
    }


    public TeamsSelection tournamentId(Integer... value) {
        addEquals(TeamsColumns.TOURNAMENT_ID, value);
        return this;
    }
    
    public TeamsSelection tournamentIdNot(Integer... value) {
        addNotEquals(TeamsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TeamsSelection tournamentIdGt(int value) {
        addGreaterThan(TeamsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TeamsSelection tournamentIdGtEq(int value) {
        addGreaterThanOrEquals(TeamsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TeamsSelection tournamentIdLt(int value) {
        addLessThan(TeamsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TeamsSelection tournamentIdLtEq(int value) {
        addLessThanOrEquals(TeamsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TeamsSelection tournamentAbrev(String... value) {
        addEquals(TeamsColumns.TOURNAMENT_ABREV, value);
        return this;
    }
    
    public TeamsSelection tournamentAbrevNot(String... value) {
        addNotEquals(TeamsColumns.TOURNAMENT_ABREV, value);
        return this;
    }


    public TeamsSelection teamName(String... value) {
        addEquals(TeamsColumns.TEAM_NAME, value);
        return this;
    }
    
    public TeamsSelection teamNameNot(String... value) {
        addNotEquals(TeamsColumns.TEAM_NAME, value);
        return this;
    }

}
