package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.interfaces.StandingsType;
import joss.jacobo.lol.lcs.items.OverviewItem;

/**
 * Created by jossayjacobo on 7/24/14.
 */
public class ScheduleMatchSectionTitleItem extends LinearLayout {

    TextView title;

    public ScheduleMatchSectionTitleItem(Context context) {
        this(context, null);
    }

    public ScheduleMatchSectionTitleItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleMatchSectionTitleItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_item_schedule_match_section_title, this, true);
        title = (TextView) findViewById(R.id.standing_section_title);
    }

    public void setContent(OverviewItem item){
        title.setText(item.titleFirstWord);
    }

    public void setContent(StandingsType item) {
        title.setText(item.getSeparatorText());
    }
}
