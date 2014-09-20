package joss.jacobo.lol.lcs.fragment;

import android.database.Cursor;
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

import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.interfaces.StandingsType;
import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.items.StandingsItem;
import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsCursor;
import joss.jacobo.lol.lcs.provider.standings.StandingsSelection;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsSelection;
import joss.jacobo.lol.lcs.utils.DateTimeFormatter;
import joss.jacobo.lol.lcs.views.ScheduleMatchSectionTitleItem;
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
        adapter = new StandingsAdapter(new ArrayList<StandingsType>());
        setAdapter(adapter);

        getLoaderManager().initLoader(STANDINGS_CALLBACK, null, new StandingsCallBack());
        ApiService.getLatestStandings(getActivity());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StandingsType item = adapter.items.get(position);
        switch (item.getType()){
            case StandingsType.TYPE_ITEM:
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

    public class StandingsAdapter extends BaseAdapter {

        public List<StandingsType> items;

        public StandingsAdapter(List<StandingsType> items){
            this.items = items;
        }

        public void setItems(List<StandingsType> newItems) {
            this.items = newItems;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position){
            return items.get(position).getType();
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

            StandingsType item = items.get(position);

            switch (item.getType()){
                case StandingsType.TYPE_ITEM:

                    StandingsItemView standingsItemView = convertView == null
                            ? new StandingsItemView(getActivity())
                            : (StandingsItemView) convertView;
                    standingsItemView.setContent((StandingsItem) item);

                    return standingsItemView;

                case StandingsType.TYPE_SEPARATOR:

                    ScheduleMatchSectionTitleItem scheduleMatchSectionTitleItem = convertView == null
                            ? new ScheduleMatchSectionTitleItem(getActivity())
                            : (ScheduleMatchSectionTitleItem) convertView;
                    scheduleMatchSectionTitleItem.setContent(item);

                    return scheduleMatchSectionTitleItem;
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
                    where.sel(), where.args(),
                    StandingsColumns.TOURNAMENT_GROUP + " ASC, " + StandingsColumns.STANDING_POSITION + " ASC ");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                StandingsCursor cursor = new StandingsCursor(data);
                List<StandingsItem> items = cursor.getListAsStandingItems();

                for(StandingsItem item : items){
                    item.teamLogoUrl = getTeamLogoUrl(item.teamAbrev);
                }

                adapter.setItems(getItemsWithSeparators(items));
                showContent();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private List<StandingsType> getItemsWithSeparators(List<StandingsItem> list) {
        List<StandingsType> items = new ArrayList<StandingsType>();

        String separator = "";
        for(StandingsItem standing : list){

            // Add section title if separator text is new
            if(standing.getSeparatorText() != null){
                String matchSeparator = standing.getSeparatorText();
                if(!matchSeparator.equals(separator)){
                    items.add(new StandingsItem(matchSeparator, StandingsType.TYPE_SEPARATOR));
                    separator = matchSeparator;

                    // Hide the bottom divider of the previous item before the separator
                    if(items.size() > 1){
                        ((StandingsItem) items.get(items.size() - 2)).showDivider = false;
                    }
                }
            }

            items.add(standing);

        }

        // Also remove the last divider
        if(items.size() > 0){
            ((StandingsItem) items.get(items.size() - 1)).showDivider = false;
        }

        return items;
    }

    private String getTeamLogoUrl(String teamAbrev) {
        TeamDetailsSelection where = new TeamDetailsSelection();
        where.abrev(teamAbrev);

        TeamDetailsCursor cursor = new TeamDetailsCursor(where.query(getActivity().getContentResolver()));

        if(cursor.moveToFirst()){
            return cursor.getLogo();
        }
        cursor.close();

        return null;
    }

}
