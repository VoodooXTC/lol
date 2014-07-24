package joss.jacobo.lol.lcs.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.model.Tournament;
import joss.jacobo.lol.lcs.model.TournamentsModel;

/**
 * Created by jossayjacobo on 7/23/14.
 */
public class DrawerHeader extends LinearLayout implements View.OnClickListener{

    @Inject
    Datastore datastore;

    Context context;
    TextView title;
    LinearLayout subContainer;

    List<TournamentsModel> tournaments;

    TournamentListener listener;

    public boolean subMenuShowing = false;

    public DrawerHeader(Context context) {
        this(context, null);
    }

    public DrawerHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        if(context instanceof TournamentListener){
            listener = (TournamentListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implement TournamentListener");
        }

        LayoutInflater.from(context).inflate(R.layout.view_drawer_header, this, true);
        title = (TextView) findViewById(R.id.drawer_header_title);
        subContainer = (LinearLayout) findViewById(R.id.drawer_sub_containers);
    }

    public void setContent(List<TournamentsModel> tournaments, int selectedTournamentId){

        this.tournaments = tournaments;

        subContainer.removeAllViews();
        for(TournamentsModel tournament : tournaments){

            DrawerHeaderSubItem subItem = new DrawerHeaderSubItem(context);
            subItem.setContent(tournament);
            subItem.setOnClickListener(this);

            subContainer.addView(subItem);

            if(selectedTournamentId == tournament.tournamentId)
                title.setText(tournament.abrev + " - Menu");
        }

    }

    @Override
    public void onClick(View v) {

        int tourneyId = ((DrawerHeaderSubItem) v).getTournamentId();

        hideSub();
        setContent(tournaments, tourneyId);
        listener.onTournamentSelected(tourneyId);

    }

    public static interface TournamentListener {

        public void onTournamentSelected(int tournamentId);

    }

    public void showSub(){
        subMenuShowing = true;
        subContainer.setVisibility(View.VISIBLE);
        subContainer.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }

    public void hideSub(){
        subMenuShowing = false;
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                subContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        subContainer.startAnimation(fadeOutAnimation);
    }
}
