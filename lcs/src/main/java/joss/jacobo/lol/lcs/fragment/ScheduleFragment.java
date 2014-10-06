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

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.provider.matches.MatchesSelection;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsSelection;
import joss.jacobo.lol.lcs.utils.DateTimeFormatter;
import joss.jacobo.lol.lcs.views.OverviewMatchDetailsItem;
import joss.jacobo.lol.lcs.views.ScheduleMatchSectionTitleItem;

/**
 * Created by jossayjacobo on 7/24/14
 */
public class ScheduleFragment extends BaseListFragment {

    private static final int MATCHES_CALLBACK = 3;

    int selectedTournament;
    String selectedTournamentAbrev;
    MatchesAdapter adapter;

    int scrollToItem = -1;
    boolean scrolled = false;

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);
        setRetainInstance(true);

        selectedTournament = datastore.getSelectedTournament();
        selectedTournamentAbrev = TournamentsSelection.getTournamentAbrev(getActivity(), selectedTournament);
        listener.onSetActionBarTitle(getString(R.string.schedule_actionbar_title), selectedTournamentAbrev);

        setupListView();
        showLoading();
        adapter = new MatchesAdapter(new ArrayList<OverviewItem>());
        setAdapter(adapter);

        getLoaderManager().initLoader(MATCHES_CALLBACK, null, new MatchesCallBack());
    }

    public class MatchesAdapter extends BaseAdapter {

        public List<OverviewItem> items;

        public MatchesAdapter(List<OverviewItem> items){
            this.items = items;
        }

        public void setItems(List<OverviewItem> newItems) {
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

            OverviewItem item = items.get(position);

            switch (item.type){
                case OverviewItem.TYPE_MATCH_RESULTS:
                    MatchDetailsItem matchDetailsItem = (MatchDetailsItem) item;

                    OverviewMatchDetailsItem overviewMatchDetailsItem = convertView == null
                            ? new OverviewMatchDetailsItem(getActivity())
                            : (OverviewMatchDetailsItem) convertView;
                    overviewMatchDetailsItem.setContent(matchDetailsItem);
                    overviewMatchDetailsItem.setAsScheduleMatchItem();
                    return overviewMatchDetailsItem;

                case OverviewItem.TYPE_MATCH_UPCOMING:
                    MatchDetailsItem matchDetailsUpcomingItem = (MatchDetailsItem) item;

                    OverviewMatchDetailsItem overviewMatchDetailsUpcomingItem = convertView == null
                            ? new OverviewMatchDetailsItem(getActivity())
                            : (OverviewMatchDetailsItem) convertView;
                    overviewMatchDetailsUpcomingItem.setContent(matchDetailsUpcomingItem);
                    overviewMatchDetailsUpcomingItem.setAsScheduleMatchItem();
                    return overviewMatchDetailsUpcomingItem;

                case OverviewItem.TYPE_SECTION_TITLE_SCHEDULE_MATCHES:
                    ScheduleMatchSectionTitleItem scheduleMatchSectionTitleItem = convertView == null
                            ? new ScheduleMatchSectionTitleItem(getActivity())
                            : (ScheduleMatchSectionTitleItem) convertView;
                    scheduleMatchSectionTitleItem.setContent(item);

                    return scheduleMatchSectionTitleItem;
            }

            return convertView;
        }
    }

    private class MatchesCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            MatchesSelection where = new MatchesSelection();
            where.tournamentId(selectedTournament);

            return new CursorLoader(getActivity(), MatchesColumns.CONTENT_URI,
                    MatchesColumns.FULL_PROJECTION,
                    where.sel(),
                    where.args(),
                    "DATETIME(" + MatchesColumns.DATETIME + ") ASC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                MatchesCursor cursor = new MatchesCursor(data);
                adapter.setItems(getItems(cursor.getList()));
                showContent();

                if(adapter.items.size() > 0 && scrollToItem != -1 && !scrolled){
                    scrolled = true;
                    listView.setSelection(scrollToItem);
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private List<OverviewItem> getItems(List<MatchesModel> list) {
        List<OverviewItem> items = new ArrayList<OverviewItem>();

        String date = "";
        for(MatchesModel match : list){

            // Add section title if datetime is new
            String matchDate = DateTimeFormatter.formatDatetimeToDate(match.datetime);
            if(!matchDate.equals(date)){
                items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE_SCHEDULE_MATCHES, match.week + " - " + matchDate));
                date = matchDate;

                // Hide the bottom divider of the previous item
                if(items.size() > 1){
                    items.get(items.size() - 2).showDivider = false;
                }
            }
            items.add(new MatchDetailsItem(getActivity(), match, match.played == 1 ? OverviewItem.TYPE_MATCH_RESULTS : OverviewItem.TYPE_MATCH_UPCOMING));

            if(match.played == 1){
                scrollToItem = items.size() - 1;
            }

        }

        // Also remove the last divider
        if(items.size() > 0){
            items.get(items.size() - 1).showDivider = false;
        }

        return items;
    }
}
