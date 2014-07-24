package joss.jacobo.lol.lcs.provider.team_details;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code team_details} table.
 */
public class TeamDetailsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TeamDetailsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     * 
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, TeamDetailsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public TeamDetailsContentValues putTeamId(Integer value) {
        mContentValues.put(TeamDetailsColumns.TEAM_ID, value);
        return this;
    }

    public TeamDetailsContentValues putTeamIdNull() {
        mContentValues.putNull(TeamDetailsColumns.TEAM_ID);
        return this;
    }


    public TeamDetailsContentValues putAbrev(String value) {
        mContentValues.put(TeamDetailsColumns.ABREV, value);
        return this;
    }

    public TeamDetailsContentValues putAbrevNull() {
        mContentValues.putNull(TeamDetailsColumns.ABREV);
        return this;
    }


    public TeamDetailsContentValues putName(String value) {
        mContentValues.put(TeamDetailsColumns.NAME, value);
        return this;
    }

    public TeamDetailsContentValues putNameNull() {
        mContentValues.putNull(TeamDetailsColumns.NAME);
        return this;
    }


    public TeamDetailsContentValues putLogo(String value) {
        mContentValues.put(TeamDetailsColumns.LOGO, value);
        return this;
    }

    public TeamDetailsContentValues putLogoNull() {
        mContentValues.putNull(TeamDetailsColumns.LOGO);
        return this;
    }


    public TeamDetailsContentValues putTeamPicture(String value) {
        mContentValues.put(TeamDetailsColumns.TEAM_PICTURE, value);
        return this;
    }

    public TeamDetailsContentValues putTeamPictureNull() {
        mContentValues.putNull(TeamDetailsColumns.TEAM_PICTURE);
        return this;
    }


    public static ContentValues[] getContentValues(List<TeamDetailsModel> items){
        List<ContentValues> values = new ArrayList<ContentValues>();
        for(TeamDetailsModel item : items){
            values.add(getSingleContentValue(item));
        }
        return values.toArray(new ContentValues[values.size()]);
    }

    public static ContentValues getSingleContentValue(TeamDetailsModel item){
        TeamDetailsContentValues values = new TeamDetailsContentValues();
        values.putTeamId(item.teamId);
        values.putAbrev(item.abrev);
        values.putName(item.name);
        values.putLogo(item.logo);
        values.putTeamPicture(item.teamPicture);
        return values.values();
    }
}
