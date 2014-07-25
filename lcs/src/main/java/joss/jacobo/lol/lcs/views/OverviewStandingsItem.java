package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.StandingsItem;

/**
 * Created by jossayjacobo on 7/23/14.
 */
public class OverviewStandingsItem extends RelativeLayout {

    TextView position;
    TextView teamName;
    TextView wins;
    TextView losses;

    public OverviewStandingsItem(Context context) {
        this(context, null);
    }

    public OverviewStandingsItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverviewStandingsItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_item_overview_standings, this, true);
        position = (TextView) findViewById(R.id.item_overview_position);
        teamName = (TextView) findViewById(R.id.item_overview_team_name);
        wins = (TextView) findViewById(R.id.item_overview_wins);
        losses = (TextView) findViewById(R.id.item_overview_losses);
    }

    public void setContent(StandingsItem standingsItem){
        position.setText(standingsItem.position + "");
        teamName.setText(standingsItem.teamName);
        wins.setText(standingsItem.wins + "");
        losses.setText(standingsItem.losses + "");
    }
}