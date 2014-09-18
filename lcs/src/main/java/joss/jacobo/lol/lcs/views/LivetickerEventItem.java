package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.model.Liveticker.Event;

/**
 * Created by Joss on 9/7/2014
 */
public class LivetickerEventItem extends LinearLayout {

    Map<String, Integer> type;

    TextView time;
    ImageView icon;
    TextView title;
    TextView text;
    View divider;

    public LivetickerEventItem(Context context) {
        this(context, null);
    }

    public LivetickerEventItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LivetickerEventItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        type = getTypes();
        init(context);
    }

    private Map<String, Integer> getTypes() {
        Map<String, Integer> types = new HashMap<String, Integer>();

        types.put(getContext().getString(R.string.liveticker_dragon), R.drawable.dragon_icon);
        types.put(getContext().getString(R.string.liveticker_baron), R.drawable.baron_icon);
        types.put(getContext().getString(R.string.liveticker_team_fight), R.drawable.teamfight_icon);
        types.put(getContext().getString(R.string.liveticker_kill), R.drawable.kills_versus_icon);
        types.put(getContext().getString(R.string.liveticker_tower), R.drawable.turret_icon);
        types.put(getContext().getString(R.string.liveticker_inhibitor), R.drawable.inhibitor_icon);
        types.put(getContext().getString(R.string.liveticker_buff), R.drawable.inhibitor_icon);
        types.put(getContext().getString(R.string.liveticker_nexus), R.drawable.inhibitor_icon);
        types.put(getContext().getString(R.string.liveticker_first_blood), R.drawable.firstblood_icon);

        return types;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_item_liveticker_event, this, true);
        time = (TextView) findViewById(R.id.liveticker_event_time);
        icon = (ImageView) findViewById(R.id.liveticker_event_icon);
        title = (TextView) findViewById(R.id.liveticker_event_title);
        text = (TextView) findViewById(R.id.liveticker_event_text);
        divider = findViewById(R.id.liveticker_event_hint);
    }

    public void setContent(Event event){

        time.setVisibility(event.time == null  || event.time.equals("")
                ? View.GONE : View.VISIBLE);
        title.setVisibility(event.title == null || event.title.equals("")
                ? View.GONE : View.VISIBLE);
        text.setVisibility(event.event == null || event.event.equals("")
                ? View.GONE : View.VISIBLE);

        time.setText(event.time);
        title.setText(event.title);
        text.setText(event.event);
        if(type.containsKey(event.type)){
            icon.setImageResource(type.get(event.type));
        }else{
            icon.setImageResource(0);
        }
    }

    public void showDivider(boolean showDivider){
        divider.setVisibility(showDivider ? View.VISIBLE : View.GONE);
    }
}
