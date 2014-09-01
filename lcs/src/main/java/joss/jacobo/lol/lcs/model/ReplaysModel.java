package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.Replays.Replay;
import joss.jacobo.lol.lcs.provider.replays.ReplaysCursor;
import joss.jacobo.lol.lcs.utils.GGson;

import com.google.gson.annotations.SerializedName;

/**
 * Model object for the {@code replays}.
 */
public class ReplaysModel{
    public String kind;
    public String etag;
    public String youtubeId;
    public String publishedAt;
    public String channelId;
    public String title;
    public String description;
    public String thumbnails;
    public String channelTitle;
    public String type;
    public String duration;
    public String dimension;
    public String definition;
    public String caption;
    public Boolean licensedcontent;
    public Integer viewcount;
    public Integer likecount;
    public Integer dislikecount;
    public Integer favoritecount;
    public Integer commentcount;

    public ReplaysModel(){}

    public ReplaysModel(ReplaysCursor cursor){
        this.kind = cursor.getKind();
        this.etag = cursor.getEtag();
        this.youtubeId = cursor.getYoutubeId();
        this.publishedAt = cursor.getPublishedAt();
        this.channelId = cursor.getChannelId();
        this.title = cursor.getTitle();
        this.description = cursor.getDescription();
        this.thumbnails = cursor.getThumbnails();
        this.channelTitle = cursor.getChannelTitle();
        this.type = cursor.getType();
        this.duration = cursor.getDuration();
        this.dimension = cursor.getDimension();
        this.definition = cursor.getDefinition();
        this.caption = cursor.getCaption();
        this.licensedcontent = cursor.getLicensedcontent();
        this.viewcount = cursor.getViewcount();
        this.likecount = cursor.getLikecount();
        this.dislikecount = cursor.getDislikecount();
        this.favoritecount = cursor.getFavoritecount();
        this.commentcount = cursor.getCommentcount();
    }

    public ReplaysModel(Replay replay) {
        this.kind = replay.kind;
        this.etag = replay.etag;
        this.youtubeId = replay.id;
        this.publishedAt = replay.snippet.publishedAt;
        this.channelId = replay.snippet.channelId;
        this.title = replay.snippet.title;
        this.description = replay.snippet.description;
        this.thumbnails = GGson.toJson(replay.snippet.thumbnails);
        this.channelTitle = replay.snippet.channelTitle;
        this.type = replay.snippet.type;
        this.duration = replay.contentDetails.duration;
        this.dimension = replay.contentDetails.dimension;
        this.definition = replay.contentDetails.definition;
        this.caption = replay.contentDetails.caption;
        this.licensedcontent = replay.contentDetails.licensedContent;
        this.viewcount = replay.statistics.viewCount;
        this.likecount = replay.statistics.likeCount;
        this.dislikecount = replay.statistics.dislikeCount;
        this.favoritecount = replay.statistics.favoriteCount;
        this.commentcount = replay.statistics.commentCount;
    }
}