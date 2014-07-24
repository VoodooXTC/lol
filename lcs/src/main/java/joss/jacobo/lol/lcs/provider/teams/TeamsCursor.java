package joss.jacobo.lol.lcs.provider.teams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.items.DrawerItem;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code teams} table.
 */
public class TeamsCursor extends AbstractCursor {
    public TeamsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code team_id} value.
     * Can be {@code null}.
     */
    public Integer getTeamId() {
        return getIntegerOrNull(TeamsColumns.TEAM_ID);
    }

    /**
     * Get the {@code team_abrev} value.
     * Can be {@code null}.
     */
    public String getTeamAbrev() {
        Integer index = getCachedColumnIndexOrThrow(TeamsColumns.TEAM_ABREV);
        return getString(index);
    }

    /**
     * Get the {@code tournament_id} value.
     * Can be {@code null}.
     */
    public Integer getTournamentId() {
        return getIntegerOrNull(TeamsColumns.TOURNAMENT_ID);
    }

    /**
     * Get the {@code tournament_abrev} value.
     * Can be {@code null}.
     */
    public String getTournamentAbrev() {
        Integer index = getCachedColumnIndexOrThrow(TeamsColumns.TOURNAMENT_ABREV);
        return getString(index);
    }

    /**
     * Get the {@code team_name} value.
     * Can be {@code null}.
     */
    public String getTeamName() {
        Integer index = getCachedColumnIndexOrThrow(TeamsColumns.TEAM_NAME);
        return getString(index);
    }

    public List<DrawerItem> getListAsDrawerItems() {
        List<DrawerItem> items = new ArrayList<DrawerItem>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new DrawerItem(this));
                moveToNext();
            }
        }
        return items;
    }
}
