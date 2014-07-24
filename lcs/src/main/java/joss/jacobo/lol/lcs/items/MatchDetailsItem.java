package joss.jacobo.lol.lcs.items;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;

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
        this.date = formatDate(cursor.getDatetime());
        this.time = cursor.getTime();
        this.winner = cursor.getResult();
    }

    private String formatDate(String dateString){

        try {

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateString);

            SimpleDateFormat fmtOut = new SimpleDateFormat("cccc, MMMM FF");
            return fmtOut.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }
}
