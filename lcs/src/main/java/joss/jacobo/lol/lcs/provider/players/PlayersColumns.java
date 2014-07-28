package joss.jacobo.lol.lcs.provider.players;

import android.net.Uri;
import android.provider.BaseColumns;

import joss.jacobo.lol.lcs.provider.LcsProvider;

/**
 * Columns for the {@code players} table.
 */
public interface PlayersColumns extends BaseColumns {
    String TABLE_NAME = "players";
    Uri CONTENT_URI = Uri.parse(LcsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    String _ID = BaseColumns._ID;
    String PLAYER_ID = "player_id";
    String NAME = "name";
    String IRL_FIRST_NAME = "irl_first_name";
    String IRL_LAST_NAME = "irl_last_name";
    String TEAM_ID = "team_id";
    String PLAYER_POSITION = "player_position";
    String ACTIVE = "active";
    String FAMOUS_QUOTE = "famous_quote";
    String DESCRIPTION = "description";
    String IMAGE = "image";
    String FACEBOOK_LINK = "facebook_link";
    String TWITTER_USERNAME = "twitter_username";
    String STREAMING_LINK = "streaming_link";

    String DEFAULT_ORDER = _ID;

	// @formatter:off
    String[] FULL_PROJECTION = new String[] {
            _ID,
            PLAYER_ID,
            NAME,
            IRL_FIRST_NAME,
            IRL_LAST_NAME,
            TEAM_ID,
            PLAYER_POSITION,
            ACTIVE,
            FAMOUS_QUOTE,
            DESCRIPTION,
            IMAGE,
            FACEBOOK_LINK,
            TWITTER_USERNAME,
            STREAMING_LINK
    };
    // @formatter:on
}