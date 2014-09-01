package joss.jacobo.lol.lcs.provider.matches;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code matches} table.
 */
public class MatchesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MatchesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, MatchesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MatchesContentValues putMatchId(Integer value) {
        mContentValues.put(MatchesColumns.MATCH_ID, value);
        return this;
    }

    public MatchesContentValues putMatchIdNull() {
        mContentValues.putNull(MatchesColumns.MATCH_ID);
        return this;
    }


    public MatchesContentValues putTournamentId(Integer value) {
        mContentValues.put(MatchesColumns.TOURNAMENT_ID, value);
        return this;
    }

    public MatchesContentValues putTournamentIdNull() {
        mContentValues.putNull(MatchesColumns.TOURNAMENT_ID);
        return this;
    }


    public MatchesContentValues putTournamentAbrev(String value) {
        mContentValues.put(MatchesColumns.TOURNAMENT_ABREV, value);
        return this;
    }

    public MatchesContentValues putTournamentAbrevNull() {
        mContentValues.putNull(MatchesColumns.TOURNAMENT_ABREV);
        return this;
    }


    public MatchesContentValues putSplit(String value) {
        mContentValues.put(MatchesColumns.SPLIT, value);
        return this;
    }

    public MatchesContentValues putSplitNull() {
        mContentValues.putNull(MatchesColumns.SPLIT);
        return this;
    }


    public MatchesContentValues putDatetime(String value) {
        mContentValues.put(MatchesColumns.DATETIME, value);
        return this;
    }

    public MatchesContentValues putDatetimeNull() {
        mContentValues.putNull(MatchesColumns.DATETIME);
        return this;
    }


    public MatchesContentValues putWeek(Integer value) {
        mContentValues.put(MatchesColumns.WEEK, value);
        return this;
    }

    public MatchesContentValues putWeekNull() {
        mContentValues.putNull(MatchesColumns.WEEK);
        return this;
    }


    public MatchesContentValues putDay(Integer value) {
        mContentValues.put(MatchesColumns.DAY, value);
        return this;
    }

    public MatchesContentValues putDayNull() {
        mContentValues.putNull(MatchesColumns.DAY);
        return this;
    }


    public MatchesContentValues putDate(String value) {
        mContentValues.put(MatchesColumns.DATE, value);
        return this;
    }

    public MatchesContentValues putDateNull() {
        mContentValues.putNull(MatchesColumns.DATE);
        return this;
    }


    public MatchesContentValues putTeam1Id(Integer value) {
        mContentValues.put(MatchesColumns.TEAM1_ID, value);
        return this;
    }

    public MatchesContentValues putTeam1IdNull() {
        mContentValues.putNull(MatchesColumns.TEAM1_ID);
        return this;
    }


    public MatchesContentValues putTeam2Id(Integer value) {
        mContentValues.put(MatchesColumns.TEAM2_ID, value);
        return this;
    }

    public MatchesContentValues putTeam2IdNull() {
        mContentValues.putNull(MatchesColumns.TEAM2_ID);
        return this;
    }


    public MatchesContentValues putTeam1(String value) {
        mContentValues.put(MatchesColumns.TEAM1, value);
        return this;
    }

    public MatchesContentValues putTeam1Null() {
        mContentValues.putNull(MatchesColumns.TEAM1);
        return this;
    }


    public MatchesContentValues putTeam2(String value) {
        mContentValues.put(MatchesColumns.TEAM2, value);
        return this;
    }

    public MatchesContentValues putTeam2Null() {
        mContentValues.putNull(MatchesColumns.TEAM2);
        return this;
    }


    public MatchesContentValues putTime(String value) {
        mContentValues.put(MatchesColumns.TIME, value);
        return this;
    }

    public MatchesContentValues putTimeNull() {
        mContentValues.putNull(MatchesColumns.TIME);
        return this;
    }


    public MatchesContentValues putResult1(Integer value) {
        mContentValues.put(MatchesColumns.RESULT1, value);
        return this;
    }

    public MatchesContentValues putResult1Null() {
        mContentValues.putNull(MatchesColumns.RESULT1);
        return this;
    }


    public MatchesContentValues putResult2(Integer value) {
        mContentValues.put(MatchesColumns.RESULT2, value);
        return this;
    }

    public MatchesContentValues putResult2Null() {
        mContentValues.putNull(MatchesColumns.RESULT2);
        return this;
    }


    public MatchesContentValues putPlayed(Integer value) {
        mContentValues.put(MatchesColumns.PLAYED, value);
        return this;
    }

    public MatchesContentValues putPlayedNull() {
        mContentValues.putNull(MatchesColumns.PLAYED);
        return this;
    }


    public MatchesContentValues putMatchNo(Integer value) {
        mContentValues.put(MatchesColumns.MATCH_NO, value);
        return this;
    }

    public MatchesContentValues putMatchNoNull() {
        mContentValues.putNull(MatchesColumns.MATCH_NO);
        return this;
    }


    public MatchesContentValues putMatchPosition(Integer value) {
        mContentValues.put(MatchesColumns.MATCH_POSITION, value);
        return this;
    }

    public MatchesContentValues putMatchPositionNull() {
        mContentValues.putNull(MatchesColumns.MATCH_POSITION);
        return this;
    }


    public static ContentValues[] getContentValues(List<MatchesModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(MatchesModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(MatchesModel item){
        MatchesContentValues values = new MatchesContentValues();
        values.putMatchId(item.matchId);
        values.putTournamentId(item.tournamentId);
        values.putTournamentAbrev(item.tournamentAbrev);
        values.putSplit(item.split);
        values.putDatetime(item.datetime);
        values.putWeek(item.week);
        values.putDay(item.day);
        values.putDate(item.date);
        values.putTeam1Id(item.team1Id);
        values.putTeam2Id(item.team2Id);
        values.putTeam1(item.team1);
        values.putTeam2(item.team2);
        values.putTime(item.time);
        values.putResult1(item.result1);
        values.putResult2(item.result2);
        values.putPlayed(item.played);
        values.putMatchNo(item.matchNo);
        values.putMatchPosition(item.matchPosition);
        return values.values();
    }
}
