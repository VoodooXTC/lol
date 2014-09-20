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
import joss.jacobo.lol.lcs.provider.players.PlayersColumns;
import joss.jacobo.lol.lcs.provider.players.PlayersCursor;
import joss.jacobo.lol.lcs.provider.players.PlayersSelection;

/**
 * Created by Joss on 9/7/2014
 */
public class LivetickerBottomDrawerPickBans extends LinearLayout {

    @InjectView(R.id.lt_blue_top_player_name)
    TextView blueTopPlayerName;
    @InjectView(R.id.lt_blue_jungle_player_name)
    TextView blueJunglePlayerName;
    @InjectView(R.id.lt_blue_mid_player_name)
    TextView blueMidPlayerName;
    @InjectView(R.id.lt_blue_adc_player_name)
    TextView blueAdcPlayerName;
    @InjectView(R.id.lt_blue_support_player_name)
    TextView blueSupportPlayerName;
    
    @InjectView(R.id.lt_blue_top_champ_name)
    TextView blueTopChampName;
    @InjectView(R.id.lt_blue_jungle_champ_name)
    TextView blueJungleChampName;
    @InjectView(R.id.lt_blue_mid_champ_name)
    TextView blueMidChampName;
    @InjectView(R.id.lt_blue_adc_champ_name)
    TextView blueAdcChampName;
    @InjectView(R.id.lt_blue_support_champ_name)
    TextView blueSupportChampName;

    @InjectView(R.id.lt_purple_top_player_name)
    TextView purpleTopPlayerName;
    @InjectView(R.id.lt_purple_jungle_player_name)
    TextView purpleJunglePlayerName;
    @InjectView(R.id.lt_purple_mid_player_name)
    TextView purpleMidPlayerName;
    @InjectView(R.id.lt_purple_adc_player_name)
    TextView purpleAdcPlayerName;
    @InjectView(R.id.lt_purple_support_player_name)
    TextView purpleSupportPlayerName;

    @InjectView(R.id.lt_purple_top_champ_name)
    TextView purpleTopChampName;
    @InjectView(R.id.lt_purple_jungle_champ_name)
    TextView purpleJungleChampName;
    @InjectView(R.id.lt_purple_mid_champ_name)
    TextView purpleMidChampName;
    @InjectView(R.id.lt_purple_adc_champ_name)
    TextView purpleAdcChampName;
    @InjectView(R.id.lt_purple_support_champ_name)
    TextView purpleSupportChampName;

    @InjectView(R.id.lt_blue_bans)
    TextView blueBans;
    @InjectView(R.id.lt_purple_bans)
    TextView purpleBans;

    Context context;

    public LivetickerBottomDrawerPickBans(Context context) {
        this(context, null);
    }

    public LivetickerBottomDrawerPickBans(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LivetickerBottomDrawerPickBans(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_liveticker_bottom_drawer_pick_bans, this, true);
        ButterKnife.inject(this);
        this.context = context;
    }

    public void setContent(MatchDetails details){
        blueTopPlayerName.setText(getPlayerName(details.blueTeamId, PlayersColumns.POSITION_TOP));
        blueJunglePlayerName.setText(getPlayerName(details.blueTeamId, PlayersColumns.POSITION_JUNGLE));
        blueMidPlayerName.setText(getPlayerName(details.blueTeamId, PlayersColumns.POSITION_MID));
        blueAdcPlayerName.setText(getPlayerName(details.blueTeamId, PlayersColumns.POSITION_ADC));
        blueSupportPlayerName.setText(getPlayerName(details.blueTeamId, PlayersColumns.POSITION_SUPPORT));

        purpleTopPlayerName.setText(getPlayerName(details.purpleTeamId, PlayersColumns.POSITION_TOP));
        purpleJunglePlayerName.setText(getPlayerName(details.purpleTeamId, PlayersColumns.POSITION_JUNGLE));
        purpleMidPlayerName.setText(getPlayerName(details.purpleTeamId, PlayersColumns.POSITION_MID));
        purpleAdcPlayerName.setText(getPlayerName(details.purpleTeamId, PlayersColumns.POSITION_ADC));
        purpleSupportPlayerName.setText(getPlayerName(details.purpleTeamId, PlayersColumns.POSITION_SUPPORT));
        
        blueTopChampName.setText(details.blueTopChampId);
        blueJungleChampName.setText(details.blueJungleChampId);
        blueMidChampName.setText(details.blueMidChampId);
        blueAdcChampName.setText(details.blueAdcChampId);
        blueSupportChampName.setText(details.blueSupportChampId);

        purpleTopChampName.setText(details.purpleTopChampId);
        purpleJungleChampName.setText(details.purpleJungleChampId);
        purpleMidChampName.setText(details.purpleMidChampId);
        purpleAdcChampName.setText(details.purpleAdcChampId);
        purpleSupportChampName.setText(details.purpleSupportChampId);

        blueBans.setText(details.blueBan1 + ", " + details.blueBan2 + ", " + details.blueBan3);
        purpleBans.setText(details.purpleBan1 + ", " + details.purpleBan2 + ", " + details.purpleBan3);
    }

    private String getPlayerName(int teamId, String position){
        PlayersSelection where = new PlayersSelection();
        where.teamId(teamId).and().playerPosition(position).and().active(1);

        PlayersCursor cursor = where.query(context.getContentResolver());
        String playerName = null;

        if(cursor.moveToFirst()){
            playerName = cursor.getName();
        }
        cursor.close();

        return  playerName;
    }

}
