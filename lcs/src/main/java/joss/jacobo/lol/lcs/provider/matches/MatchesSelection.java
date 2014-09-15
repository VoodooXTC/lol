package joss.jacobo.lol.lcs.provider.matches;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code matches} table.
 */
public class MatchesSelection extends AbstractSelection<MatchesSelection> {
    @Override
    public Uri uri() {
        return MatchesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code MatchesCursor} object, which is positioned before the first entry, or null.
     */
    public MatchesCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new MatchesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public MatchesCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public MatchesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public MatchesSelection id(long... value) {
        addEquals(MatchesColumns._ID, toObjectArray(value));
        return this;
    }

    public MatchesSelection matchId(Integer... value) {
        addEquals(MatchesColumns.MATCH_ID, value);
        return this;
    }

    public MatchesSelection matchIdNot(Integer... value) {
        addNotEquals(MatchesColumns.MATCH_ID, value);
        return this;
    }

    public MatchesSelection matchIdGt(int value) {
        addGreaterThan(MatchesColumns.MATCH_ID, value);
        return this;
    }

    public MatchesSelection matchIdGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.MATCH_ID, value);
        return this;
    }

    public MatchesSelection matchIdLt(int value) {
        addLessThan(MatchesColumns.MATCH_ID, value);
        return this;
    }

    public MatchesSelection matchIdLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.MATCH_ID, value);
        return this;
    }

    public MatchesSelection tournamentId(Integer... value) {
        addEquals(MatchesColumns.TOURNAMENT_ID, value);
        return this;
    }

    public MatchesSelection tournamentIdNot(Integer... value) {
        addNotEquals(MatchesColumns.TOURNAMENT_ID, value);
        return this;
    }

    public MatchesSelection tournamentIdGt(int value) {
        addGreaterThan(MatchesColumns.TOURNAMENT_ID, value);
        return this;
    }

    public MatchesSelection tournamentIdGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.TOURNAMENT_ID, value);
        return this;
    }

    public MatchesSelection tournamentIdLt(int value) {
        addLessThan(MatchesColumns.TOURNAMENT_ID, value);
        return this;
    }

    public MatchesSelection tournamentIdLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.TOURNAMENT_ID, value);
        return this;
    }

    public MatchesSelection tournamentAbrev(String... value) {
        addEquals(MatchesColumns.TOURNAMENT_ABREV, value);
        return this;
    }

    public MatchesSelection tournamentAbrevNot(String... value) {
        addNotEquals(MatchesColumns.TOURNAMENT_ABREV, value);
        return this;
    }


    public MatchesSelection split(String... value) {
        addEquals(MatchesColumns.SPLIT, value);
        return this;
    }

    public MatchesSelection splitNot(String... value) {
        addNotEquals(MatchesColumns.SPLIT, value);
        return this;
    }


    public MatchesSelection datetime(String... value) {
        addEquals(MatchesColumns.DATETIME, value);
        return this;
    }

    public MatchesSelection datetimeNot(String... value) {
        addNotEquals(MatchesColumns.DATETIME, value);
        return this;
    }


    public MatchesSelection week(String... value) {
        addEquals(MatchesColumns.WEEK, value);
        return this;
    }

    public MatchesSelection weekNot(String... value) {
        addNotEquals(MatchesColumns.WEEK, value);
        return this;
    }


    public MatchesSelection day(Integer... value) {
        addEquals(MatchesColumns.DAY, value);
        return this;
    }

    public MatchesSelection dayNot(Integer... value) {
        addNotEquals(MatchesColumns.DAY, value);
        return this;
    }

    public MatchesSelection dayGt(int value) {
        addGreaterThan(MatchesColumns.DAY, value);
        return this;
    }

    public MatchesSelection dayGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.DAY, value);
        return this;
    }

    public MatchesSelection dayLt(int value) {
        addLessThan(MatchesColumns.DAY, value);
        return this;
    }

    public MatchesSelection dayLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.DAY, value);
        return this;
    }

    public MatchesSelection date(String... value) {
        addEquals(MatchesColumns.DATE, value);
        return this;
    }

    public MatchesSelection dateNot(String... value) {
        addNotEquals(MatchesColumns.DATE, value);
        return this;
    }


    public MatchesSelection team1Id(Integer... value) {
        addEquals(MatchesColumns.TEAM1_ID, value);
        return this;
    }

    public MatchesSelection team1IdNot(Integer... value) {
        addNotEquals(MatchesColumns.TEAM1_ID, value);
        return this;
    }

    public MatchesSelection team1IdGt(int value) {
        addGreaterThan(MatchesColumns.TEAM1_ID, value);
        return this;
    }

    public MatchesSelection team1IdGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.TEAM1_ID, value);
        return this;
    }

    public MatchesSelection team1IdLt(int value) {
        addLessThan(MatchesColumns.TEAM1_ID, value);
        return this;
    }

    public MatchesSelection team1IdLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.TEAM1_ID, value);
        return this;
    }

    public MatchesSelection team2Id(Integer... value) {
        addEquals(MatchesColumns.TEAM2_ID, value);
        return this;
    }

    public MatchesSelection team2IdNot(Integer... value) {
        addNotEquals(MatchesColumns.TEAM2_ID, value);
        return this;
    }

    public MatchesSelection team2IdGt(int value) {
        addGreaterThan(MatchesColumns.TEAM2_ID, value);
        return this;
    }

    public MatchesSelection team2IdGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.TEAM2_ID, value);
        return this;
    }

    public MatchesSelection team2IdLt(int value) {
        addLessThan(MatchesColumns.TEAM2_ID, value);
        return this;
    }

    public MatchesSelection team2IdLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.TEAM2_ID, value);
        return this;
    }

    public MatchesSelection team1(String... value) {
        addEquals(MatchesColumns.TEAM1, value);
        return this;
    }

    public MatchesSelection team1Not(String... value) {
        addNotEquals(MatchesColumns.TEAM1, value);
        return this;
    }


    public MatchesSelection team2(String... value) {
        addEquals(MatchesColumns.TEAM2, value);
        return this;
    }

    public MatchesSelection team2Not(String... value) {
        addNotEquals(MatchesColumns.TEAM2, value);
        return this;
    }


    public MatchesSelection time(String... value) {
        addEquals(MatchesColumns.TIME, value);
        return this;
    }

    public MatchesSelection timeNot(String... value) {
        addNotEquals(MatchesColumns.TIME, value);
        return this;
    }


    public MatchesSelection result1(Integer... value) {
        addEquals(MatchesColumns.RESULT1, value);
        return this;
    }

    public MatchesSelection result1Not(Integer... value) {
        addNotEquals(MatchesColumns.RESULT1, value);
        return this;
    }

    public MatchesSelection result1Gt(int value) {
        addGreaterThan(MatchesColumns.RESULT1, value);
        return this;
    }

    public MatchesSelection result1GtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.RESULT1, value);
        return this;
    }

    public MatchesSelection result1Lt(int value) {
        addLessThan(MatchesColumns.RESULT1, value);
        return this;
    }

    public MatchesSelection result1LtEq(int value) {
        addLessThanOrEquals(MatchesColumns.RESULT1, value);
        return this;
    }

    public MatchesSelection result2(Integer... value) {
        addEquals(MatchesColumns.RESULT2, value);
        return this;
    }

    public MatchesSelection result2Not(Integer... value) {
        addNotEquals(MatchesColumns.RESULT2, value);
        return this;
    }

    public MatchesSelection result2Gt(int value) {
        addGreaterThan(MatchesColumns.RESULT2, value);
        return this;
    }

    public MatchesSelection result2GtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.RESULT2, value);
        return this;
    }

    public MatchesSelection result2Lt(int value) {
        addLessThan(MatchesColumns.RESULT2, value);
        return this;
    }

    public MatchesSelection result2LtEq(int value) {
        addLessThanOrEquals(MatchesColumns.RESULT2, value);
        return this;
    }

    public MatchesSelection played(Integer... value) {
        addEquals(MatchesColumns.PLAYED, value);
        return this;
    }

    public MatchesSelection playedNot(Integer... value) {
        addNotEquals(MatchesColumns.PLAYED, value);
        return this;
    }

    public MatchesSelection playedGt(int value) {
        addGreaterThan(MatchesColumns.PLAYED, value);
        return this;
    }

    public MatchesSelection playedGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.PLAYED, value);
        return this;
    }

    public MatchesSelection playedLt(int value) {
        addLessThan(MatchesColumns.PLAYED, value);
        return this;
    }

    public MatchesSelection playedLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.PLAYED, value);
        return this;
    }

    public MatchesSelection matchNo(Integer... value) {
        addEquals(MatchesColumns.MATCH_NO, value);
        return this;
    }

    public MatchesSelection matchNoNot(Integer... value) {
        addNotEquals(MatchesColumns.MATCH_NO, value);
        return this;
    }

    public MatchesSelection matchNoGt(int value) {
        addGreaterThan(MatchesColumns.MATCH_NO, value);
        return this;
    }

    public MatchesSelection matchNoGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.MATCH_NO, value);
        return this;
    }

    public MatchesSelection matchNoLt(int value) {
        addLessThan(MatchesColumns.MATCH_NO, value);
        return this;
    }

    public MatchesSelection matchNoLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.MATCH_NO, value);
        return this;
    }

    public MatchesSelection matchPosition(Integer... value) {
        addEquals(MatchesColumns.MATCH_POSITION, value);
        return this;
    }

    public MatchesSelection matchPositionNot(Integer... value) {
        addNotEquals(MatchesColumns.MATCH_POSITION, value);
        return this;
    }

    public MatchesSelection matchPositionGt(int value) {
        addGreaterThan(MatchesColumns.MATCH_POSITION, value);
        return this;
    }

    public MatchesSelection matchPositionGtEq(int value) {
        addGreaterThanOrEquals(MatchesColumns.MATCH_POSITION, value);
        return this;
    }

    public MatchesSelection matchPositionLt(int value) {
        addLessThan(MatchesColumns.MATCH_POSITION, value);
        return this;
    }

    public MatchesSelection matchPositionLtEq(int value) {
        addLessThanOrEquals(MatchesColumns.MATCH_POSITION, value);
        return this;
    }

    public MatchesSelection bestOf(String... value) {
        addEquals(MatchesColumns.BEST_OF, value);
        return this;
    }

    public MatchesSelection bestOfNot(String... value) {
        addNotEquals(MatchesColumns.BEST_OF, value);
        return this;
    }


    public MatchesSelection tournamentGroup(String... value) {
        addEquals(MatchesColumns.TOURNAMENT_GROUP, value);
        return this;
    }

    public MatchesSelection tournamentGroupNot(String... value) {
        addNotEquals(MatchesColumns.TOURNAMENT_GROUP, value);
        return this;
    }

}
