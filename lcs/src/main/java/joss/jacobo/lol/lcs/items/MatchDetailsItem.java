package joss.jacobo.lol.lcs.items;

import android.content.Context;

import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.utils.DateTimeFormatter;

/**
 * Created by Joss on 7/22/2014
 */
public class MatchDetailsItem extends OverviewItem {

    public String blueTeam;
    public String blueScore;
    public String purpleTeam;
    public String purpleScore;
    public String datetime;
    public int winner;

    public MatchDetailsItem(Context context, MatchesCursor cursor, int type){
        this.type = type;
        this.blueScore = String.valueOf(cursor.getResult1());
        this.purpleScore = String.valueOf(cursor.getResult2());
        this.datetime = cursor.getDatetime();
        this.winner = cursor.getResult1() > cursor.getResult2() ? 0 : 1;

        this.blueTeam = getTeamName(context, cursor.getTeam1Id());
        this.purpleTeam = getTeamName(context, cursor.getTeam2Id());
    }

    public MatchDetailsItem(Context context, MatchesModel match, int type) {
        this.type = type;
        this.blueScore = String.valueOf(match.result2);
        this.purpleScore = String .valueOf(match.result1);
        this.datetime = match.datetime;
        this.winner = match.result1 > match.result2 ? 0 : 1;

        this.blueTeam = getTeamName(context, match.team1Id);
        this.purpleTeam = getTeamName(context, match.team2Id);
    }

    private String getTeamName(Context context, int teamId){
        TeamDetailsSelection where = new TeamDetailsSelection();
        where.teamId(teamId);
        TeamDetailsCursor cursor = where.query(context.getContentResolver());
        return cursor.moveToFirst() ? cursor.getName() : null;
    }
}
