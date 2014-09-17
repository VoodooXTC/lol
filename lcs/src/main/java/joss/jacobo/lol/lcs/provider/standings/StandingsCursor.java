package joss.jacobo.lol.lcs.provider.standings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.items.StandingsItem;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code standings} table.
 */
public class StandingsCursor extends AbstractCursor {
    public StandingsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code standing_id} value.
     * Can be {@code null}.
     */
    public Integer getStandingId() {
        return getIntegerOrNull(StandingsColumns.STANDING_ID);
    }

    /**
     * Get the {@code tournament_abrev} value.
     * Can be {@code null}.
     */
    public String getTournamentAbrev() {
        Integer index = getCachedColumnIndexOrThrow(StandingsColumns.TOURNAMENT_ABREV);
        return getString(index);
    }

    /**
     * Get the {@code standing_week} value.
     * Can be {@code null}.
     */
    public Integer getStandingWeek() {
        return getIntegerOrNull(StandingsColumns.STANDING_WEEK);
    }

    /**
     * Get the {@code team_name} value.
     * Can be {@code null}.
     */
    public String getTeamName() {
        Integer index = getCachedColumnIndexOrThrow(StandingsColumns.TEAM_NAME);
        return getString(index);
    }

    /**
     * Get the {@code team_abrev} value.
     * Can be {@code null}.
     */
    public String getTeamAbrev() {
        Integer index = getCachedColumnIndexOrThrow(StandingsColumns.TEAM_ABREV);
        return getString(index);
    }

    /**
     * Get the {@code wins} value.
     * Can be {@code null}.
     */
    public Integer getWins() {
        return getIntegerOrNull(StandingsColumns.WINS);
    }

    /**
     * Get the {@code losses} value.
     * Can be {@code null}.
     */
    public Integer getLosses() {
        return getIntegerOrNull(StandingsColumns.LOSSES);
    }

    /**
     * Get the {@code delta} value.
     * Can be {@code null}.
     */
    public Integer getDelta() {
        return getIntegerOrNull(StandingsColumns.DELTA);
    }

    /**
     * Get the {@code standing_position} value.
     * Can be {@code null}.
     */
    public Integer getStandingPosition() {
        return getIntegerOrNull(StandingsColumns.STANDING_POSITION);
    }

    /**
     * Get the {@code tournament_group} value.
     * Can be {@code null}.
     */
    public String getTournamentGroup() {
        Integer index = getCachedColumnIndexOrThrow(StandingsColumns.TOURNAMENT_GROUP);
        return getString(index);
    }

    public List<StandingsItem> getListAsStandingItems() {
        List<StandingsItem> items = new ArrayList<StandingsItem>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new StandingsItem(this));
                moveToNext();
            }
        }
        return items;
    }

    public List<StandingsItem> getListAsStandingItemsTop3() {
        int counter = 0;
        List<StandingsItem> items = new ArrayList<StandingsItem>();
        if(moveToFirst()){
            while(!isAfterLast() && counter < 3){
                items.add(new StandingsItem(this));
                moveToNext();
                counter++;
            }
        }
        return items;
    }
}
