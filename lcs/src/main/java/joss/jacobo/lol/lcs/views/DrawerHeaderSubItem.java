package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.TournamentsModel;

/**
 * Created by jossayjacobo on 7/23/14.
 */
public class DrawerHeaderSubItem extends LinearLayout {

    TournamentsModel tournament;
    TextView title;

    public DrawerHeaderSubItem(Context context) {
        this(context, null);
    }

    public DrawerHeaderSubItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerHeaderSubItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_drawer_header_sub_item, this, true);
        title = (TextView) findViewById(R.id.drawer_header_sub_item_title);
    }

    public void setContent(TournamentsModel tournament){
        this.tournament = tournament;

        title.setText(tournament.name);
    }

    public int getTournamentId(){
        return tournament.tournamentId;
    }
}
