
package joss.jacobo.lol.lcs.api.model.Liveticker;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Liveticker {

    @SerializedName("match_details")
    public MatchDetails matchDetails;
    public List<Event> events = new ArrayList<Event>();
}
