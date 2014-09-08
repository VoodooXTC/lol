
package joss.jacobo.lol.lcs.api.model.Liveticker;

import com.google.gson.annotations.SerializedName;

public class Event {

    public String id;
    @SerializedName("match_id")
    public String matchId;
    public String time;
    public String title;
    public String event;
    public String type;

}
