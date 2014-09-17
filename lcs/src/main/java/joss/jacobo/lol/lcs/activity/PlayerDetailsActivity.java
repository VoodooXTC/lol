package joss.jacobo.lol.lcs.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.PlayersModel;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;

/**
 * Created by Joss on 8/2/2014
 */
public class PlayerDetailsActivity extends BaseActivity {

    public static final String PLAYER = "player";
    public static final String TEAM_DETAILS = "team_details";

    @InjectView(R.id.player_details_image)
    ImageView image;
    @InjectView(R.id.player_details_position)
    TextView position;
    @InjectView(R.id.player_details_name)
    TextView name;
    @InjectView(R.id.player_details_quote)
    TextView quote;
    @InjectView(R.id.player_details_text)
    TextView text;

    PlayersModel player;
    TeamDetailsModel teamDetails;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);
        ButterKnife.inject(this);

        Gson gson = new Gson();
        player = gson.fromJson(getIntent().getStringExtra(PLAYER), PlayersModel.class);
        teamDetails = gson.fromJson(getIntent().getStringExtra(TEAM_DETAILS), TeamDetailsModel.class);

        setContent(player, teamDetails);
        setupActionBar();
        onSetActionBarTitle(teamDetails.name, null);

    }

    private void setContent(PlayersModel player, TeamDetailsModel teamDetails) {
        Picasso.with(this).load(player.image).placeholder(R.drawable.blank_silhouette).error(R.drawable.blank_silhouette).into(image);
        position.setText(player.playerPosition);

        if(player.famousQuote == null){
            quote.setVisibility(View.GONE);
        }else{
            quote.setText(player.famousQuote);
        }

        String displayName = player.irlFirstName + " \"" + player.name + "\" " + player.irlLastName;
        if(player.irlFirstName == null || player.irlLastName == null)
            displayName = displayName.replace("\"", "").trim();
        name.setText(displayName);

        if(player.description != null){
            text.setText(Html.fromHtml(player.description));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
