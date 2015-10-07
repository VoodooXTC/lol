package joss.jacobo.lol.lcs.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.model.TeamsModel;
import joss.jacobo.lol.lcs.provider.teams.TeamsCursor;
import joss.jacobo.lol.lcs.provider.teams.TeamsSelection;

/**
 * Created by jossayjacobo on 7/25/14
 */
public class TeamsFragment extends BaseFragment {

    public static final String TEAM_ID = "team_id";
    public static final String TEAM = "team";

    @InjectView(R.id.teams_view_pager)
    ViewPager viewPager;
    @InjectView(R.id.teams_page_strip)
    PagerTitleStrip pagerTitleStrip;

    String[] titles;

    int teamId;
    TeamsModel team;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        teamId = getArguments().getInt(TEAM_ID);
        titles = getResources().getStringArray(R.array.team_fragment_titles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams,
                container, false);
        ButterKnife.inject(this, view);
        setHasOptionsMenu(true);

        team = getTeam(teamId);

        listener.onSetActionBarTitle(team != null ? team.teamName : "", null);
        setupPagerTabStrip();
        setupViewPager();

        return view;
    }

    private TeamsModel getTeam(int teamId) {
        TeamsSelection where = new TeamsSelection();
        where.teamId(teamId);
        TeamsCursor teamsCursor = where.query(getActivity().getContentResolver());

        TeamsModel team = new TeamsModel();
        if(teamsCursor.moveToFirst()){
            team = new TeamsModel(teamsCursor);
        }
        teamsCursor.close();
        return team;
    }

    private void setupPagerTabStrip() {
        pagerTitleStrip.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        pagerTitleStrip.setNonPrimaryAlpha(0.5f);
        pagerTitleStrip.setTextSpacing(25);
        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        pagerTitleStrip.setTextColor(getResources().getColor(R.color.white));
    }

    private void setupViewPager() {
        viewPager.setAdapter(new TeamsPagerAdapter(getChildFragmentManager(), getFragments(), titles));
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(5);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<Fragment>();

        Gson gson = new Gson();
        Bundle arguments = new Bundle();
        arguments.putString(TEAM, gson.toJson(team));

        TeamAboutFragment about = new TeamAboutFragment();
        about.setArguments(arguments);

        TeamRosterFragment roster = new TeamRosterFragment();
        roster.setArguments(arguments);

        TeamSocialFragment overview = new TeamSocialFragment();
        overview.setArguments(arguments);

        fragments.add(roster);
        fragments.add(about);
        fragments.add(overview);

        return fragments;
    }

    public class TeamsPagerAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;
        String[] titles;

        public TeamsPagerAdapter(FragmentManager fm, List<Fragment> frags, String[] titles) {
            super(fm);
            this.fragments = frags;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return titles[position];
        }
    }

}
