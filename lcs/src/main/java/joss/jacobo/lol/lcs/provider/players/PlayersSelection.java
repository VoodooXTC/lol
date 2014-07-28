package joss.jacobo.lol.lcs.provider.players;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import joss.jacobo.lol.lcs.provider.base.AbstractSelection;

/**
 * Selection for the {@code players} table.
 */
public class PlayersSelection extends AbstractSelection<PlayersSelection> {
    @Override
    public Uri uri() {
        return PlayersColumns.CONTENT_URI;
    }
    
    /**
     * Query the given content resolver using this selection.
     * 
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code PlayersCursor} object, which is positioned before the first entry, or null.
     */
    public PlayersCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new PlayersCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public PlayersCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public PlayersCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }
    
    
    public PlayersSelection id(long... value) {
        addEquals(PlayersColumns._ID, toObjectArray(value));
        return this;
    }

    public PlayersSelection playerId(Integer... value) {
        addEquals(PlayersColumns.PLAYER_ID, value);
        return this;
    }
    
    public PlayersSelection playerIdNot(Integer... value) {
        addNotEquals(PlayersColumns.PLAYER_ID, value);
        return this;
    }

    public PlayersSelection playerIdGt(int value) {
        addGreaterThan(PlayersColumns.PLAYER_ID, value);
        return this;
    }

    public PlayersSelection playerIdGtEq(int value) {
        addGreaterThanOrEquals(PlayersColumns.PLAYER_ID, value);
        return this;
    }

    public PlayersSelection playerIdLt(int value) {
        addLessThan(PlayersColumns.PLAYER_ID, value);
        return this;
    }

    public PlayersSelection playerIdLtEq(int value) {
        addLessThanOrEquals(PlayersColumns.PLAYER_ID, value);
        return this;
    }

    public PlayersSelection name(String... value) {
        addEquals(PlayersColumns.NAME, value);
        return this;
    }
    
    public PlayersSelection nameNot(String... value) {
        addNotEquals(PlayersColumns.NAME, value);
        return this;
    }


    public PlayersSelection irlFirstName(String... value) {
        addEquals(PlayersColumns.IRL_FIRST_NAME, value);
        return this;
    }
    
    public PlayersSelection irlFirstNameNot(String... value) {
        addNotEquals(PlayersColumns.IRL_FIRST_NAME, value);
        return this;
    }


    public PlayersSelection irlLastName(String... value) {
        addEquals(PlayersColumns.IRL_LAST_NAME, value);
        return this;
    }
    
    public PlayersSelection irlLastNameNot(String... value) {
        addNotEquals(PlayersColumns.IRL_LAST_NAME, value);
        return this;
    }


    public PlayersSelection teamId(Integer... value) {
        addEquals(PlayersColumns.TEAM_ID, value);
        return this;
    }
    
    public PlayersSelection teamIdNot(Integer... value) {
        addNotEquals(PlayersColumns.TEAM_ID, value);
        return this;
    }

    public PlayersSelection teamIdGt(int value) {
        addGreaterThan(PlayersColumns.TEAM_ID, value);
        return this;
    }

    public PlayersSelection teamIdGtEq(int value) {
        addGreaterThanOrEquals(PlayersColumns.TEAM_ID, value);
        return this;
    }

    public PlayersSelection teamIdLt(int value) {
        addLessThan(PlayersColumns.TEAM_ID, value);
        return this;
    }

    public PlayersSelection teamIdLtEq(int value) {
        addLessThanOrEquals(PlayersColumns.TEAM_ID, value);
        return this;
    }

    public PlayersSelection playerPosition(String... value) {
        addEquals(PlayersColumns.PLAYER_POSITION, value);
        return this;
    }
    
    public PlayersSelection playerPositionNot(String... value) {
        addNotEquals(PlayersColumns.PLAYER_POSITION, value);
        return this;
    }


    public PlayersSelection active(Integer... value) {
        addEquals(PlayersColumns.ACTIVE, value);
        return this;
    }
    
    public PlayersSelection activeNot(Integer... value) {
        addNotEquals(PlayersColumns.ACTIVE, value);
        return this;
    }

    public PlayersSelection activeGt(int value) {
        addGreaterThan(PlayersColumns.ACTIVE, value);
        return this;
    }

    public PlayersSelection activeGtEq(int value) {
        addGreaterThanOrEquals(PlayersColumns.ACTIVE, value);
        return this;
    }

    public PlayersSelection activeLt(int value) {
        addLessThan(PlayersColumns.ACTIVE, value);
        return this;
    }

    public PlayersSelection activeLtEq(int value) {
        addLessThanOrEquals(PlayersColumns.ACTIVE, value);
        return this;
    }

    public PlayersSelection famousQuote(String... value) {
        addEquals(PlayersColumns.FAMOUS_QUOTE, value);
        return this;
    }
    
    public PlayersSelection famousQuoteNot(String... value) {
        addNotEquals(PlayersColumns.FAMOUS_QUOTE, value);
        return this;
    }


    public PlayersSelection description(String... value) {
        addEquals(PlayersColumns.DESCRIPTION, value);
        return this;
    }
    
    public PlayersSelection descriptionNot(String... value) {
        addNotEquals(PlayersColumns.DESCRIPTION, value);
        return this;
    }


    public PlayersSelection image(String... value) {
        addEquals(PlayersColumns.IMAGE, value);
        return this;
    }
    
    public PlayersSelection imageNot(String... value) {
        addNotEquals(PlayersColumns.IMAGE, value);
        return this;
    }


    public PlayersSelection facebookLink(String... value) {
        addEquals(PlayersColumns.FACEBOOK_LINK, value);
        return this;
    }
    
    public PlayersSelection facebookLinkNot(String... value) {
        addNotEquals(PlayersColumns.FACEBOOK_LINK, value);
        return this;
    }


    public PlayersSelection twitterUsername(String... value) {
        addEquals(PlayersColumns.TWITTER_USERNAME, value);
        return this;
    }
    
    public PlayersSelection twitterUsernameNot(String... value) {
        addNotEquals(PlayersColumns.TWITTER_USERNAME, value);
        return this;
    }


    public PlayersSelection streamingLink(String... value) {
        addEquals(PlayersColumns.STREAMING_LINK, value);
        return this;
    }
    
    public PlayersSelection streamingLinkNot(String... value) {
        addNotEquals(PlayersColumns.STREAMING_LINK, value);
        return this;
    }

}
