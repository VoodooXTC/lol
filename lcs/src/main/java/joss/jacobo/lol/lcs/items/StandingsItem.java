package joss.jacobo.lol.lcs.items;

/**
 * Created by Joss on 7/22/2014.
 */
public class StandingsItem extends OverviewItem {

    public String league;
    public int week;
    public int position;
    public String teamAbrv;
    public int wins;
    public int losses;

    public StandingsItem(int type, String league, int week, int position, String teamAbrv, int wins, int losses){
        this.type = type;
        this.league = league;
        this.week = week;
        this.position = position;
        this.teamAbrv = teamAbrv;
        this.wins = wins;
        this.losses = losses;
    }
}
