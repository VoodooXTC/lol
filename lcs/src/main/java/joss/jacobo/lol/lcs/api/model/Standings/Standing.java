
package joss.jacobo.lol.lcs.api.model.Standings;

import com.google.gson.annotations.SerializedName;

public class Standing {

    public Integer id;
    
    public String league;
    
    public Integer week;
    
    public Integer position;

    @SerializedName("team_abrv")
    public String teamAbrv;

    @SerializedName("team")
    public String teamName;
    
    public Integer wins;
    
    public Integer losses;
    
    public Integer delta;

    public String group;

}
