package joss.jacobo.lol.lcs.provider.matches;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code matches} table.
 */
public interface MatchesColumns extends BaseColumns {
    String TABLE_NAME = "matches";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String MATCH_ID = "match_id";
    String TOURNAMENT_ID = "tournament_id";
    String TOURNAMENT_ABREV = "tournament_abrev";
    String SPLIT = "split";
    String DATETIME = "datetime";
    String WEEK = "week";
    String DAY = "day";
    String DATE = "date";
    String TEAM1_ID = "team1_id";
    String TEAM2_ID = "team2_id";
    String TEAM1 = "team1";
    String TEAM2 = "team2";
    String TIME = "time";
    String RESULT1 = "result1";
    String RESULT2 = "result2";
    String PLAYED = "played";
    String MATCH_NO = "match_no";
    String MATCH_POSITION = "match_position";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            MATCH_ID,
            TOURNAMENT_ID,
            TOURNAMENT_ABREV,
            SPLIT,
            DATETIME,
            WEEK,
            DAY,
            DATE,
            TEAM1_ID,
            TEAM2_ID,
            TEAM1,
            TEAM2,
            TIME,
            RESULT1,
            RESULT2,
            PLAYED,
            MATCH_NO,
            MATCH_POSITION
    };
    // @formatter:on
}