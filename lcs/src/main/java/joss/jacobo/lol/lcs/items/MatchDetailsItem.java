package joss.jacobo.lol.lcs.items;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.utils.DateTimeStringFormatter;

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

    public MatchDetailsItem(MatchesCursor cursor, int type){
        this.type = type;
        this.blueTeam = cursor.getTeam1();
        this.blueScore = cursor.getResult() == 0 ? "1" : "0";
        this.purpleTeam = cursor.getTeam2();
        this.purpleScore = cursor.getResult() == 1 ? "1" : "0";
        this.date = DateTimeStringFormatter.formatDatetime(cursor.getDatetime());
        this.time = cursor.getTime();
        this.winner = cursor.getResult();
    }

    public MatchDetailsItem(MatchesModel match, int type) {
        this.type = type;
        this.blueTeam = match.team1;
        this.blueScore = match.result == 0 ? "1" : "0";
        this.purpleTeam = match.team2;
        this.purpleScore = match.result == 1 ? "1" : "0";
        this.date = DateTimeStringFormatter.formatDate(match.date);
        this.time = match.time;
        this.winner = match.result;
    }
}
