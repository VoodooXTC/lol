package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.TournamentsModel;

/**
 * Created by jossayjacobo on 7/23/14.
 */
public class DrawerHeader extends LinearLayout implements View.OnClickListener{

    @Inject
    Datastore datastore;

    Context context;
    LinearLayout titleContainer;
    TextView title;
    ImageView dropdownIcon;
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
        titleContainer = (LinearLayout) findViewById(R.id.drawer_header_title_container);
        title = (TextView) findViewById(R.id.drawer_header_title);
        dropdownIcon = (ImageView) findViewById(R.id.drawer_header_dropwown_icon);
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
                title.setText(tournament.abrev + " - " + context.getString(R.string.drawer_header_menu));
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

        Animation rotate180 = AnimationUtils.loadAnimation(context, R.anim.rotate_juan_eighty);
        dropdownIcon.setRotation(180);
        dropdownIcon.startAnimation(rotate180);

        expand(subContainer);
    }

    public void hideSub(){
        subMenuShowing = false;

        Animation rotate180 = AnimationUtils.loadAnimation(context, R.anim.rotate_negative_juan_eighty);
        dropdownIcon.setRotation(0);
        dropdownIcon.startAnimation(rotate180);

        collapse(subContainer);
    }

    private void expand(final View v) {
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation(){
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LayoutParams.WRAP_CONTENT
                        : (int)(targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
