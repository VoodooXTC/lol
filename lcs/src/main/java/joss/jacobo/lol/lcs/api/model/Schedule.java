
package joss.jacobo.lol.lcs.api.model;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    public Integer id;

    @SerializedName("tournament_id")
    public Integer tournamentId;
    
    public String league;
    
    public String split;
    
    public String datetime;
    
    public Integer week;
    
    public Integer day;
    
    public String date;

    @SerializedName("team1_id")
    public Integer team1Id;

    @SerializedName("team2_id")
    public Integer team2Id;
    
    public String team1;
    
    public String team2;
    
    public String time;
    
    public Integer result1;

    public Integer result2;
    
    public Integer played;

    @SerializedName("match_no")
    public Integer matchNo;
    
    public Integer position;

}
