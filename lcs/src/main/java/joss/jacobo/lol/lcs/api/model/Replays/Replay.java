
package joss.jacobo.lol.lcs.api.model.Replays;

import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.api.model.LiveStreams.Image;
import joss.jacobo.lol.lcs.api.model.LiveStreams.Statistics;
import joss.jacobo.lol.lcs.api.model.LiveStreams.Thumbnails;
import joss.jacobo.lol.lcs.model.ReplaysModel;
import joss.jacobo.lol.lcs.provider.replays.ReplaysCursor;
import joss.jacobo.lol.lcs.utils.GGson;

public class Replay {

    public String kind;
    public String etag;
    public String id;
    public Snippet snippet;
    public ContentDetails contentDetails;
    public Statistics statistics;

    public Replay(ReplaysCursor replaysCursor) {
        this.kind = replaysCursor.getKind();
        this.etag = replaysCursor.getEtag();
        this.id = replaysCursor.getYoutubeId();

        this.snippet = new Snippet();
        this.snippet.publishedAt = replaysCursor.getPublishedAt();
        this.snippet.channelId = replaysCursor.getChannelId();
        this.snippet.title = replaysCursor.getTitle();
        this.snippet.description = replaysCursor.getDescription();
        this.snippet.thumbnails = GGson.fromJson(replaysCursor.getThumbnails(), Thumbnails.class);
        this.snippet.channelTitle = replaysCursor.getChannelTitle();
        this.snippet.type = replaysCursor.getType();

        this.contentDetails = new ContentDetails();
        this.contentDetails.duration = replaysCursor.getDuration();
        this.contentDetails.dimension = replaysCursor.getDimension();
        this.contentDetails.definition = replaysCursor.getDefinition();
        this.contentDetails.caption = replaysCursor.getCaption();
        this.contentDetails.licensedContent = replaysCursor.getLicensedcontent();

        this.statistics = new Statistics();
        this.statistics.viewCount = replaysCursor.getViewcount();
        this.statistics.likeCount = replaysCursor.getLikecount();
        this.statistics.dislikeCount = replaysCursor.getDislikecount();
        this.statistics.favoriteCount = replaysCursor.getFavoritecount();
        this.statistics.commentCount = replaysCursor.getCommentcount();
    }

    public static List<ReplaysModel> getList(List<Replay> replays) {
        List<ReplaysModel> items = new ArrayList<ReplaysModel>();
        for(Replay replay : replays){
            items.add(new ReplaysModel(replay));
        }
        return items;
    }
}
