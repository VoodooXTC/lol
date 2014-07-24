package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.Team;
import joss.jacobo.lol.lcs.provider.teams.TeamsCursor;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for the {@code teams}.
 */
public class TeamsModel{
    public Integer teamId;
    public String teamAbrev;
    public Integer tournamentId;
    public String tournamentAbrev;
    public String teamName;

    public TeamsModel(){}

    public TeamsModel(TeamsCursor cursor){
        this.teamId = cursor.getTeamId();
        this.teamAbrev = cursor.getTeamAbrev();
        this.tournamentId = cursor.getTournamentId();
        this.tournamentAbrev = cursor.getTournamentAbrev();
        this.teamName = cursor.getTeamName();
    }

    public TeamsModel(Team team) {
        this.teamId = team.teamId;
        this.teamAbrev = team.teamAbrev;
        this.tournamentId = team.tournamentId;
        this.tournamentAbrev = team.tournamentAbrev;
        this.teamName = team.teamName;
    }
}