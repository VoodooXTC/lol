package joss.jacobo.lol.lcs.activity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

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
import joss.jacobo.lol.lcs.purchaseUtils.PurchaseConstants;
import joss.jacobo.lol.lcs.views.DrawerHeader;


public class MainActivity extends BaseDrawerActivity implements DrawerHeader.TournamentListener{

    private static final String FRAGMENT_TAG = "fragment_tag";
    private static final String CURRENT_FRAG = "current_frag";

    private static final int TOURNAMENT_CALLBACK = 0;
    private static final int REQUEST_ADS_FREE_PURCHASE = 1988;

    private int currentFrag = 0;
    private int currentTeam = -1;
    private Fragment frag;

    int selectedTournament;
    private List<TournamentsModel> tournaments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tournaments = new ArrayList<TournamentsModel>();
        selectedTournament = datastore.getSelectedTournament();

        if (savedInstanceState != null)
            currentFrag = savedInstanceState.getInt(CURRENT_FRAG);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            selectFragment(R.id.fragment_overview, getFragmentPosition(DrawerItem.TYPE_OVERVIEW));
        }

        ApiService.getInitialConfig(this);

        getLoaderManager().initLoader(TOURNAMENT_CALLBACK, null, new TournamentCallBack());
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter.setItems(getDrawerItems());
    }

    @Override
    public void onBackPressed(){
        if(currentFrag != R.id.fragment_overview) {
            selectFragment(R.id.fragment_overview, getFragmentPosition(DrawerItem.TYPE_OVERVIEW));
            return;
        }

        super.onBackPressed();
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

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)){
            case ConnectionResult.SUCCESS:
                if(!datastore.isAdsFree()){
                    items.add(new DrawerItem(DrawerItem.TYPE_SECTION_TITLE, 0, getString(R.string.drawer_support)));
                    items.add(new DrawerItem(DrawerItem.TYPE_ADS_FREE, 0, getString(R.string.drawer_ads_fre)));
                }
                break;
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

    @Override
    public void onDrawerItemClicked(View view, int position) {
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

                case DrawerItem.TYPE_ADS_FREE:
                    switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(MainActivity.this)){
                        case ConnectionResult.SUCCESS:
                            Intent intent = new Intent(MainActivity.this, PurchaseAdsFreeActivity.class);
                            intent.putExtra(PurchaseAdsFreeActivity.SKU, PurchaseConstants.SKU_ADS_FREE);
                            startActivityForResult(intent, REQUEST_ADS_FREE_PURCHASE);
                            break;
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ADS_FREE_PURCHASE == requestCode) {
            if (RESULT_OK == resultCode) {
                dealWithSuccessfulPurchase();
            } else {
                dealWithFailedPurchase();
            }
        }
    }

    private void dealWithSuccessfulPurchase() {
        Toast.makeText(this, "Thank you for supporting!", Toast.LENGTH_SHORT).show();
    }

    private void dealWithFailedPurchase() {

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

    private int getFragmentPosition(int type) {
        List<DrawerItem> items = getDrawerItems();
        for(int i = 0; i < items.size(); i++){
            if(items.get(i).type == type){
                return i;
            }
        }
        return 0;
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
