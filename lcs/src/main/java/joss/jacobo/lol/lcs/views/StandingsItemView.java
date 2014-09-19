package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.items.StandingsItem;

/**
 * Created by Joss on 7/25/2014
 */
public class StandingsItemView extends LinearLayout {

    Context context;

    TextView position;
    TextView delta;
    ImageView teamLogo;
    TextView teamName;
    TextView wins;
    TextView losses;

    public StandingsItemView(Context context) {
        this(context, null);
    }

    public StandingsItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StandingsItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_item_standings, this, true);
        position = (TextView) findViewById(R.id.item_match_position);
        delta = (TextView) findViewById(R.id.item_match_delta);
        teamLogo = (ImageView) findViewById(R.id.item_match_team_logo);
        teamName = (TextView) findViewById(R.id.item_match_team_name);
        wins = (TextView) findViewById(R.id.item_match_wins);
        losses = (TextView) findViewById(R.id.item_match_losses);
    }

    public void setContent(StandingsItem standing){

        position.setText(String.valueOf(standing.position));
        teamName.setText(standing.teamName);
        wins.setText(String.valueOf(standing.wins));
        losses.setText(String.valueOf(standing.losses));

        Picasso.with(context).load(standing.teamLogoUrl).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(teamLogo);

        delta.setText(String.valueOf(standing.delta));
        if(standing.delta > 0){
            delta.setText("+" + standing.delta);
            setDeltaAsPositive();
        }else if(standing.delta < 0){
            setDeltaAsNegative();
        }else{
            setDeltaAsNeutral();
        }

    }

    public void setDeltaAsPositive(){
        showDelta();
        delta.setTextColor(getResources().getColor(R.color.green));
    }

    public void setDeltaAsNegative(){
        showDelta();
        delta.setTextColor(getResources().getColor(R.color.red));
    }

    public void setDeltaAsNeutral(){
        hideDelta();
        delta.setText("+3"); // Adding a text value aligns the logos nicely.
        delta.setTextColor(getResources().getColor(R.color.white));
    }

    public void showDelta(){
        delta.setVisibility(View.VISIBLE);
    }

    public void hideDelta(){
        delta.setVisibility(View.INVISIBLE);
    }


}
