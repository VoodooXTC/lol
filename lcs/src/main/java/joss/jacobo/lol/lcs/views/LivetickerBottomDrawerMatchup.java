package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.model.Liveticker.MatchDetails;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;

/**
 * Created by Joss on 9/7/2014
 */
public class LivetickerBottomDrawerMatchup extends LinearLayout {

    @InjectView(R.id.lt_blue_team_logo)
    ImageView blueTeamLogo;
    @InjectView(R.id.lt_blue_team_name)
    TextView blueTeamName;
    @InjectView(R.id.lt_blue_score)
    TextView blueScore;

    @InjectView(R.id.lt_purple_team_logo)
    ImageView purpleTeamLogo;
    @InjectView(R.id.lt_purple_team_name)
    TextView purpleTeamName;
    @InjectView(R.id.lt_purple_score)
    TextView purpleScore;

    @InjectView(R.id.lt_best_of)
    TextView bestOf;

    Context context;

    public LivetickerBottomDrawerMatchup(Context context) {
        this(context, null);
    }

    public LivetickerBottomDrawerMatchup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LivetickerBottomDrawerMatchup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_liveticker_bottom_drawer_matchup, this, true);
        ButterKnife.inject(this);
        this.context = context;
    }

    public void setContent(MatchDetails match){
        TeamDetailsModel blueTeam = getTeam(match.blueTeamId);
        TeamDetailsModel purpleTeam = getTeam(match.purpleTeamId);

        int stub = R.drawable.champion_square_empty;
        if(blueTeam != null){
            Picasso.with(context).load(blueTeam.logo).placeholder(stub).error(stub).into(blueTeamLogo);
            blueTeamName.setText(blueTeam.name);
        }

        if(purpleTeam != null){
            Picasso.with(context).load(purpleTeam.logo).placeholder(stub).error(stub).into(purpleTeamLogo);
            purpleTeamName.setText(purpleTeam.name);
        }

        blueScore.setText(match.blueTeamScore);
        purpleScore.setText(match.purpleTeamScore);
    }

    private TeamDetailsModel getTeam(int teamId){
        TeamDetailsSelection where = new TeamDetailsSelection();
        where.teamId(teamId);

        TeamDetailsCursor cursor = where.query(context.getContentResolver());
        TeamDetailsModel team = null;
        if(cursor.moveToFirst()){
            team = new TeamDetailsModel(cursor);
        }
        cursor.close();

        return  team;
    }
}
