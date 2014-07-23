package joss.jacobo.lol.lcs.items;

/**
 * Created by Joss on 7/22/2014.
 */
public class MatchDetailsItem extends OverviewItem {

    public String blueTeam;
    public String blueScore;
    public String purpleTeam;
    public String purpleScore;
    public String date;
    public String time;
    public int winner;

    public MatchDetailsItem(int type, String blueTeam, String blueScore, String purpleTeam, String purpleScore, String date, String time, int winner){
        this.type = type;
        this.blueTeam = blueTeam;
        this.blueScore = blueScore;
        this.purpleTeam = purpleTeam;
        this.purpleScore = purpleScore;
        this.date = date;
        this.time = time;
        this.winner = winner;
    }
}
