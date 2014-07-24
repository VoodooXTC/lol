
package joss.jacobo.lol.lcs.api.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.model.TeamsModel;
import joss.jacobo.lol.lcs.model.TournamentsModel;

public class Config {

    public List<Tournament> tournaments = new ArrayList<Tournament>();

    public List<Team> teams = new ArrayList<Team>();

    @SerializedName("team_details")
    public List<TeamDetail> teamDetails = new ArrayList<TeamDetail>();

    public List<Schedule> schedule = new ArrayList<Schedule>();

    public List<TournamentsModel> getTournaments() {
        List<TournamentsModel> tourneys = new ArrayList<TournamentsModel>();

        for(Tournament tournament : tournaments){
            tourneys.add(new TournamentsModel(tournament));
        }

        return tourneys;
    }

    public List<TeamDetailsModel> getTeamDetails() {
        List<TeamDetailsModel> teamDetails = new ArrayList<TeamDetailsModel>();

        for(TeamDetailsModel teamDetail : teamDetails){
            teamDetails.add(new TeamDetailsModel(teamDetail));
        }

        return teamDetails;
    }

    public List<TeamsModel> getTeams() {
        List<TeamsModel> teamModels = new ArrayList<TeamsModel>();

        for(Team team : teams){
            teamModels.add(new TeamsModel(team));
        }

        return teamModels;
    }

    public List<MatchesModel> getMatches() {
        List<MatchesModel> matchesModels = new ArrayList<MatchesModel>();
        for(Schedule match : schedule){
            matchesModels.add(new MatchesModel(match));
        }
        return matchesModels;
    }
}
