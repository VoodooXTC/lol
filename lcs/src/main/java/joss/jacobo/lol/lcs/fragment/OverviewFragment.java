package joss.jacobo.lol.lcs.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.items.StandingsItem;
import joss.jacobo.lol.lcs.views.OverviewMatchDetailsItem;
import joss.jacobo.lol.lcs.views.OverviewSectionTitle;
import joss.jacobo.lol.lcs.views.OverviewStandingsItem;

/**
 * Created by Joss on 7/22/2014.
 */
public class OverviewFragment extends BaseListFragment {

    private List<OverviewItem> items;
    private OverviewAdapter adapter;

    @Override
    public void onCreate(Bundle savedIntance){
        super.onCreate(savedIntance);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState){
        setupListView();
        showLoading();
        adapter = new OverviewAdapter(getItems());
        setAdapter(adapter);
        showContent();
    }

    private List<OverviewItem> getItems(){
        List<OverviewItem> items = new ArrayList<OverviewItem>();

        items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE, "TOP", "TEAMS"));
        items.add(new StandingsItem(OverviewItem.TYPE_STANDINGS, "NA-LCS", 8, 1, "Alliance", 14, 4));
        items.add(new StandingsItem(OverviewItem.TYPE_STANDINGS, "NA-LCS", 8, 2, "SK Gaming", 11, 7));
        items.add(new StandingsItem(OverviewItem.TYPE_STANDINGS, "NA-LCS", 8, 3, "Alliance", 11, 7));

        items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE, "LATEST", "RESULTS"));
        items.add(new MatchDetailsItem(OverviewItem.TYPE_MATCH_RESULTS, "Sk Gaming", "0", "Alliance", "1", "Saturday, July 21", "18:00", 1));
        items.add(new MatchDetailsItem(OverviewItem.TYPE_MATCH_RESULTS, "Supa Hot Crew", "1", "Copahagen Wolves", "0", "Saturday, July 21", "18:00", 0));
        items.add(new MatchDetailsItem(OverviewItem.TYPE_MATCH_RESULTS, "Millenium", "0", "Roccat", "1", "Saturday, July 21", "18:00", 1));

        items.add(new OverviewItem(OverviewItem.TYPE_SECTION_TITLE, "UPCOMING", "MATCHES"));
        items.add(new MatchDetailsItem(OverviewItem.TYPE_MATCH_UPCOMING, "SK Gaming", "0", "Alliance", "0", "Saturday, July 21", "18:00", 1));
        items.add(new MatchDetailsItem(OverviewItem.TYPE_MATCH_UPCOMING, "Supa Hot Crew", "0", "Copahagen Wolves", "0", "Saturday, July 21", "18:00", 1));
        items.add(new MatchDetailsItem(OverviewItem.TYPE_MATCH_UPCOMING, "Millenium", "0", "Roccat", "0", "Saturday, July 21", "18:00", 1));

        return items;
    }

    public class OverviewAdapter extends BaseAdapter {

        public List<OverviewItem> items;

        public OverviewAdapter(List<OverviewItem> items){
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

                case OverviewItem.TYPE_MATCH_RESULTS:
                    MatchDetailsItem matchDetailsItem = (MatchDetailsItem) item;

                    OverviewMatchDetailsItem overviewMatchDetailsItem = convertView == null
                            ? new OverviewMatchDetailsItem(getActivity())
                            : (OverviewMatchDetailsItem) convertView;
                    overviewMatchDetailsItem.setContent(matchDetailsItem);
                    return overviewMatchDetailsItem;

                case OverviewItem.TYPE_MATCH_UPCOMING:
                    MatchDetailsItem matchDetailsUpcomingItem = (MatchDetailsItem) item;

                    OverviewMatchDetailsItem overviewMatchDetailsUpcomingItem = convertView == null
                            ? new OverviewMatchDetailsItem(getActivity())
                            : (OverviewMatchDetailsItem) convertView;
                    overviewMatchDetailsUpcomingItem.setContent(matchDetailsUpcomingItem);
                    return overviewMatchDetailsUpcomingItem;

                case OverviewItem.TYPE_SECTION_TITLE:
                    OverviewSectionTitle overviewSectionTitle = convertView == null
                            ? new OverviewSectionTitle(getActivity())
                            : (OverviewSectionTitle) convertView;
                    overviewSectionTitle.setContent(item);
                    return overviewSectionTitle;
            }

            return convertView;
        }
    }

}
