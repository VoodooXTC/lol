package joss.jacobo.lol.lcs.items;

import joss.jacobo.lol.lcs.provider.teams.TeamsCursor;

/**
 * Created by Joss on 7/21/2014
 */
public class DrawerItem {

    public static final int TYPE_LIVESTREAM = 0;
    public static final int TYPE_LIVETICKER = 1;
    public static final int TYPE_OVERVIEW = 2;
    public static final int TYPE_NEWS = 3;
    public static final int TYPE_SCHEDULE_RESULTS = 4;
    public static final int TYPE_STANDINGS = 5;
    public static final int TYPE_TEAM = 6;
    public static final int TYPE_SECTION_TITLE = 7;
    public static final int TYPE_REPLAYS = 8;
    public static final int TYPE_ADS_FREE = 9;
    public static final int TYPE_MAX = TYPE_ADS_FREE + 1;

    public int type;
    public int teamId;
    public String title;

    public DrawerItem(int type, int teamId, String title){
        this.type = type;
        this.teamId = teamId;
        this.title = title;
    }

    public DrawerItem(TeamsCursor teamsCursor) {
        this.type = TYPE_TEAM;
        this.teamId = teamsCursor.getTeamId();
        this.title = teamsCursor.getTeamName();
    }
}
