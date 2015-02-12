package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.model.PlayersModel;

/**
 * Created by jossayjacobo on 7/23/14
 */
public class OverviewSectionTitle extends LinearLayout {

    TextView firstWord;
    TextView secondWord;

    public OverviewSectionTitle(Context context) {
        this(context, null);
    }

    public OverviewSectionTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverviewSectionTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.li_overview_section_title, this, true);
        firstWord = (TextView) findViewById(R.id.item_overview_section_title_first_word);
        secondWord = (TextView) findViewById(R.id.item_overview_section_title_second_word);
    }

    public void setContent(OverviewItem item){
        firstWord.setText(item.titleFirstWord);
        secondWord.setText(item.titleSecondWord);
    }

    public void setContent(PlayersModel player) {
        firstWord.setText(player.titleFirstWord);
        secondWord.setText(player.titleSecondWord);
    }
}
