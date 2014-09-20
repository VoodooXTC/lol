package joss.jacobo.lol.lcs.items;

import joss.jacobo.lol.lcs.utils.DateTimeFormatter;

/**
 * Created by Joss on 7/22/2014
 */
public class OverviewItem {

    public static final int TYPE_STANDINGS = 0;
    public static final int TYPE_MATCH_RESULTS = 1;
    public static final int TYPE_MATCH_UPCOMING = 2;
    public static final int TYPE_SECTION_TITLE = 3;
    public static final int TYPE_SECTION_TITLE_SCHEDULE_MATCHES = 4;
    public static final int TYPE_MAX = TYPE_SECTION_TITLE_SCHEDULE_MATCHES + 1;

    public int type;
    public String titleFirstWord;
    public String titleSecondWord;

    public boolean showDivider = true;

    public OverviewItem(){

    }

    public OverviewItem(int type, String firstWord, String secondWord){
        this.type = type;
        this.titleFirstWord = firstWord;
        this.titleSecondWord = secondWord;
    }

    public OverviewItem(int type, String date){
        this.type = type;
        this.titleFirstWord = DateTimeFormatter.formatDate(date);
        this.titleSecondWord = "";
    }

}
