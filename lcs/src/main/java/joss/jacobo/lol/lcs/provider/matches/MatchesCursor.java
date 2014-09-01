package joss.jacobo.lol.lcs.provider.matches;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code matches} table.
 */
public class MatchesCursor extends AbstractCursor {
    public MatchesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code match_id} value.
     * Can be {@code null}.
     */
    public Integer getMatchId() {
        return getIntegerOrNull(MatchesColumns.MATCH_ID);
    }

    /**
     * Get the {@code tournament_id} value.
     * Can be {@code null}.
     */
    public Integer getTournamentId() {
        return getIntegerOrNull(MatchesColumns.TOURNAMENT_ID);
    }

    /**
     * Get the {@code tournament_abrev} value.
     * Can be {@code null}.
     */
    public String getTournamentAbrev() {
        Integer index = getCachedColumnIndexOrThrow(MatchesColumns.TOURNAMENT_ABREV);
        return getString(index);
    }

    /**
     * Get the {@code split} value.
     * Can be {@code null}.
     */
    public String getSplit() {
        Integer index = getCachedColumnIndexOrThrow(MatchesColumns.SPLIT);
        return getString(index);
    }

    /**
     * Get the {@code datetime} value.
     * Can be {@code null}.
     */
    public String getDatetime() {
        Integer index = getCachedColumnIndexOrThrow(MatchesColumns.DATETIME);
        return getString(index);
    }

    /**
     * Get the {@code week} value.
     * Can be {@code null}.
     */
    public Integer getWeek() {
        return getIntegerOrNull(MatchesColumns.WEEK);
    }

    /**
     * Get the {@code day} value.
     * Can be {@code null}.
     */
    public Integer getDay() {
        return getIntegerOrNull(MatchesColumns.DAY);
    }

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    public String getDate() {
        Integer index = getCachedColumnIndexOrThrow(MatchesColumns.DATE);
        return getString(index);
    }

    /**
     * Get the {@code team1_id} value.
     * Can be {@code null}.
     */
    public Integer getTeam1Id() {
        return getIntegerOrNull(MatchesColumns.TEAM1_ID);
    }

    /**
     * Get the {@code team2_id} value.
     * Can be {@code null}.
     */
    public Integer getTeam2Id() {
        return getIntegerOrNull(MatchesColumns.TEAM2_ID);
    }

    /**
     * Get the {@code team1} value.
     * Can be {@code null}.
     */
    public String getTeam1() {
        Integer index = getCachedColumnIndexOrThrow(MatchesColumns.TEAM1);
        return getString(index);
    }

    /**
     * Get the {@code team2} value.
     * Can be {@code null}.
     */
    public String getTeam2() {
        Integer index = getCachedColumnIndexOrThrow(MatchesColumns.TEAM2);
        return getString(index);
    }

    /**
     * Get the {@code time} value.
     * Can be {@code null}.
     */
    public String getTime() {
        Integer index = getCachedColumnIndexOrThrow(MatchesColumns.TIME);
        return getString(index);
    }

    /**
     * Get the {@code result1} value.
     * Can be {@code null}.
     */
    public Integer getResult1() {
        return getIntegerOrNull(MatchesColumns.RESULT1);
    }

    /**
     * Get the {@code result2} value.
     * Can be {@code null}.
     */
    public Integer getResult2() {
        return getIntegerOrNull(MatchesColumns.RESULT2);
    }

    /**
     * Get the {@code played} value.
     * Can be {@code null}.
     */
    public Integer getPlayed() {
        return getIntegerOrNull(MatchesColumns.PLAYED);
    }

    /**
     * Get the {@code match_no} value.
     * Can be {@code null}.
     */
    public Integer getMatchNo() {
        return getIntegerOrNull(MatchesColumns.MATCH_NO);
    }

    /**
     * Get the {@code match_position} value.
     * Can be {@code null}.
     */
    public Integer getMatchPosition() {
        return getIntegerOrNull(MatchesColumns.MATCH_POSITION);
    }

    public List<MatchesModel> getListAsMatchesModel() {
        List<MatchesModel> items = new ArrayList<MatchesModel>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new MatchesModel(this));
                moveToNext();
            }
        }
        return items;
    }

    public List<MatchDetailsItem> geListAsMatchDetailsItems(int overviewItemType) {
        List<MatchDetailsItem> items = new ArrayList<MatchDetailsItem>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new MatchDetailsItem(this, overviewItemType));
                moveToNext();
            }
        }
        return items;
    }

    public List<MatchesModel> getList() {
        List<MatchesModel> items = new ArrayList<MatchesModel>();
        if(moveToFirst()){
            while (!isAfterLast()){
                items.add(new MatchesModel(this));
                moveToNext();
            }
        }
        return items;
    }
}
