
package joss.jacobo.lol.lcs.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.api.model.Players.Player;
import joss.jacobo.lol.lcs.model.PlayersModel;

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

    @SerializedName("players")
    public List<Player> players;

    public List<PlayersModel> getPlayersList(){
        List<PlayersModel> items = new ArrayList<PlayersModel>();
        for(Player player : players){
            items.add(new PlayersModel(player));
        }
        return items;
    }
}
