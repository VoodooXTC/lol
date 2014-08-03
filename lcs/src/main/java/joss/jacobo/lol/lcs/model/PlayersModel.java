package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.Players.Player;
import joss.jacobo.lol.lcs.provider.players.PlayersCursor;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for the {@code players}.
 */
public class PlayersModel{

    public static final int TYPE_PLAYER = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_MAX = TYPE_TITLE + 1;

    public int type = TYPE_PLAYER;
    public String titleFirstWord;
    public String titleSecondWord;
    public boolean showDivider = true;
    public String teamLogoUrl;

    public Integer playerId;
    public String name;
    public String irlFirstName;
    public String irlLastName;
    public Integer teamId;
    public String playerPosition;
    public Integer active;
    public String famousQuote;
    public String description;
    public String image;
    public String facebookLink;
    public String twitterUsername;
    public String streamingLink;

    public PlayersModel(){}

    public PlayersModel(PlayersCursor cursor){
        this.playerId = cursor.getPlayerId();
        this.name = cursor.getName();
        this.irlFirstName = cursor.getIrlFirstName();
        this.irlLastName = cursor.getIrlLastName();
        this.teamId = cursor.getTeamId();
        this.playerPosition = cursor.getPlayerPosition();
        this.active = cursor.getActive();
        this.famousQuote = cursor.getFamousQuote();
        this.description = cursor.getDescription();
        this.image = cursor.getImage();
        this.facebookLink = cursor.getFacebookLink();
        this.twitterUsername = cursor.getTwitterUsername();
        this.streamingLink = cursor.getStreamingLink();
    }

    public PlayersModel(Player player) {
        this.playerId = player.id;
        this.name = player.name;
        this.irlFirstName = player.irlFirstName;
        this.irlLastName = player.irlLastName;
        this.teamId = player.teamId;
        this.playerPosition = player.position;
        this.active = player.active;
        this.famousQuote = player.famousQuote;
        this.description = player.description;
        this.image = player.image;
        this.facebookLink = player.facebookLink;
        this.twitterUsername = player.twitterUsername;
        this.streamingLink = player.streamingLink;
    }

    public PlayersModel(int typeTitle, String first, String second) {
        this.type = typeTitle;
        this.titleFirstWord = first;
        this.titleSecondWord = second;
    }
}