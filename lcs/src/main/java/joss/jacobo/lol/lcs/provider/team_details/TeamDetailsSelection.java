package joss.jacobo.lol.lcs.provider.team_details;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code team_details} table.
 */
public class TeamDetailsSelection extends AbstractSelection<TeamDetailsSelection> {
    @Override
    public Uri uri() {
        return TeamDetailsColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code TeamDetailsCursor} object, which is positioned before the first entry, or null.
     */
    public TeamDetailsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new TeamDetailsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public TeamDetailsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public TeamDetailsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public TeamDetailsSelection id(long... value) {
        addEquals(TeamDetailsColumns._ID, toObjectArray(value));
        return this;
    }

    public TeamDetailsSelection teamId(Integer... value) {
        addEquals(TeamDetailsColumns.TEAM_ID, value);
        return this;
    }
    
    public TeamDetailsSelection teamIdNot(Integer... value) {
        addNotEquals(TeamDetailsColumns.TEAM_ID, value);
        return this;
    }

    public TeamDetailsSelection teamIdGt(int value) {
        addGreaterThan(TeamDetailsColumns.TEAM_ID, value);
        return this;
    }

    public TeamDetailsSelection teamIdGtEq(int value) {
        addGreaterThanOrEquals(TeamDetailsColumns.TEAM_ID, value);
        return this;
    }

    public TeamDetailsSelection teamIdLt(int value) {
        addLessThan(TeamDetailsColumns.TEAM_ID, value);
        return this;
    }

    public TeamDetailsSelection teamIdLtEq(int value) {
        addLessThanOrEquals(TeamDetailsColumns.TEAM_ID, value);
        return this;
    }

    public TeamDetailsSelection abrev(String... value) {
        addEquals(TeamDetailsColumns.ABREV, value);
        return this;
    }
    
    public TeamDetailsSelection abrevNot(String... value) {
        addNotEquals(TeamDetailsColumns.ABREV, value);
        return this;
    }


    public TeamDetailsSelection name(String... value) {
        addEquals(TeamDetailsColumns.NAME, value);
        return this;
    }
    
    public TeamDetailsSelection nameNot(String... value) {
        addNotEquals(TeamDetailsColumns.NAME, value);
        return this;
    }


    public TeamDetailsSelection logo(String... value) {
        addEquals(TeamDetailsColumns.LOGO, value);
        return this;
    }
    
    public TeamDetailsSelection logoNot(String... value) {
        addNotEquals(TeamDetailsColumns.LOGO, value);
        return this;
    }


    public TeamDetailsSelection teamPicture(String... value) {
        addEquals(TeamDetailsColumns.TEAM_PICTURE, value);
        return this;
    }
    
    public TeamDetailsSelection teamPictureNot(String... value) {
        addNotEquals(TeamDetailsColumns.TEAM_PICTURE, value);
        return this;
    }


    public TeamDetailsSelection aboutText(String... value) {
        addEquals(TeamDetailsColumns.ABOUT_TEXT, value);
        return this;
    }
    
    public TeamDetailsSelection aboutTextNot(String... value) {
        addNotEquals(TeamDetailsColumns.ABOUT_TEXT, value);
        return this;
    }


    public TeamDetailsSelection twitterHandle(String... value) {
        addEquals(TeamDetailsColumns.TWITTER_HANDLE, value);
        return this;
    }
    
    public TeamDetailsSelection twitterHandleNot(String... value) {
        addNotEquals(TeamDetailsColumns.TWITTER_HANDLE, value);
        return this;
    }

    public static TeamDetailsModel getTeamDetails(ContentResolver contentResolver, Integer teamId) {
        TeamDetailsSelection where = new TeamDetailsSelection();
        where.teamId(teamId);
        TeamDetailsCursor cursor = where.query(contentResolver);
        if(cursor.moveToFirst()){
            return new TeamDetailsModel(cursor);
        }
        return null;
    }

}
