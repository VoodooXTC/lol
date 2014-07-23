package joss.jacobo.lol.lcs.items;

/**
 * Created by Joss on 7/22/2014.
 */
public class StandingsItem extends OverviewItem {

    public String league;
    public int week;
    public int position;
    public String teamName;
    public int wins;
    public int losses;

    public StandingsItem(int type, String league, int week, int position, String teamName, int wins, int losses){
        this.type = type;
        this.league = league;
        this.week = week;
        this.position = position;
        this.teamName = teamName;
        this.wins = wins;
        this.losses = losses;
    }
}
