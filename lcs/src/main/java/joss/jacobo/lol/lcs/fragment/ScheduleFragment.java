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
import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.items.StandingsItem;
import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.provider.matches.MatchesSelection;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.views.OverviewStandingsItem;
import joss.jacobo.lol.lcs.views.StandingSectionTitleItem;

/**
 * Created by jossayjacobo on 7/24/14.
 */
public class ScheduleFragment extends BaseListFragment {

    private static final int MATCHES_CALLBACK = 3;

    int selectedTournament;
    MatchesAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);

        selectedTournament = datastore.getSelectedTournament();

        setupListView();
        showLoading();
        adapter = new MatchesAdapter(new ArrayList<OverviewItem>());
        setAdapter(adapter);

        getLoaderManager().initLoader(MATCHES_CALLBACK, null, new MatchesCallBack());
        ApiService.getLatestStandings(getActivity());
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
                case OverviewItem.TYPE_STANDINGS:
                    StandingsItem standingsItem = (StandingsItem) item;

                    OverviewStandingsItem overviewStandingsItem = convertView == null
                            ? new OverviewStandingsItem(getActivity())
                            : (OverviewStandingsItem) convertView;
                    overviewStandingsItem.setContent(standingsItem);

                    return overviewStandingsItem;

                case OverviewItem.TYPE_SECTION_TITLE_STANDINGS:
                    StandingSectionTitleItem standingSectionTitleItem = convertView == null
                            ? new StandingSectionTitleItem(getActivity())
                            : (StandingSectionTitleItem) convertView;
                    standingSectionTitleItem.setContent(item);
                    return standingSectionTitleItem;
            }

            return convertView;
        }
    }

    private class MatchesCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            MatchesSelection where = new MatchesSelection();
            where.tournamentId(selectedTournament);

            return new CursorLoader(getActivity(), StandingsColumns.CONTENT_URI,
                    StandingsColumns.FULL_PROJECTION,
                    where.sel(),
                    where.args(),
                    "DATETIME(" + MatchesColumns.DATETIME + ") DESC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                MatchesCursor cursor = new MatchesCursor(data);
                adapter.setItems(getItems(cursor.getList()));
                showContent();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private List<OverviewItem> getItems(List<MatchesModel> list) {
        List<OverviewItem> items = new ArrayList<OverviewItem>();

        String datetime = "";
        for(MatchesModel match : list){
            // Add section title if datetime is new
            if(!match.datetime.equals(datetime)){
                items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE_STANDINGS, match.datetime, null));
                datetime = match.datetime;
            }
            items.add(new MatchDetailsItem(match, match.played == 0 ? OverviewItem.TYPE_MATCH_RESULTS : OverviewItem.TYPE_MATCH_UPCOMING));
        }
        return items;
    }
}
