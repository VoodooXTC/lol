package joss.jacobo.lol.lcs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.model.Players.Player;
import joss.jacobo.lol.lcs.model.PlayersModel;

/**
 * Created by Joss on 7/27/2014
 */
public class PlayerItem extends LinearLayout {

    Context context;

    ImageView teamLogo;
    TextView name;
    TextView position;
    View divider;

    public PlayerItem(Context context) {
        this(context, null);
    }

    public PlayerItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_item_player, this, true);
        teamLogo = (ImageView) findViewById(R.id.item_player_team_logo);
        name = (TextView) findViewById(R.id.item_player_name);
        position = (TextView) findViewById(R.id.item_player_position);
        divider = findViewById(R.id.divider);
    }

    public void setContent(PlayersModel player){
        Picasso.with(context).load(player.teamLogoUrl).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(teamLogo);
        position.setText(player.playerPosition);
        divider.setVisibility(player.showDivider ? View.VISIBLE : View.INVISIBLE);

        String displayName = player.irlFirstName + " \"" + player.name + "\" " + player.irlLastName;
        if(player.irlFirstName == null || player.irlLastName == null)
            displayName = displayName.replace("\"", "").trim();

        name.setText(displayName);
    }
}
