package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.model.Replays.Replay;
import joss.jacobo.lol.lcs.utils.DateTimeFormatter;
import joss.jacobo.lol.lcs.utils.PTimeFormatter;

/**
 * Created by Joss on 9/1/2014
 */
public class ReplayItem extends LinearLayout {

    Context context;
    ImageView image;
    TextView duration;
    TextView title;
    TextView channelTitle;
    TextView publishedAtAndViews;
    View divider;

    PTimeFormatter pTimeFormatter;

    public ReplayItem(Context context) {
        this(context, null);
    }

    public ReplayItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReplayItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_item_replay, this, true);
        image = (ImageView) findViewById(R.id.replay_image);
        duration = (TextView) findViewById(R.id.replay_duration);
        title = (TextView) findViewById(R.id.replay_title);
        channelTitle = (TextView) findViewById(R.id.replay_channel_title);
        publishedAtAndViews = (TextView) findViewById(R.id.replay_published_and_views);
        divider = findViewById(R.id.replay_divider);

        pTimeFormatter = new PTimeFormatter();
    }

    public void setContent(Replay replay){
        String url = replay.snippet.thumbnails.getImage() != null ? replay.snippet.thumbnails.getImage().url : "";
        Picasso.with(context).load(url).into(image);
        duration.setText(pTimeFormatter.formatPTimeRegex(replay.contentDetails.duration));
        title.setText(replay.snippet.title);
        channelTitle.setText(replay.snippet.channelTitle);
        publishedAtAndViews.setText(
                DateTimeFormatter.formatDateTimeStringToAgo(replay.snippet.publishedAt, DateTimeFormatter.YOUTUBE)
                        + " · "
                        + DateTimeFormatter.formatCountCommas(replay.statistics.viewCount) + " views");
    }

    public void showDivider(boolean show){
        divider.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
