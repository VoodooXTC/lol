package joss.jacobo.lol.lcs.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.fragment.NewsFragment;
import joss.jacobo.lol.lcs.fragment.OverviewFragment;
import joss.jacobo.lol.lcs.fragment.ReplaysFragment;
import joss.jacobo.lol.lcs.fragment.ScheduleFragment;
import joss.jacobo.lol.lcs.fragment.StandingsFragment;
import joss.jacobo.lol.lcs.fragment.TeamsFragment;
import joss.jacobo.lol.lcs.items.DrawerItem;
import joss.jacobo.lol.lcs.model.TournamentsModel;
import joss.jacobo.lol.lcs.provider.teams.TeamsColumns;
import joss.jacobo.lol.lcs.provider.teams.TeamsCursor;
import joss.jacobo.lol.lcs.provider.teams.TeamsSelection;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsColumns;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsCursor;
import joss.jacobo.lol.lcs.views.DrawerHeader;
import joss.jacobo.lol.lcs.views.DrawerItemSectionTitle;
import joss.jacobo.lol.lcs.views.DrawerItemView;


public class MainActivity extends BaseActivity implements DrawerHeader.TournamentListener{

    private static final String FRAGMENT_TAG = "fragment_tag";
    private static final String CURRENT_FRAG = "current_frag";

    private static final int TOURNAMENT_CALLBACK = 0;

    private DrawerLayout mDrawerLayout;
    private FrameLayout contentView;
    private ListView mDrawerList;
    private MenuListAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;

    private int currentFrag = 0;
    private int currentTeam = -1;
    private Fragment frag;

    int selectedTournament;
    private List<TournamentsModel> tournaments;

    private DrawerHeader drawerHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = (FrameLayout) findViewById(R.id.content_container);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list_view);

        tournaments = new ArrayList<TournamentsModel>();
        selectedTournament = datastore.getSelectedTournament();

        setUpDrawerLayout();
        setupActionBar();

        if (savedInstanceState != null)
            currentFrag = savedInstanceState.getInt(CURRENT_FRAG);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            selectFragment(R.id.fragment_overview, 4);
        }

        ApiService.getInitialConfig(this);

        getLoaderManager().initLoader(TOURNAMENT_CALLBACK, null, new TournamentCallBack());
    }

    @Override
    public void onBackPressed(){
        if(currentFrag != R.id.fragment_overview) {
            selectFragment(R.id.fragment_overview, 4);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen((View) mDrawerList.getParent());
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.isDrawerIndicatorEnabled()) {
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setUpDrawerLayout() {

        drawerHeader = new DrawerHeader(this);
        mDrawerList.addHeaderView(drawerHeader);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        adapter = new MenuListAdapter(this, getDrawerItems());
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private List<DrawerItem> getDrawerItems() {
        List<DrawerItem> items = new ArrayList<DrawerItem>();

        items.add(new DrawerItem(DrawerItem.TYPE_SECTION_TITLE, 0, getString(R.string.drawer_media)));
        items.add(new DrawerItem(DrawerItem.TYPE_LIVESTREAM, 0, getString(R.string.drawer_livestream)));
        items.add(new DrawerItem(DrawerItem.TYPE_LIVETICKER, 0, getString(R.string.drawer_liveticker)));
        items.add(new DrawerItem(DrawerItem.TYPE_REPLAYS, 0, getString(R.string.drawer_replays)));

        items.add(new DrawerItem(DrawerItem.TYPE_SECTION_TITLE, 0, getString(R.string.drawer_general)));
        items.add(new DrawerItem(DrawerItem.TYPE_OVERVIEW, 0, getString(R.string.drawer_overview)));
        items.add(new DrawerItem(DrawerItem.TYPE_NEWS, 0, getString(R.string.drawer_news)));
        items.add(new DrawerItem(DrawerItem.TYPE_SCHEDULE_RESULTS, 0, getString(R.string.drawer_schedule_results)));
        items.add(new DrawerItem(DrawerItem.TYPE_STANDINGS, 0, getString(R.string.drawer_standings)));

        List<DrawerItem> teams = getTeams(datastore.getSelectedTournament());
        if(teams != null && teams.size() > 0){
            items.add(new DrawerItem(DrawerItem.TYPE_SECTION_TITLE, 0, getString(R.string.drawer_teams)));
            items.addAll(teams);
        }

        return items;
    }

    @Override
    public void onTournamentSelected(int tournamentId) {
        if(selectedTournament != tournamentId){

            selectedTournament = tournamentId;
            datastore.persistSelectedTournament(tournamentId);
            adapter.setItems(getDrawerItems());

            if(currentFrag == R.id.fragment_overview
                    || currentFrag == R.id.fragment_schedule_results
                    || currentFrag == R.id.fragment_standings){
                replaceFragment(null);
            }else if(currentFrag == R.id.fragment_team){
                // remove the hint but do not reload the team fragment
                adapter.setHint(-1);
            }

        }
    }

    /**
     * Team Selected from a fragment
     * @param teamId - Team Id
     */
    @Override
    public void teamSelected(int teamId) {
        for(int i = 0; i < adapter.items.size(); i++){
            DrawerItem drawerItem = adapter.items.get(i);
            if(drawerItem.type == DrawerItem.TYPE_TEAM && drawerItem.teamId == teamId){
                selectFragment(R.id.fragment_team, i);
                break;
            }
        }
    }

    private void onTeamSelected(int teamId) {
        if(currentTeam != teamId){
            currentTeam = teamId;

            Bundle args = new Bundle();
            args.putInt(TeamsFragment.TEAM_ID, currentTeam);
            replaceFragment(args);
        }
    }

    public class MenuListAdapter extends BaseAdapter {

        private Context context;
        private List<DrawerItem> items;
        private int hintPosition;

        public MenuListAdapter(Context c, List<DrawerItem> i) {
            this.context = c;
            this.items = i;
        }

        public void setItems(List<DrawerItem> items){
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position){
            return items.get(position).type;
        }

        @Override
        public int getViewTypeCount(){
            return DrawerItem.TYPE_MAX;
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void setHint(int position) {
            hintPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            DrawerItem item = items.get(i);

            switch(item.type){
                case DrawerItem.TYPE_SECTION_TITLE:
                    DrawerItemSectionTitle drawerItemSectionTitle = view == null ? new DrawerItemSectionTitle(context) : (DrawerItemSectionTitle) view;
                    drawerItemSectionTitle.setContent(item);
                    return drawerItemSectionTitle;
                default:
                    DrawerItemView drawerItem = view == null ? new DrawerItemView(context) : (DrawerItemView) view;
                    drawerItem.setContent(item);
                    drawerItem.title.setSelected(i == hintPosition);
                    return drawerItem;
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {

                //Compensate for headerView in position 0
                DrawerItem clicked = adapter.items.get(position - 1);

                switch (clicked.type){
                    case DrawerItem.TYPE_LIVESTREAM:
                        selectFragment(R.id.fragment_livestream, position - 1);
                        break;

                    case DrawerItem.TYPE_LIVETICKER:
                        selectFragment(R.id.fragment_liveticker, position - 1);
                        break;

                    case DrawerItem.TYPE_REPLAYS:
                        selectFragment(R.id.fragment_replays, position - 1);
                        break;

                    case DrawerItem.TYPE_OVERVIEW:
                        selectFragment(R.id.fragment_overview, position - 1);
                        break;

                    case DrawerItem.TYPE_NEWS:
                        selectFragment(R.id.fragment_news, position - 1);
                        break;

                    case DrawerItem.TYPE_SCHEDULE_RESULTS:
                        selectFragment(R.id.fragment_schedule_results, position - 1);
                        break;

                    case DrawerItem.TYPE_STANDINGS:
                        selectFragment(R.id.fragment_standings, position - 1);
                        break;

                    case DrawerItem.TYPE_TEAM:
                        selectFragment(R.id.fragment_team, position - 1);
                        break;
                }
            }else if(position == 0){

                DrawerHeader header = (DrawerHeader) view;
                if(header.subMenuShowing){
                    header.hideSub();
                }else{
                    header.showSub();
                }
            }
        }
    }

    public void selectFragment(int nextFrag, int position) {
        if (nextFrag != currentFrag || nextFrag == R.id.fragment_team) {
            switch (nextFrag) {
                case R.id.fragment_livestream:
                    Intent i = new Intent(this, LiveStreamingActivity.class);
                    startActivity(i);
                    closeDrawer();
                    return;
                case R.id.fragment_liveticker:
                    Intent livetickerIntent = new Intent(this, LivetickerActivity.class);
                    startActivity(livetickerIntent);
                    closeDrawer();
                    return;
                case R.id.fragment_replays:
                    currentFrag = R.id.fragment_replays;
                    break;
                case R.id.fragment_overview:
                    currentFrag = R.id.fragment_overview;
                    break;
                case R.id.fragment_news:
                    currentFrag = R.id.fragment_news;
                    break;
                case R.id.fragment_schedule_results:
                    currentFrag = R.id.fragment_schedule_results;
                    break;
                case R.id.fragment_standings:
                    currentFrag = R.id.fragment_standings;
                    break;
                case R.id.fragment_team:
                    currentFrag = R.id.fragment_team;
                    adapter.setHint(position);
                    onTeamSelected(adapter.items.get(position).teamId);
                    return;
                case R.id.fragment_feedback:

//                    closeDrawer();
//
//                    String query = Saguaro.getSendFeedbackIntent(this).getData().toString();
//                    query += Uri.encode("\n");
//                    query += Uri.encode(Device.getFullDeviceName());
//                    query += Uri.encode("\n");
//                    query += Uri.encode(Device.getOSVersion());
//
//                    Intent feedBackIntent = new Intent(Intent.ACTION_SENDTO);
//                    feedBackIntent.setData(Uri.parse(query));
//                    startActivity(feedBackIntent);

                    return;
            }
            currentTeam = 0;
            adapter.setHint(position);
            replaceFragment(null);
        } else {
            closeDrawer();
        }
    }

    private void replaceFragment(Bundle args) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frag = new OverviewFragment();
        switch (currentFrag) {
            case R.id.fragment_livestream:
                break;
            case R.id.fragment_liveticker:
                break;
            case R.id.fragment_replays:
                frag = new ReplaysFragment();
                break;
            case R.id.fragment_news:
                frag = new NewsFragment();
                break;
            case R.id.fragment_schedule_results:
                frag = new ScheduleFragment();
                break;
            case R.id.fragment_standings:
                frag = new StandingsFragment();
                break;
            case R.id.fragment_team:
                frag = new TeamsFragment();
                frag.setArguments(args);
                break;
        }

        ft.setCustomAnimations(R.anim.slide_in_from_right, R.anim.fade_out);
        ft.replace(contentView.getId(), frag, FRAGMENT_TAG);
        ft.commit();
        closeDrawer();
    }

    public void closeDrawer() {
        if (mDrawerLayout.isShown()) {
            mDrawerLayout.closeDrawers();
        }
    }

    private class TournamentCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(MainActivity.this, TournamentsColumns.CONTENT_URI,
                    TournamentsColumns.FULL_PROJECTION, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                TournamentsCursor tournamentsCursor = new TournamentsCursor(data);
                tournaments.clear();
                tournaments.addAll(tournamentsCursor.getList());

                int match = 0;
                for(TournamentsModel t : tournaments){
                    if(t.tournamentId == selectedTournament) {
                        match = 1;
                        break;
                    }
                }

                if(match == 0 || selectedTournament == -1)
                    if(tournaments.size() > 0){
                        selectedTournament = tournaments.get(0).tournamentId;
                        datastore.persistSelectedTournament(selectedTournament);
                    }

                drawerHeader.setContent(tournaments, selectedTournament);
                adapter.setItems(getDrawerItems());

            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private List<DrawerItem> getTeams(int selectedTournament){
        TeamsSelection where = new TeamsSelection();
        where.tournamentId(selectedTournament);
        TeamsCursor cursor = new TeamsCursor(where.query(getContentResolver(), null, TeamsColumns.TEAM_NAME));
        List<DrawerItem> list = cursor.getListAsDrawerItems();
        cursor.close();
        return list;
    }
}
