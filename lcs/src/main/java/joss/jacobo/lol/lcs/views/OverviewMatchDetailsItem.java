package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.OverviewItem;

/**
 * Created by jossayjacobo on 7/23/14
 */
public class OverviewMatchDetailsItem extends RelativeLayout {

    TextView blueTeamName;
    TextView blueScore;
    TextView purpleTeamName;
    TextView purpleScore;
    LinearLayout scoreContainer;
    TextView time;
    TextView date;

    public OverviewMatchDetailsItem(Context context) {
        this(context, null);
    }

    public OverviewMatchDetailsItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverviewMatchDetailsItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_match_details_item, this, true);
        blueTeamName = (TextView) findViewById(R.id.item_match_details_blue_team_name);
        blueScore = (TextView) findViewById(R.id.item_match_details_blue_score);
        purpleTeamName = (TextView) findViewById(R.id.item_match_details_purple_team_name);
        purpleScore = (TextView) findViewById(R.id.item_match_details_purple_score);
        scoreContainer = (LinearLayout) findViewById(R.id.item_match_details_score_container);
        time = (TextView) findViewById(R.id.item_match_details_time);
        date = (TextView) findViewById(R.id.item_match_details_date);
    }

    public void setContent(MatchDetailsItem matchDetailsItem){
        blueTeamName.setText(matchDetailsItem.blueTeam);
        blueScore.setText(matchDetailsItem.blueScore);
        purpleTeamName.setText(matchDetailsItem.purpleTeam);
        purpleScore.setText(matchDetailsItem.purpleScore);
        date.setText(matchDetailsItem.date);
        time.setText(matchDetailsItem.time);

        switch (matchDetailsItem.type){
            case OverviewItem.TYPE_MATCH_RESULTS:
                time.setVisibility(View.GONE);
                scoreContainer.setVisibility(View.VISIBLE);

                if(matchDetailsItem.winner == 0){
                    setBlueWinner();
                }else{
                    setPurpleWinner();
                }

                break;
            case OverviewItem.TYPE_MATCH_UPCOMING:
                time.setVisibility(View.VISIBLE);
                scoreContainer.setVisibility(View.GONE);
                setNoWinner();
                break;
        }

    }

    public void setAsScheduleMatchItem(){
        date.setVisibility(View.GONE);
        scoreContainer.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        time.setTextColor(getResources().getColor(R.color.blue));
    }

    public void setNoWinner(){
        blueTeamName.setTextColor(getResources().getColor(R.color.white));
        purpleTeamName.setTextColor(getResources().getColor(R.color.white));
        blueScore.setText("0");
        purpleScore.setText("0");
    }

    public void setBlueWinner(){
        blueTeamName.setTextColor(getResources().getColor(R.color.gold));
        purpleTeamName.setTextColor(getResources().getColor(R.color.white));
    }

    public void setPurpleWinner(){
        blueTeamName.setTextColor(getResources().getColor(R.color.white));
        purpleTeamName.setTextColor(getResources().getColor(R.color.gold));
    }
}
