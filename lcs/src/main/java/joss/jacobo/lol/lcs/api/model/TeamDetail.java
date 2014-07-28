
package joss.jacobo.lol.lcs.api.model;

import com.google.gson.annotations.SerializedName;

public class TeamDetail {

    @SerializedName("_id")
    public Integer id;
    
    public String team;
    
    public String abrv;
    
    public String logo;

    @SerializedName("twitter_handle")
    public String twitterHandle;

    @SerializedName("about_text")
    public String aboutText;
    
    @SerializedName("team_picture")
    public String teamPicture;

}
