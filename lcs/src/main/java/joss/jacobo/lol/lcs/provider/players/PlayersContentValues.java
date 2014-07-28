package joss.jacobo.lol.lcs.provider.players;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.PlayersModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code players} table.
 */
public class PlayersContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return PlayersColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, PlayersSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public PlayersContentValues putPlayerId(Integer value) {
        mContentValues.put(PlayersColumns.PLAYER_ID, value);
        return this;
    }

    public PlayersContentValues putPlayerIdNull() {
        mContentValues.putNull(PlayersColumns.PLAYER_ID);
        return this;
    }


    public PlayersContentValues putName(String value) {
        mContentValues.put(PlayersColumns.NAME, value);
        return this;
    }

    public PlayersContentValues putNameNull() {
        mContentValues.putNull(PlayersColumns.NAME);
        return this;
    }


    public PlayersContentValues putIrlFirstName(String value) {
        mContentValues.put(PlayersColumns.IRL_FIRST_NAME, value);
        return this;
    }

    public PlayersContentValues putIrlFirstNameNull() {
        mContentValues.putNull(PlayersColumns.IRL_FIRST_NAME);
        return this;
    }


    public PlayersContentValues putIrlLastName(String value) {
        mContentValues.put(PlayersColumns.IRL_LAST_NAME, value);
        return this;
    }

    public PlayersContentValues putIrlLastNameNull() {
        mContentValues.putNull(PlayersColumns.IRL_LAST_NAME);
        return this;
    }


    public PlayersContentValues putTeamId(Integer value) {
        mContentValues.put(PlayersColumns.TEAM_ID, value);
        return this;
    }

    public PlayersContentValues putTeamIdNull() {
        mContentValues.putNull(PlayersColumns.TEAM_ID);
        return this;
    }


    public PlayersContentValues putPlayerPosition(String value) {
        mContentValues.put(PlayersColumns.PLAYER_POSITION, value);
        return this;
    }

    public PlayersContentValues putPlayerPositionNull() {
        mContentValues.putNull(PlayersColumns.PLAYER_POSITION);
        return this;
    }


    public PlayersContentValues putActive(Integer value) {
        mContentValues.put(PlayersColumns.ACTIVE, value);
        return this;
    }

    public PlayersContentValues putActiveNull() {
        mContentValues.putNull(PlayersColumns.ACTIVE);
        return this;
    }


    public PlayersContentValues putFamousQuote(String value) {
        mContentValues.put(PlayersColumns.FAMOUS_QUOTE, value);
        return this;
    }

    public PlayersContentValues putFamousQuoteNull() {
        mContentValues.putNull(PlayersColumns.FAMOUS_QUOTE);
        return this;
    }


    public PlayersContentValues putDescription(String value) {
        mContentValues.put(PlayersColumns.DESCRIPTION, value);
        return this;
    }

    public PlayersContentValues putDescriptionNull() {
        mContentValues.putNull(PlayersColumns.DESCRIPTION);
        return this;
    }


    public PlayersContentValues putImage(String value) {
        mContentValues.put(PlayersColumns.IMAGE, value);
        return this;
    }

    public PlayersContentValues putImageNull() {
        mContentValues.putNull(PlayersColumns.IMAGE);
        return this;
    }


    public PlayersContentValues putFacebookLink(String value) {
        mContentValues.put(PlayersColumns.FACEBOOK_LINK, value);
        return this;
    }

    public PlayersContentValues putFacebookLinkNull() {
        mContentValues.putNull(PlayersColumns.FACEBOOK_LINK);
        return this;
    }


    public PlayersContentValues putTwitterUsername(String value) {
        mContentValues.put(PlayersColumns.TWITTER_USERNAME, value);
        return this;
    }

    public PlayersContentValues putTwitterUsernameNull() {
        mContentValues.putNull(PlayersColumns.TWITTER_USERNAME);
        return this;
    }


    public PlayersContentValues putStreamingLink(String value) {
        mContentValues.put(PlayersColumns.STREAMING_LINK, value);
        return this;
    }

    public PlayersContentValues putStreamingLinkNull() {
        mContentValues.putNull(PlayersColumns.STREAMING_LINK);
        return this;
    }


    public static ContentValues[] getContentValues(List<PlayersModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(PlayersModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(PlayersModel item){
        PlayersContentValues values = new PlayersContentValues();
        values.putPlayerId(item.playerId);
        values.putName(item.name);
        values.putIrlFirstName(item.irlFirstName);
        values.putIrlLastName(item.irlLastName);
        values.putTeamId(item.teamId);
        values.putPlayerPosition(item.playerPosition);
        values.putActive(item.active);
        values.putFamousQuote(item.famousQuote);
        values.putDescription(item.description);
        values.putImage(item.image);
        values.putFacebookLink(item.facebookLink);
        values.putTwitterUsername(item.twitterUsername);
        values.putStreamingLink(item.streamingLink);
        return values.values();
    }
}
