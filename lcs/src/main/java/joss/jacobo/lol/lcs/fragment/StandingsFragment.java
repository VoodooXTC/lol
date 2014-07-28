package joss.jacobo.lol.lcs.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.items.StandingsItem;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsCursor;
import joss.jacobo.lol.lcs.provider.standings.StandingsSelection;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsSelection;
import joss.jacobo.lol.lcs.views.StandingsItemView;

/**
 * Created by Joss on 7/25/2014
 */
public class StandingsFragment extends BaseListFragment {

    private static final int STANDINGS_CALLBACK = 4;

    private StandingsAdapter adapter;

    int selectedTournament;
    String selectedTournamentAbrev;

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);
        setRetainInstance(true);

        selectedTournament = datastore.getSelectedTournament();
        selectedTournamentAbrev = TournamentsSelection.getTournamentAbrev(getActivity(), selectedTournament);
        listener.onSetActionBarTitle("Standings", selectedTournamentAbrev);

        setupListView();
        showLoading();
        adapter = new StandingsAdapter(new ArrayList<StandingsItem>());
        setAdapter(adapter);

        getLoaderManager().initLoader(STANDINGS_CALLBACK, null, new StandingsCallBack());
        ApiService.getLatestStandings(getActivity());
    }

    public class StandingsAdapter extends BaseAdapter {

        public List<StandingsItem> items;

        public StandingsAdapter(List<StandingsItem> items){
            this.items = items;
        }

        public void setItems(List<StandingsItem> newItems) {
            this.items = newItems;
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
            return OverviewItem.TYPE_MAX;
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            StandingsItem item = items.get(position);

            switch (item.type){
                case OverviewItem.TYPE_STANDINGS:

                    StandingsItemView standingsItemView = convertView == null
                            ? new StandingsItemView(getActivity())
                            : (StandingsItemView) convertView;
                    standingsItemView.setContent(item);

                    return standingsItemView;
            }

            return convertView;
        }
    }

    private class StandingsCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            StandingsSelection where = new StandingsSelection();
            where.tournamentAbrev(selectedTournamentAbrev);
            return new CursorLoader(getActivity(), StandingsColumns.CONTENT_URI, null,
                    where.sel(), where.args(), StandingsColumns.STANDING_POSITION);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                StandingsCursor cursor = new StandingsCursor(data);

                List<StandingsItem> items = cursor.getListAsStandingItems();
                for(StandingsItem item : items){
                    item.teamLogoUrl = getTeamLogoUrl(item.teamAbrev);
                }

                adapter.setItems(items);
                showContent();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private String getTeamLogoUrl(String teamAbrev) {
        TeamDetailsSelection where = new TeamDetailsSelection();
        where.abrev(teamAbrev);

        TeamDetailsCursor cursor = new TeamDetailsCursor(where.query(getActivity().getContentResolver()));

        if(cursor.moveToFirst()){
            return cursor.getLogo();
        }

        return null;
    }

}
