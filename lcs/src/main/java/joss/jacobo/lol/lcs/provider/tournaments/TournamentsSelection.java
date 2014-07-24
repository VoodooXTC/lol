package joss.jacobo.lol.lcs.provider.tournaments;

import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code tournaments} table.
 */
public class TournamentsSelection extends AbstractSelection<TournamentsSelection> {
    @Override
    public Uri uri() {
        return TournamentsColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code TournamentsCursor} object, which is positioned before the first entry, or null.
     */
    public TournamentsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new TournamentsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public TournamentsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public TournamentsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public TournamentsSelection id(long... value) {
        addEquals(TournamentsColumns._ID, toObjectArray(value));
        return this;
    }

    public TournamentsSelection tournamentId(Integer... value) {
        addEquals(TournamentsColumns.TOURNAMENT_ID, value);
        return this;
    }
    
    public TournamentsSelection tournamentIdNot(Integer... value) {
        addNotEquals(TournamentsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TournamentsSelection tournamentIdGt(int value) {
        addGreaterThan(TournamentsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TournamentsSelection tournamentIdGtEq(int value) {
        addGreaterThanOrEquals(TournamentsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TournamentsSelection tournamentIdLt(int value) {
        addLessThan(TournamentsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TournamentsSelection tournamentIdLtEq(int value) {
        addLessThanOrEquals(TournamentsColumns.TOURNAMENT_ID, value);
        return this;
    }

    public TournamentsSelection abrev(String... value) {
        addEquals(TournamentsColumns.ABREV, value);
        return this;
    }
    
    public TournamentsSelection abrevNot(String... value) {
        addNotEquals(TournamentsColumns.ABREV, value);
        return this;
    }


    public TournamentsSelection name(String... value) {
        addEquals(TournamentsColumns.NAME, value);
        return this;
    }
    
    public TournamentsSelection nameNot(String... value) {
        addNotEquals(TournamentsColumns.NAME, value);
        return this;
    }


    public TournamentsSelection year(Integer... value) {
        addEquals(TournamentsColumns.YEAR, value);
        return this;
    }
    
    public TournamentsSelection yearNot(Integer... value) {
        addNotEquals(TournamentsColumns.YEAR, value);
        return this;
    }

    public TournamentsSelection yearGt(int value) {
        addGreaterThan(TournamentsColumns.YEAR, value);
        return this;
    }

    public TournamentsSelection yearGtEq(int value) {
        addGreaterThanOrEquals(TournamentsColumns.YEAR, value);
        return this;
    }

    public TournamentsSelection yearLt(int value) {
        addLessThan(TournamentsColumns.YEAR, value);
        return this;
    }

    public TournamentsSelection yearLtEq(int value) {
        addLessThanOrEquals(TournamentsColumns.YEAR, value);
        return this;
    }

    public TournamentsSelection season(String... value) {
        addEquals(TournamentsColumns.SEASON, value);
        return this;
    }
    
    public TournamentsSelection seasonNot(String... value) {
        addNotEquals(TournamentsColumns.SEASON, value);
        return this;
    }


    public TournamentsSelection ongoing(Integer... value) {
        addEquals(TournamentsColumns.ONGOING, value);
        return this;
    }
    
    public TournamentsSelection ongoingNot(Integer... value) {
        addNotEquals(TournamentsColumns.ONGOING, value);
        return this;
    }

    public TournamentsSelection ongoingGt(int value) {
        addGreaterThan(TournamentsColumns.ONGOING, value);
        return this;
    }

    public TournamentsSelection ongoingGtEq(int value) {
        addGreaterThanOrEquals(TournamentsColumns.ONGOING, value);
        return this;
    }

    public TournamentsSelection ongoingLt(int value) {
        addLessThan(TournamentsColumns.ONGOING, value);
        return this;
    }

    public TournamentsSelection ongoingLtEq(int value) {
        addLessThanOrEquals(TournamentsColumns.ONGOING, value);
        return this;
    }

    public static String getTournamentAbrev(Context context, int tournamentId) {
        TournamentsSelection where = new TournamentsSelection();
        where.tournamentId(tournamentId);
        TournamentsCursor c = new TournamentsCursor(where.query(context.getContentResolver()));
        if(c.moveToFirst()){
            return c.getAbrev();
        }else{
            return null;
        }
    }
}
