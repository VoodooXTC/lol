package joss.jacobo.lol.lcs.provider.tournaments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.api.model.Tournament;
import joss.jacobo.lol.lcs.model.TournamentsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code tournaments} table.
 */
public class TournamentsCursor extends AbstractCursor {
    public TournamentsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code tournament_id} value.
     * Can be {@code null}.
     */
    public Integer getTournamentId() {
        return getIntegerOrNull(TournamentsColumns.TOURNAMENT_ID);
    }

    /**
     * Get the {@code abrev} value.
     * Can be {@code null}.
     */
    public String getAbrev() {
        Integer index = getCachedColumnIndexOrThrow(TournamentsColumns.ABREV);
        return getString(index);
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(TournamentsColumns.NAME);
        return getString(index);
    }

    /**
     * Get the {@code year} value.
     * Can be {@code null}.
     */
    public Integer getYear() {
        return getIntegerOrNull(TournamentsColumns.YEAR);
    }

    /**
     * Get the {@code season} value.
     * Can be {@code null}.
     */
    public String getSeason() {
        Integer index = getCachedColumnIndexOrThrow(TournamentsColumns.SEASON);
        return getString(index);
    }

    /**
     * Get the {@code ongoing} value.
     * Can be {@code null}.
     */
    public Integer getOngoing() {
        return getIntegerOrNull(TournamentsColumns.ONGOING);
    }

    public List<TournamentsModel> getList() {
        List<TournamentsModel> items = new ArrayList<TournamentsModel>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new TournamentsModel(this));
                moveToNext();
            }
        }
        return items;
    }
}
