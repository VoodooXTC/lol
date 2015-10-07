package joss.jacobo.lol.lcs.fragment;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.adapters.OverviewAdapter;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.api.model.TeamDetail;
import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.items.StandingsItem;
import joss.jacobo.lol.lcs.provider.LcsProvider;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.provider.matches.MatchesSelection;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsCursor;
import joss.jacobo.lol.lcs.provider.standings.StandingsSelection;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsSelection;
import joss.jacobo.lol.lcs.views.OverviewMatchDetailsItem;
import joss.jacobo.lol.lcs.views.OverviewSectionTitle;
import joss.jacobo.lol.lcs.views.OverviewStandingsItem;

/**
 * Created by Joss on 7/22/2014
 */
public class OverviewFragment extends BaseListFragment {

    private static final int MATCHES_CALLBACK = 2;
    private static final int STANDINGS_CALLBACK = 3;

    private OverviewAdapter adapter;

    private int selectedTournament;
    private String selectedTournamentAbrev;

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);
        setRetainInstance(true);

        selectedTournament = datastore.getSelectedTournament();
        selectedTournamentAbrev = TournamentsSelection.getTournamentAbrev(getActivity(), selectedTournament);

        listener.onSetActionBarTitle(getString(R.string.overview_actionbar_title), selectedTournamentAbrev);

        setupListView();
        showLoading();
        adapter = new OverviewAdapter(getActivity(), getItems());
        setAdapter(adapter);

        getLoaderManager().initLoader(STANDINGS_CALLBACK, null, new StandingsCallBack());
        getLoaderManager().initLoader(MATCHES_CALLBACK, null, new MatchesCallBack());
        ApiService.getLatestStandings(getActivity());
    }

    public void setSelectedTournament(int tournamentId){
        datastore.persistSelectedTournament(tournamentId);
        selectedTournament = tournamentId;
        selectedTournamentAbrev = TournamentsSelection.getTournamentAbrev(getActivity(), tournamentId);

        adapter.setItems(getItems());
    }

    private List<OverviewItem> getItems(){
        List<OverviewItem> items = new ArrayList<OverviewItem>();

        List<StandingsItem> standings = getStandings(selectedTournamentAbrev);
        if(standings != null && standings.size() > 0){
            items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE,
                    getString(R.string.overview_top), getString(R.string.overview_teams)));
            standings.get(standings.size() - 1).showDivider = false;
            items.addAll(standings);
            showContent();
        }


        List<MatchDetailsItem> latestResults = getLatestResults(selectedTournament);
        if(latestResults != null && latestResults.size() > 0){
            items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE,
                    getString(R.string.overview_latest), getString(R.string.overview_results)));
            latestResults.get(latestResults.size() - 1).showDivider = false;
            items.addAll(latestResults);
            showContent();
        }

        List<MatchDetailsItem> upcomingMatches = getUpcomingMatches(selectedTournament);
        if(upcomingMatches != null && upcomingMatches.size() > 0){
            items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE,
                    getString(R.string.overview_upcoming), getString(R.string.overview_matches)));
            upcomingMatches.get(upcomingMatches.size() - 1).showDivider = false;
            items.addAll(upcomingMatches);
            showContent();
        }

        return items;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OverviewItem item = adapter.items.get(position);
        switch (item.type){
            case OverviewItem.TYPE_STANDINGS:
                StandingsItem standingsItem = (StandingsItem) item;

                TeamDetailsSelection where = new TeamDetailsSelection();
                where.abrev(standingsItem.teamAbrev);
                TeamDetailsCursor cursor = where.query(getActivity().getContentResolver());

                if(cursor.moveToFirst()){
                    listener.teamSelected(cursor.getTeamId());
                }
                cursor.close();

                break;
        }
    }

    private class StandingsCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), StandingsColumns.CONTENT_URI, null, null, null,
                    StandingsColumns.STANDING_POSITION + " ASC, " + StandingsColumns.WINS + " DESC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                selectedTournament = datastore.getSelectedTournament();
                adapter.setItems(getItems());
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private class MatchesCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), MatchesColumns.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                selectedTournament = datastore.getSelectedTournament();
                adapter.setItems(getItems());
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private List<StandingsItem> getStandings(String tournamentAbrev){
        StandingsSelection standingsSelection = new StandingsSelection();
        standingsSelection.tournamentAbrev(selectedTournamentAbrev);
        StandingsCursor c = new StandingsCursor(
                standingsSelection.query(
                        getActivity().getContentResolver(),
                        null,
                        StandingsColumns.STANDING_POSITION + " ASC, " + StandingsColumns.WINS + " DESC"));
        List<StandingsItem> list = c.getListAsStandingItemsTop3();
        c.close();
        return list;
    }

    private List<MatchDetailsItem> getLatestResults(int selectedTournament){
        MatchesSelection matchesSelection = new MatchesSelection();
        matchesSelection.played(1).and().tournamentId(selectedTournament);
        Cursor cursor = getActivity().getContentResolver().query(MatchesColumns.CONTENT_URI,
                MatchesColumns.FULL_PROJECTION,
                matchesSelection.sel(),
                matchesSelection.args(),
                "DATETIME(" + MatchesColumns.DATETIME + ") DESC LIMIT 3");

        MatchesCursor matchesCursor = new MatchesCursor(cursor);
        List<MatchDetailsItem> list = matchesCursor.geListAsMatchDetailsItems(getActivity(), OverviewItem.TYPE_MATCH_RESULTS);
        matchesCursor.close();

        return list;
    }

    private List<MatchDetailsItem> getUpcomingMatches(int selectedTournament){
        MatchesSelection matchesSelection = new MatchesSelection();
        matchesSelection.played(0).and().tournamentId(selectedTournament);
        Cursor cursor = getActivity().getContentResolver().query(MatchesColumns.CONTENT_URI,
                MatchesColumns.FULL_PROJECTION,
                matchesSelection.sel(),
                matchesSelection.args(),
                "DATETIME(" + MatchesColumns.DATETIME + ") ASC LIMIT 3");

        MatchesCursor matchesCursor = new MatchesCursor(cursor);
        List<MatchDetailsItem> list = matchesCursor.geListAsMatchDetailsItems(getActivity(), OverviewItem.TYPE_MATCH_UPCOMING);
        matchesCursor.close();
        return list;
    }

    private Cursor rawQuery(String rawQuery, String[] args){
        ContentProviderClient client = getActivity().getContentResolver().acquireContentProviderClient(LcsProvider.AUTHORITY);
        SQLiteOpenHelper dbHandle = ((LcsProvider)client.getLocalContentProvider()).mLcsSQLiteOpenHelper;
        Cursor cursor = dbHandle.getReadableDatabase().rawQuery(rawQuery,args);
        client.release();
        return cursor;
    }

}
