
package joss.jacobo.lol.lcs.api.model.Replays;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.model.ReplaysModel;

public class Replays {

    public String kind;
    public String etag;
    public String nextPageToken;
    public String prevPageToken;
    public PageInfo pageInfo;
    public List<Replay> items = new ArrayList<Replay>();

    public List<ReplaysModel> getList() {
        List<ReplaysModel> replaysModels = new ArrayList<ReplaysModel>();

        for(Replay replay : items){
            replaysModels.add(new ReplaysModel(replay));
        }

        return replaysModels;
    }
}
