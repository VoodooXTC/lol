package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.TeamDetail;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for the {@code team_details}.
 */
public class TeamDetailsModel{
    public Integer teamId;
    public String abrev;
    public String name;
    public String logo;
    public String teamPicture;

    public TeamDetailsModel(){}

    public TeamDetailsModel(TeamDetailsCursor cursor){
        this.teamId = cursor.getTeamId();
        this.abrev = cursor.getAbrev();
        this.name = cursor.getName();
        this.logo = cursor.getLogo();
        this.teamPicture = cursor.getTeamPicture();
    }

    public TeamDetailsModel(TeamDetail teamDetail) {
        this.teamId = teamDetail.id;
        this.abrev = teamDetail.abrv;
        this.name = teamDetail.team;
        this.logo = teamDetail.logo;
        this.teamPicture = teamDetail.teamPicture;
    }
}