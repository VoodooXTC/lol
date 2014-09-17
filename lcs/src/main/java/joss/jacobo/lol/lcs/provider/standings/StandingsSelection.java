package joss.jacobo.lol.lcs.provider.standings;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code standings} table.
 */
public class StandingsSelection extends AbstractSelection<StandingsSelection> {
    @Override
    public Uri uri() {
        return StandingsColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code StandingsCursor} object, which is positioned before the first entry, or null.
     */
    public StandingsCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new StandingsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public StandingsCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public StandingsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public StandingsSelection id(long... value) {
        addEquals(StandingsColumns._ID, toObjectArray(value));
        return this;
    }

    public StandingsSelection standingId(Integer... value) {
        addEquals(StandingsColumns.STANDING_ID, value);
        return this;
    }
    
    public StandingsSelection standingIdNot(Integer... value) {
        addNotEquals(StandingsColumns.STANDING_ID, value);
        return this;
    }

    public StandingsSelection standingIdGt(int value) {
        addGreaterThan(StandingsColumns.STANDING_ID, value);
        return this;
    }

    public StandingsSelection standingIdGtEq(int value) {
        addGreaterThanOrEquals(StandingsColumns.STANDING_ID, value);
        return this;
    }

    public StandingsSelection standingIdLt(int value) {
        addLessThan(StandingsColumns.STANDING_ID, value);
        return this;
    }

    public StandingsSelection standingIdLtEq(int value) {
        addLessThanOrEquals(StandingsColumns.STANDING_ID, value);
        return this;
    }

    public StandingsSelection tournamentAbrev(String... value) {
        addEquals(StandingsColumns.TOURNAMENT_ABREV, value);
        return this;
    }
    
    public StandingsSelection tournamentAbrevNot(String... value) {
        addNotEquals(StandingsColumns.TOURNAMENT_ABREV, value);
        return this;
    }


    public StandingsSelection standingWeek(Integer... value) {
        addEquals(StandingsColumns.STANDING_WEEK, value);
        return this;
    }
    
    public StandingsSelection standingWeekNot(Integer... value) {
        addNotEquals(StandingsColumns.STANDING_WEEK, value);
        return this;
    }

    public StandingsSelection standingWeekGt(int value) {
        addGreaterThan(StandingsColumns.STANDING_WEEK, value);
        return this;
    }

    public StandingsSelection standingWeekGtEq(int value) {
        addGreaterThanOrEquals(StandingsColumns.STANDING_WEEK, value);
        return this;
    }

    public StandingsSelection standingWeekLt(int value) {
        addLessThan(StandingsColumns.STANDING_WEEK, value);
        return this;
    }

    public StandingsSelection standingWeekLtEq(int value) {
        addLessThanOrEquals(StandingsColumns.STANDING_WEEK, value);
        return this;
    }

    public StandingsSelection teamName(String... value) {
        addEquals(StandingsColumns.TEAM_NAME, value);
        return this;
    }
    
    public StandingsSelection teamNameNot(String... value) {
        addNotEquals(StandingsColumns.TEAM_NAME, value);
        return this;
    }


    public StandingsSelection teamAbrev(String... value) {
        addEquals(StandingsColumns.TEAM_ABREV, value);
        return this;
    }
    
    public StandingsSelection teamAbrevNot(String... value) {
        addNotEquals(StandingsColumns.TEAM_ABREV, value);
        return this;
    }


    public StandingsSelection wins(Integer... value) {
        addEquals(StandingsColumns.WINS, value);
        return this;
    }
    
    public StandingsSelection winsNot(Integer... value) {
        addNotEquals(StandingsColumns.WINS, value);
        return this;
    }

    public StandingsSelection winsGt(int value) {
        addGreaterThan(StandingsColumns.WINS, value);
        return this;
    }

    public StandingsSelection winsGtEq(int value) {
        addGreaterThanOrEquals(StandingsColumns.WINS, value);
        return this;
    }

    public StandingsSelection winsLt(int value) {
        addLessThan(StandingsColumns.WINS, value);
        return this;
    }

    public StandingsSelection winsLtEq(int value) {
        addLessThanOrEquals(StandingsColumns.WINS, value);
        return this;
    }

    public StandingsSelection losses(Integer... value) {
        addEquals(StandingsColumns.LOSSES, value);
        return this;
    }
    
    public StandingsSelection lossesNot(Integer... value) {
        addNotEquals(StandingsColumns.LOSSES, value);
        return this;
    }

    public StandingsSelection lossesGt(int value) {
        addGreaterThan(StandingsColumns.LOSSES, value);
        return this;
    }

    public StandingsSelection lossesGtEq(int value) {
        addGreaterThanOrEquals(StandingsColumns.LOSSES, value);
        return this;
    }

    public StandingsSelection lossesLt(int value) {
        addLessThan(StandingsColumns.LOSSES, value);
        return this;
    }

    public StandingsSelection lossesLtEq(int value) {
        addLessThanOrEquals(StandingsColumns.LOSSES, value);
        return this;
    }

    public StandingsSelection delta(Integer... value) {
        addEquals(StandingsColumns.DELTA, value);
        return this;
    }
    
    public StandingsSelection deltaNot(Integer... value) {
        addNotEquals(StandingsColumns.DELTA, value);
        return this;
    }

    public StandingsSelection deltaGt(int value) {
        addGreaterThan(StandingsColumns.DELTA, value);
        return this;
    }

    public StandingsSelection deltaGtEq(int value) {
        addGreaterThanOrEquals(StandingsColumns.DELTA, value);
        return this;
    }

    public StandingsSelection deltaLt(int value) {
        addLessThan(StandingsColumns.DELTA, value);
        return this;
    }

    public StandingsSelection deltaLtEq(int value) {
        addLessThanOrEquals(StandingsColumns.DELTA, value);
        return this;
    }

    public StandingsSelection standingPosition(Integer... value) {
        addEquals(StandingsColumns.STANDING_POSITION, value);
        return this;
    }
    
    public StandingsSelection standingPositionNot(Integer... value) {
        addNotEquals(StandingsColumns.STANDING_POSITION, value);
        return this;
    }

    public StandingsSelection standingPositionGt(int value) {
        addGreaterThan(StandingsColumns.STANDING_POSITION, value);
        return this;
    }

    public StandingsSelection standingPositionGtEq(int value) {
        addGreaterThanOrEquals(StandingsColumns.STANDING_POSITION, value);
        return this;
    }

    public StandingsSelection standingPositionLt(int value) {
        addLessThan(StandingsColumns.STANDING_POSITION, value);
        return this;
    }

    public StandingsSelection standingPositionLtEq(int value) {
        addLessThanOrEquals(StandingsColumns.STANDING_POSITION, value);
        return this;
    }

    public StandingsSelection tournamentGroup(String... value) {
        addEquals(StandingsColumns.TOURNAMENT_GROUP, value);
        return this;
    }
    
    public StandingsSelection tournamentGroupNot(String... value) {
        addNotEquals(StandingsColumns.TOURNAMENT_GROUP, value);
        return this;
    }

}
