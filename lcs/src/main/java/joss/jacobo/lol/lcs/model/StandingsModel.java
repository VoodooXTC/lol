package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.Standings.Standing;
import joss.jacobo.lol.lcs.provider.standings.StandingsCursor;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for the {@code standings}.
 */
public class StandingsModel{
    public Integer standingId;
    public String tournamentAbrev;
    public Integer standingWeek;
    public String teamName;
    public String teamAbrev;
    public Integer wins;
    public Integer losses;
    public Integer delta;
    public Integer standingPosition;
    public String tournamentGroup;

    public StandingsModel(){}

    public StandingsModel(StandingsCursor cursor){
        this.standingId = cursor.getStandingId();
        this.tournamentAbrev = cursor.getTournamentAbrev();
        this.standingWeek = cursor.getStandingWeek();
        this.teamName = cursor.getTeamName();
        this.teamAbrev = cursor.getTeamAbrev();
        this.wins = cursor.getWins();
        this.losses = cursor.getLosses();
        this.delta = cursor.getDelta();
        this.standingPosition = cursor.getStandingPosition();
        this.tournamentGroup = cursor.getTournamentGroup();
    }

    public StandingsModel(Standing standing) {
        this.standingId = standing.id;
        this.tournamentAbrev = standing.league;
        this.standingWeek = standing.week;
        this.teamName = standing.teamName;
        this.teamAbrev = standing.teamAbrv;
        this.wins = standing.wins;
        this.losses = standing.losses;
        this.delta = standing.delta;
        this.standingPosition = standing.position;
        this.tournamentGroup = standing.group;
    }
}