package joss.jacobo.lol.lcs.provider.teams;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code teams} table.
 */
public interface TeamsColumns extends BaseColumns {
    String TABLE_NAME = "teams";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String TEAM_ID = "team_id";
    String TEAM_ABREV = "team_abrev";
    String TOURNAMENT_ID = "tournament_id";
    String TOURNAMENT_ABREV = "tournament_abrev";
    String TEAM_NAME = "team_name";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            TEAM_ID,
            TEAM_ABREV,
            TOURNAMENT_ID,
            TOURNAMENT_ABREV,
            TEAM_NAME
    };
    // @formatter:on
}