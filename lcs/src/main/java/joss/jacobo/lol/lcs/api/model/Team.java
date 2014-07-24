
package joss.jacobo.lol.lcs.api.model;

import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("team_id")
    public Integer teamId;

    @SerializedName("team_abrev")
    public String teamAbrev;

    @SerializedName("tournament_id")
    public Integer tournamentId;

    @SerializedName("tournament_abrev")
    public String tournamentAbrev;

    @SerializedName("team")
    public String teamName;
}
