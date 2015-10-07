package joss.jacobo.lol.lcs.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.model.TeamsModel;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.utils.GGson;

/**
 * Created by jossayjacobo on 7/25/14
 */
public class TeamAboutFragment extends Fragment {

    @InjectView(R.id.team_about_image)
    ImageView image;
    @InjectView(R.id.team_about_title)
    TextView title;
    @InjectView(R.id.team_about_text1)
    TextView text1;

    TeamsModel team;
    TeamDetailsModel teamDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        team = GGson.fromJson(getArguments().getString(TeamsFragment.TEAM), TeamsModel.class);
        teamDetail = TeamDetailsSelection.getTeamDetails(getActivity().getContentResolver(), team.teamId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_team, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedState) {
        super.onViewCreated(view, savedState);
        setContent();
    }

    private void setContent() {
        Picasso.with(getActivity()).load(teamDetail.teamPicture).into(image);
        title.setText(teamDetail.name);
        text1.setText(teamDetail.aboutText);
    }
}
