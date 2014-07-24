package joss.jacobo.lol.lcs.provider.standings;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code standings} table.
 */
public interface StandingsColumns extends BaseColumns {
    String TABLE_NAME = "standings";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String STANDING_ID = "standing_id";
    String TOURNAMENT_ABREV = "tournament_abrev";
    String STANDING_WEEK = "standing_week";
    String TEAM_NAME = "team_name";
    String TEAM_ABREV = "team_abrev";
    String WINS = "wins";
    String LOSSES = "losses";
    String DELTA = "delta";
    String STANDING_POSITION = "standing_position";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            STANDING_ID,
            TOURNAMENT_ABREV,
            STANDING_WEEK,
            TEAM_NAME,
            TEAM_ABREV,
            WINS,
            LOSSES,
            DELTA,
            STANDING_POSITION
    };
    // @formatter:on
}