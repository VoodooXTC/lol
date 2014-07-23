package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import joss.jacobo.lol.lcs.R;

/**
 * Created by Joss on 7/21/2014.
 */
public class DrawerItemSectionTitle extends LinearLayout {

    TextView title;

    public DrawerItemSectionTitle(Context context) {
        this(context, null);
    }

    public DrawerItemSectionTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerItemSectionTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_drawer_item_section_title, this, true);
        title = (TextView) findViewById(R.id.drawer_item_title);
    }

    public void setContent(joss.jacobo.lol.lcs.items.DrawerItem item){
        this.title.setText(item.title.toUpperCase());
    }

}
