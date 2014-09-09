package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;

/**
 * Created by Joss on 9/8/2014
 */
public class ActionBarCustomCenteredTitle extends LinearLayout {

    private static final int TITLE_SHOWN = 0;
    private static final int MATCH_SHOWN = 1;

    int state = TITLE_SHOWN;

    Context context;
    TextView title;

    RelativeLayout matchContainer;
    TextView team1;
    TextView vs;
    TextView team2;

    public ActionBarCustomCenteredTitle(Context context) {
        this(context, null);
    }

    public ActionBarCustomCenteredTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarCustomCenteredTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1f;
        setLayoutParams(params);

        LayoutInflater.from(context).inflate(R.layout.view_actionbar_custom_title_centered, this, true);
        title = (TextView) findViewById(R.id.action_bar_title);
        matchContainer = (RelativeLayout) findViewById(R.id.action_bar_match_container);
        team1 = (TextView) findViewById(R.id.action_bar_team_1);
        vs = (TextView) findViewById(R.id.action_bar_vs);
        team2 = (TextView) findViewById(R.id.action_bar_team_2);
    }

    public void setContent(String title, String team1, String team2){
        this.title.setText(title);
        this.team1.setText(team1);
        this.team2.setText(team2);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setMatch(String team1, String team2){
        this.team1.setText(team1);
        this.team2.setText(team2);
    }

    public void showTitle(){
        state = TITLE_SHOWN;
        title.setVisibility(VISIBLE);
        title.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                matchContainer.setVisibility(INVISIBLE);
            }
        }).start();
        matchContainer.animate().alpha(0f).setDuration(1000).start();
    }

    public void showMatch(){
        state = MATCH_SHOWN;
        matchContainer.setVisibility(VISIBLE);
        title.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
            @Override
            public void run() {
                title.setVisibility(INVISIBLE);
            }
        }).start();
        matchContainer.animate().alpha(1f).setDuration(1000).start();
    }

    public void toggle(){
        if(state == TITLE_SHOWN){
            showMatch();
        }else{
            showTitle();
        }
    }
}
