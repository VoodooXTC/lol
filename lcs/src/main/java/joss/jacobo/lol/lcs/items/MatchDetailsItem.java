package joss.jacobo.lol.lcs.items;

import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.utils.DateTimeFormatter;

/**
 * Created by Joss on 7/22/2014
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

    public MatchDetailsItem(MatchesCursor cursor, int type){
        this.type = type;
        this.blueTeam = cursor.getTeam1();
        this.blueScore = String.valueOf(cursor.getResult1());
        this.purpleTeam = cursor.getTeam2();
        this.purpleScore = String.valueOf(cursor.getResult2());
        this.date = DateTimeFormatter.formatDatetime(cursor.getDatetime());
        this.time = cursor.getTime();
        this.winner = cursor.getResult1() > cursor.getResult2() ? 0 : 1;
    }

    public MatchDetailsItem(MatchesModel match, int type) {
        this.type = type;
        this.blueTeam = match.team1;
        this.blueScore = String.valueOf(match.result2);
        this.purpleTeam = match.team2;
        this.purpleScore = String .valueOf(match.result1);
        this.date = DateTimeFormatter.formatDate(match.date);
        this.time = match.time;
        this.winner = match.result1 > match.result2 ? 0 : 1;
    }
}
