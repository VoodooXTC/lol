package joss.jacobo.lol.lcs.provider.teams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.TeamsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code teams} table.
 */
public class TeamsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TeamsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, TeamsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public TeamsContentValues putTeamId(Integer value) {
        mContentValues.put(TeamsColumns.TEAM_ID, value);
        return this;
    }

    public TeamsContentValues putTeamIdNull() {
        mContentValues.putNull(TeamsColumns.TEAM_ID);
        return this;
    }


    public TeamsContentValues putTeamAbrev(String value) {
        mContentValues.put(TeamsColumns.TEAM_ABREV, value);
        return this;
    }

    public TeamsContentValues putTeamAbrevNull() {
        mContentValues.putNull(TeamsColumns.TEAM_ABREV);
        return this;
    }


    public TeamsContentValues putTournamentId(Integer value) {
        mContentValues.put(TeamsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TeamsContentValues putTournamentIdNull() {
        mContentValues.putNull(TeamsColumns.TOURNAMENT_ID);
        return this;
    }


    public TeamsContentValues putTournamentAbrev(String value) {
        mContentValues.put(TeamsColumns.TOURNAMENT_ABREV, value);
        return this;
    }

    public TeamsContentValues putTournamentAbrevNull() {
        mContentValues.putNull(TeamsColumns.TOURNAMENT_ABREV);
        return this;
    }


    public TeamsContentValues putTeamName(String value) {
        mContentValues.put(TeamsColumns.TEAM_NAME, value);
        return this;
    }

    public TeamsContentValues putTeamNameNull() {
        mContentValues.putNull(TeamsColumns.TEAM_NAME);
        return this;
    }


    public static ContentValues[] getContentValues(List<TeamsModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(TeamsModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(TeamsModel item){
        TeamsContentValues values = new TeamsContentValues();
        values.putTeamId(item.teamId);
        values.putTeamAbrev(item.teamAbrev);
        values.putTournamentId(item.tournamentId);
        values.putTournamentAbrev(item.tournamentAbrev);
        values.putTeamName(item.teamName);
        return values.values();
    }
}
