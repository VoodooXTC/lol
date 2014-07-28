package joss.jacobo.lol.lcs.provider.players;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;

import joss.jacobo.lol.lcs.model.PlayersModel;
import joss.jacobo.lol.lcs.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code players} table.
 */
public class PlayersCursor extends AbstractCursor {
    public PlayersCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code player_id} value.
     * Can be {@code null}.
     */
    public Integer getPlayerId() {
        return getIntegerOrNull(PlayersColumns.PLAYER_ID);
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    public String getName() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.NAME);
        return getString(index);
    }

    /**
     * Get the {@code irl_first_name} value.
     * Can be {@code null}.
     */
    public String getIrlFirstName() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.IRL_FIRST_NAME);
        return getString(index);
    }

    /**
     * Get the {@code irl_last_name} value.
     * Can be {@code null}.
     */
    public String getIrlLastName() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.IRL_LAST_NAME);
        return getString(index);
    }

    /**
     * Get the {@code team_id} value.
     * Can be {@code null}.
     */
    public Integer getTeamId() {
        return getIntegerOrNull(PlayersColumns.TEAM_ID);
    }

    /**
     * Get the {@code player_position} value.
     * Can be {@code null}.
     */
    public String getPlayerPosition() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.PLAYER_POSITION);
        return getString(index);
    }

    /**
     * Get the {@code active} value.
     * Can be {@code null}.
     */
    public Integer getActive() {
        return getIntegerOrNull(PlayersColumns.ACTIVE);
    }

    /**
     * Get the {@code famous_quote} value.
     * Can be {@code null}.
     */
    public String getFamousQuote() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.FAMOUS_QUOTE);
        return getString(index);
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    public String getDescription() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.DESCRIPTION);
        return getString(index);
    }

    /**
     * Get the {@code image} value.
     * Can be {@code null}.
     */
    public String getImage() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.IMAGE);
        return getString(index);
    }

    /**
     * Get the {@code facebook_link} value.
     * Can be {@code null}.
     */
    public String getFacebookLink() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.FACEBOOK_LINK);
        return getString(index);
    }

    /**
     * Get the {@code twitter_username} value.
     * Can be {@code null}.
     */
    public String getTwitterUsername() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.TWITTER_USERNAME);
        return getString(index);
    }

    /**
     * Get the {@code streaming_link} value.
     * Can be {@code null}.
     */
    public String getStreamingLink() {
        Integer index = getCachedColumnIndexOrThrow(PlayersColumns.STREAMING_LINK);
        return getString(index);
    }

    public List<PlayersModel> getList() {
        List<PlayersModel> items = new ArrayList<PlayersModel>();
        if(moveToFirst()){
            while(!isAfterLast()){
                items.add(new PlayersModel(this));
                moveToNext();
            }
        }
        return items;
    }
}
