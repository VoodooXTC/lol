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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;

/**
 * Created by jossayjacobo on 7/25/14.
 */
public class TeamsFragment extends BaseFragment {

    @InjectView(R.id.teams_view_pager)
    ViewPager viewPager;
    @InjectView(R.id.teams_page_strip)
    PagerTitleStrip pagerTitleStrip;

    String[] titles = {"Overview", "Statistics", "Roster"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams,
                container, false);
        ButterKnife.inject(this, view);

        setHasOptionsMenu(true);

        setupPagerTabStrip();
        setupViewPager();

        return view;
    }

    private void setupViewPager() {
        viewPager.setAdapter(new TeamsPagerAdapter(getChildFragmentManager(), getFragments(), titles));
    }

    private List<TeamBaseFragment> getFragments() {
        List<TeamBaseFragment> fragments = new ArrayList<TeamBaseFragment>();

        Bundle bundle = new Bundle();

        TeamBaseFragment fragment1 = new TeamBaseFragment();
        bundle.putString(TeamBaseFragment.TITLE, "Overview");
        fragment1.setArguments(bundle);

        TeamBaseFragment fragment2 = new TeamBaseFragment();
        bundle.putString(TeamBaseFragment.TITLE, "Statistics");
        fragment2.setArguments(bundle);

        TeamBaseFragment fragment3 = new TeamBaseFragment();
        bundle.putString(TeamBaseFragment.TITLE, "Roster");
        fragment3.setArguments(bundle);

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        return fragments;
    }

    private void setupPagerTabStrip() {
        pagerTitleStrip.setBackgroundColor(getResources().getColor(R.color.purple_dark));
        pagerTitleStrip.setNonPrimaryAlpha(0.1f);
        pagerTitleStrip.setTextSpacing(25);
        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        pagerTitleStrip.setTextColor(getResources().getColor(R.color.white));
    }

    public class TeamsPagerAdapter extends FragmentPagerAdapter{

        List<TeamBaseFragment> fragments;
        String[] titles;

        public TeamsPagerAdapter(FragmentManager fm, List<TeamBaseFragment> frags, String[] titles) {
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
