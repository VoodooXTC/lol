
package joss.jacobo.lol.lcs.api.model.Standings;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.model.StandingsModel;

public class Standings {

    public String status;
    
    public String message;
    
    public List<Standing> standings = new ArrayList<Standing>();

    public List<StandingsModel> getList() {
        List<StandingsModel> items = new ArrayList<StandingsModel>();
        for(Standing standing : standings){
            items.add(new StandingsModel(standing));
        }
        return items;
    }
}
