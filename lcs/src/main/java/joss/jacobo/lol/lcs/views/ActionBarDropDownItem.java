package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.model.LiveStreams.Video;

/**
 * Created by Joss on 8/31/2014
 */
public class ActionBarDropDownItem extends LinearLayout {

    TextView title;
    TextView subtitle;

    public ActionBarDropDownItem(Context context) {
        this(context, null);
    }

    public ActionBarDropDownItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarDropDownItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_actionbar_dropdown, this, true);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);
    }

    public void setContent(Video video){
        this.title.setText(video.snippet.title);
        this.subtitle.setVisibility(View.GONE);
    }
}
