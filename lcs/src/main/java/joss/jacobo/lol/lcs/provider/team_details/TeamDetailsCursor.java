package joss.jacobo.lol.lcs.provider.team_details;

import java.util.Date;

import android.database.Cursor;

import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code team_details} table.
 */
public class TeamDetailsCursor extends AbstractCursor {
    public TeamDetailsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code team_id} value.
     * Can be {@code null}.
     */
    public Integer getTeamId() {
        return getIntegerOrNull(TeamDetailsColumns.TEAM_ID);
    }

    /**
     * Get the {@code abrev} value.
     * Can be {@code null}.
     */
    public String getAbrev() {
        Integer index = getCachedColumnIndexOrThrow(TeamDetailsColumns.ABREV);
        return getString(index);
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(TeamDetailsColumns.NAME);
        return getString(index);
    }

    /**
     * Get the {@code logo} value.
     * Can be {@code null}.
     */
    public String getLogo() {
        Integer index = getCachedColumnIndexOrThrow(TeamDetailsColumns.LOGO);
        return getString(index);
    }

    /**
     * Get the {@code team_picture} value.
     * Can be {@code null}.
     */
    public String getTeamPicture() {
        Integer index = getCachedColumnIndexOrThrow(TeamDetailsColumns.TEAM_PICTURE);
        return getString(index);
    }

    /**
     * Get the {@code about_text} value.
     * Can be {@code null}.
     */
    public String getAboutText() {
        Integer index = getCachedColumnIndexOrThrow(TeamDetailsColumns.ABOUT_TEXT);
        return getString(index);
    }

    /**
     * Get the {@code twitter_handle} value.
     * Can be {@code null}.
     */
    public String getTwitterHandle() {
        Integer index = getCachedColumnIndexOrThrow(TeamDetailsColumns.TWITTER_HANDLE);
        return getString(index);
    }
}
