package joss.jacobo.lol.lcs.provider.tournaments;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code tournaments} table.
 */
public interface TournamentsColumns extends BaseColumns {
    String TABLE_NAME = "tournaments";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String TOURNAMENT_ID = "tournament_id";
    String ABREV = "abrev";
    String NAME = "name";
    String YEAR = "year";
    String SEASON = "season";
    String ONGOING = "ongoing";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            TOURNAMENT_ID,
            ABREV,
            NAME,
            YEAR,
            SEASON,
            ONGOING
    };
    // @formatter:on
}