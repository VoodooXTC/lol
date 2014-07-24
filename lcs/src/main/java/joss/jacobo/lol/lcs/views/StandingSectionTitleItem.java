package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.items.OverviewItem;

/**
 * Created by jossayjacobo on 7/24/14.
 */
public class StandingSectionTitleItem extends LinearLayout {

    TextView title;

    public StandingSectionTitleItem(Context context) {
        this(context, null);
    }

    public StandingSectionTitleItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StandingSectionTitleItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_item_standing_section_title, this, true);
        title = (TextView) findViewById(R.id.standing_section_title);
    }

    public void setContent(OverviewItem item){
        title.setText(item.titleFirstWord);
    }
}
