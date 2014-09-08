package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.model.Liveticker.MatchDetails;

/**
 * Created by Joss on 9/7/2014
 */
public class LivetickerBottomDrawerHeader extends LinearLayout {

    @InjectView(R.id.lt_blue_kills)
    TextView blueKills;
    @InjectView(R.id.lt_blue_gold)
    TextView blueGold;
    @InjectView(R.id.lt_blue_turrets_killed)
    TextView blueTurretsKilled;
    @InjectView(R.id.lt_purple_kills)
    TextView purpleKills;
    @InjectView(R.id.lt_purple_gold)
    TextView purpleGold;
    @InjectView(R.id.lt_purple_turrets_killed)
    TextView purpleTurretsKilled;

    public LivetickerBottomDrawerHeader(Context context) {
        this(context, null);
    }

    public LivetickerBottomDrawerHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LivetickerBottomDrawerHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_liveticker_bottom_drawer_header, this, true);
        ButterKnife.inject(this);
    }

    public void setContent(MatchDetails match){
        blueKills.setText(match.blueTotalKills);
        blueGold.setText(match.blueGold);
        blueTurretsKilled.setText(match.blueTowers);

        purpleKills.setText(match.purpleTotalKills);
        purpleGold.setText(match.purpleGold);
        purpleTurretsKilled.setText(match.purpleTowers);
    }
}
