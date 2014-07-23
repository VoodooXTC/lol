package joss.jacobo.lol.lcs.items;

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
    public static final int TYPE_MAX = TYPE_SECTION_TITLE + 1;

    public int type;
    public int teamId;
    public String title;

    public DrawerItem(int type, int teamId, String title){
        this.type = type;
        this.teamId = teamId;
        this.title = title;
    }
}
