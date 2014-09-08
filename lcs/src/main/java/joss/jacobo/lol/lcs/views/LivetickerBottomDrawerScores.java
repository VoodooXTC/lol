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

/**
 * Created by Joss on 9/7/2014
 */
public class LivetickerBottomDrawerScores extends LinearLayout {

    @InjectView(R.id.lt_blue_baron_kills)
    TextView blueBaronKills;
    @InjectView(R.id.lt_blue_dragon_kills)
    TextView blueDragonKills;

    @InjectView(R.id.lt_purple_baron_kills)
    TextView purpleBaronKills;
    @InjectView(R.id.lt_purple_dragon_kills)
    TextView purpleDragonKills;

    @InjectView(R.id.lt_blue_top_score)
    TextView blueTopScore;
    @InjectView(R.id.lt_blue_jungle_score)
    TextView blueJungleScore;
    @InjectView(R.id.lt_blue_mid_score)
    TextView blueMidScore;
    @InjectView(R.id.lt_blue_adc_score)
    TextView blueAdcScore;
    @InjectView(R.id.lt_blue_support_score)
    TextView blueSupportScore;

    @InjectView(R.id.lt_purple_top_score)
    TextView purpleTopScore;
    @InjectView(R.id.lt_purple_jungle_score)
    TextView purpleJungleScore;
    @InjectView(R.id.lt_purple_mid_score)
    TextView purpleMidScore;
    @InjectView(R.id.lt_purple_adc_score)
    TextView purpleAdcScore;
    @InjectView(R.id.lt_purple_support_score)
    TextView purpleSupportScore;

    @InjectView(R.id.lt_blue_top_image)
    ImageView blueTopChampIcon;
    @InjectView(R.id.lt_blue_jungle_image)
    ImageView blueJungleChampIcon;
    @InjectView(R.id.lt_blue_mid_image)
    ImageView blueMidChampIcon;
    @InjectView(R.id.lt_blue_adc_image)
    ImageView blueAdcChampIcon;
    @InjectView(R.id.lt_blue_support_image)
    ImageView blueSupportChampIcon;
    
    @InjectView(R.id.lt_purple_top_image)
    ImageView purpleTopChampIcon;
    @InjectView(R.id.lt_purple_jungle_image)
    ImageView purpleJungleChampIcon;
    @InjectView(R.id.lt_purple_mid_image)
    ImageView purpleMidChampIcon;
    @InjectView(R.id.lt_purple_adc_image)
    ImageView purpleAdcChampIcon;
    @InjectView(R.id.lt_purple_support_image)
    ImageView purpleSupportChampIcon;
    
    Context context;
    
    public LivetickerBottomDrawerScores(Context context) {
        this(context, null);
    }

    public LivetickerBottomDrawerScores(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LivetickerBottomDrawerScores(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_liveticker_bottom_drawer_scores, this, true);
        ButterKnife.inject(this);
        this.context = context;
    }
    
    public void setContent(MatchDetails match){
        blueBaronKills.setText(match.blueBarons);
        blueDragonKills.setText(match.blueDragons);

        purpleBaronKills.setText(match.purpleBarons);
        purpleDragonKills.setText(match.purpleDragons);

        blueTopScore.setText(match.blueTopScore);
        blueJungleScore.setText(match.blueJungleScore);
        blueMidScore.setText(match.blueMidScore);
        blueAdcScore.setText(match.blueAdcScore);
        blueSupportScore.setText(match.blueSupportScore);

        purpleTopScore.setText(match.purpleTopScore);
        purpleJungleScore.setText(match.purpleJungleScore);
        purpleMidScore.setText(match.purpleMidScore);
        purpleAdcScore.setText(match.purpleAdcScore);
        purpleSupportScore.setText(match.purpleSupportScore);

        int stub = R.drawable.champion_square_empty;
        Picasso.with(context).load(match.blueTopChampImage).placeholder(stub).error(stub).into(blueTopChampIcon);
        Picasso.with(context).load(match.blueJungleChampImage).placeholder(stub).error(stub).into(blueJungleChampIcon);
        Picasso.with(context).load(match.blueMidChampImage).placeholder(stub).error(stub).into(blueMidChampIcon);
        Picasso.with(context).load(match.blueAdcChampImage).placeholder(stub).error(stub).into(blueAdcChampIcon);
        Picasso.with(context).load(match.blueSupportChampImage).placeholder(stub).error(stub).into(blueSupportChampIcon);

        Picasso.with(context).load(match.purpleTopChampImage).placeholder(stub).error(stub).into(purpleTopChampIcon);
        Picasso.with(context).load(match.purpleJungleChampImage).placeholder(stub).error(stub).into(purpleJungleChampIcon);
        Picasso.with(context).load(match.purpleMidChampImage).placeholder(stub).error(stub).into(purpleMidChampIcon);
        Picasso.with(context).load(match.purpleAdcChampImage).placeholder(stub).error(stub).into(purpleAdcChampIcon);
        Picasso.with(context).load(match.purpleSupportChampImage).placeholder(stub).error(stub).into(purpleSupportChampIcon);
    }
}
