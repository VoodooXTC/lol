package joss.jacobo.lol.lcs.provider.team_details;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code team_details} table.
 */
public interface TeamDetailsColumns extends BaseColumns {
    String TABLE_NAME = "team_details";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String TEAM_ID = "team_id";
    String ABREV = "abrev";
    String NAME = "name";
    String LOGO = "logo";
    String TEAM_PICTURE = "team_picture";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            TEAM_ID,
            ABREV,
            NAME,
            LOGO,
            TEAM_PICTURE
    };
    // @formatter:on
}