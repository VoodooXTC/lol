package joss.jacobo.lol.lcs.provider.standings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.StandingsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code standings} table.
 */
public class StandingsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StandingsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, StandingsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public StandingsContentValues putStandingId(Integer value) {
        mContentValues.put(StandingsColumns.STANDING_ID, value);
        return this;
    }

    public StandingsContentValues putStandingIdNull() {
        mContentValues.putNull(StandingsColumns.STANDING_ID);
        return this;
    }


    public StandingsContentValues putTournamentAbrev(String value) {
        mContentValues.put(StandingsColumns.TOURNAMENT_ABREV, value);
        return this;
    }

    public StandingsContentValues putTournamentAbrevNull() {
        mContentValues.putNull(StandingsColumns.TOURNAMENT_ABREV);
        return this;
    }


    public StandingsContentValues putStandingWeek(Integer value) {
        mContentValues.put(StandingsColumns.STANDING_WEEK, value);
        return this;
    }

    public StandingsContentValues putStandingWeekNull() {
        mContentValues.putNull(StandingsColumns.STANDING_WEEK);
        return this;
    }


    public StandingsContentValues putTeamName(String value) {
        mContentValues.put(StandingsColumns.TEAM_NAME, value);
        return this;
    }

    public StandingsContentValues putTeamNameNull() {
        mContentValues.putNull(StandingsColumns.TEAM_NAME);
        return this;
    }


    public StandingsContentValues putTeamAbrev(String value) {
        mContentValues.put(StandingsColumns.TEAM_ABREV, value);
        return this;
    }

    public StandingsContentValues putTeamAbrevNull() {
        mContentValues.putNull(StandingsColumns.TEAM_ABREV);
        return this;
    }


    public StandingsContentValues putWins(Integer value) {
        mContentValues.put(StandingsColumns.WINS, value);
        return this;
    }

    public StandingsContentValues putWinsNull() {
        mContentValues.putNull(StandingsColumns.WINS);
        return this;
    }


    public StandingsContentValues putLosses(Integer value) {
        mContentValues.put(StandingsColumns.LOSSES, value);
        return this;
    }

    public StandingsContentValues putLossesNull() {
        mContentValues.putNull(StandingsColumns.LOSSES);
        return this;
    }


    public StandingsContentValues putDelta(Integer value) {
        mContentValues.put(StandingsColumns.DELTA, value);
        return this;
    }

    public StandingsContentValues putDeltaNull() {
        mContentValues.putNull(StandingsColumns.DELTA);
        return this;
    }


    public StandingsContentValues putStandingPosition(Integer value) {
        mContentValues.put(StandingsColumns.STANDING_POSITION, value);
        return this;
    }

    public StandingsContentValues putStandingPositionNull() {
        mContentValues.putNull(StandingsColumns.STANDING_POSITION);
        return this;
    }


    public static ContentValues[] getContentValues(List<StandingsModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(StandingsModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(StandingsModel item){
        StandingsContentValues values = new StandingsContentValues();
        values.putStandingId(item.standingId);
        values.putTournamentAbrev(item.tournamentAbrev);
        values.putStandingWeek(item.standingWeek);
        values.putTeamName(item.teamName);
        values.putTeamAbrev(item.teamAbrev);
        values.putWins(item.wins);
        values.putLosses(item.losses);
        values.putDelta(item.delta);
        values.putStandingPosition(item.standingPosition);
        return values.values();
    }
}
