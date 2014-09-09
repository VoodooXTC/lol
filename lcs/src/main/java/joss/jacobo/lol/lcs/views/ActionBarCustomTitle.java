package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;

/**
 * Created by Joss on 7/24/2014
 */
public class ActionBarCustomTitle extends LinearLayout {

    TextView title;
    TextView subtitle;

    public ActionBarCustomTitle(Context context) {
        this(context, null);
    }

    public ActionBarCustomTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarCustomTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_actionbar_custom_title, this, true);
        title = (TextView) findViewById(R.id.action_bar_title);
        subtitle = (TextView) findViewById(R.id.action_bar_subtitle);
    }

    public void setContent(String title, String subtitle){
        this.title.setText(title);
        this.subtitle.setText(subtitle);
    }

    public void showTitle(){
        title.setVisibility(View.VISIBLE);
    }

    public void hideTitle(){
        title.setVisibility(View.GONE);
    }

    public void showSubtitle(){
        subtitle.setVisibility(View.VISIBLE);
    }

    public void hideSubtitle(){
        subtitle.setVisibility(View.GONE);
    }
}
