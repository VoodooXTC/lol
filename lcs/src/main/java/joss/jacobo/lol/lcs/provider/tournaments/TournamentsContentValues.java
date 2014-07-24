package joss.jacobo.lol.lcs.provider.tournaments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.TournamentsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code tournaments} table.
 */
public class TournamentsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TournamentsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, TournamentsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public TournamentsContentValues putTournamentId(Integer value) {
        mContentValues.put(TournamentsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TournamentsContentValues putTournamentIdNull() {
        mContentValues.putNull(TournamentsColumns.TOURNAMENT_ID);
        return this;
    }


    public TournamentsContentValues putAbrev(String value) {
        mContentValues.put(TournamentsColumns.ABREV, value);
        return this;
    }

    public TournamentsContentValues putAbrevNull() {
        mContentValues.putNull(TournamentsColumns.ABREV);
        return this;
    }


    public TournamentsContentValues putName(String value) {
        mContentValues.put(TournamentsColumns.NAME, value);
        return this;
    }

    public TournamentsContentValues putNameNull() {
        mContentValues.putNull(TournamentsColumns.NAME);
        return this;
    }


    public TournamentsContentValues putYear(Integer value) {
        mContentValues.put(TournamentsColumns.YEAR, value);
        return this;
    }

    public TournamentsContentValues putYearNull() {
        mContentValues.putNull(TournamentsColumns.YEAR);
        return this;
    }


    public TournamentsContentValues putSeason(String value) {
        mContentValues.put(TournamentsColumns.SEASON, value);
        return this;
    }

    public TournamentsContentValues putSeasonNull() {
        mContentValues.putNull(TournamentsColumns.SEASON);
        return this;
    }


    public TournamentsContentValues putOngoing(Integer value) {
        mContentValues.put(TournamentsColumns.ONGOING, value);
        return this;
    }

    public TournamentsContentValues putOngoingNull() {
        mContentValues.putNull(TournamentsColumns.ONGOING);
        return this;
    }


    public static ContentValues[] getContentValues(List<TournamentsModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(TournamentsModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(TournamentsModel item){
        TournamentsContentValues values = new TournamentsContentValues();
        values.putTournamentId(item.tournamentId);
        values.putAbrev(item.abrev);
        values.putName(item.name);
        values.putYear(item.year);
        values.putSeason(item.season);
        values.putOngoing(item.ongoing);
        return values.values();
    }
}
