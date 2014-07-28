
package joss.jacobo.lol.lcs.api.model.Players;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.model.PlayersModel;

public class Player {

    
    public Integer id;
    
    public String name;

    @SerializedName("irl_first_name")
    public String irlFirstName;

    @SerializedName("irl_last_name")
    public String irlLastName;

    @SerializedName("team_id")
    public Integer teamId;
    
    public String position;
    
    public Integer active;

    @SerializedName("famous_quote")
    public String famousQuote;
    
    public String description;
    
    public String image;

    @SerializedName("facebook_link")
    public String facebookLink;

    @SerializedName("twitter_username")
    public String twitterUsername;

    @SerializedName("streaming_link")
    public String streamingLink;

    public static List<PlayersModel> getList(List<Player> players) {
        List<PlayersModel> items = new ArrayList<PlayersModel>();
        for(Player player : players){
            items.add(new PlayersModel(player));
        }
        return items;
    }
}
