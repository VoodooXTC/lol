package joss.jacobo.lol.lcs.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import joss.jacobo.lol.lcs.items.MatchDetailsItem;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.items.StandingsItem;
import joss.jacobo.lol.lcs.views.OverviewMatchDetailsItem;
import joss.jacobo.lol.lcs.views.OverviewSectionTitle;
import joss.jacobo.lol.lcs.views.OverviewStandingsItem;

/**
 * Created by: jossayjacobo
 * Date: 2/16/15
 * Time: 12:31 PM
 */
public class OverviewAdapter extends BaseAdapter {

    public List<OverviewItem> items;
    private Context context;

    public OverviewAdapter(Context context, List<OverviewItem> items){
        this.items = items;
        this.context = context;
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
                        ? new OverviewStandingsItem(context)
                        : (OverviewStandingsItem) convertView;
                overviewStandingsItem.setContent(standingsItem);

                return overviewStandingsItem;

            case OverviewItem.TYPE_MATCH_RESULTS:
                MatchDetailsItem matchDetailsItem = (MatchDetailsItem) item;

                OverviewMatchDetailsItem overviewMatchDetailsItem = convertView == null
                        ? new OverviewMatchDetailsItem(context)
                        : (OverviewMatchDetailsItem) convertView;
                overviewMatchDetailsItem.setContent(matchDetailsItem);
                return overviewMatchDetailsItem;

            case OverviewItem.TYPE_MATCH_UPCOMING:
                MatchDetailsItem matchDetailsUpcomingItem = (MatchDetailsItem) item;

                OverviewMatchDetailsItem overviewMatchDetailsUpcomingItem = convertView == null
                        ? new OverviewMatchDetailsItem(context)
                        : (OverviewMatchDetailsItem) convertView;
                overviewMatchDetailsUpcomingItem.setContent(matchDetailsUpcomingItem);
                return overviewMatchDetailsUpcomingItem;

            case OverviewItem.TYPE_SECTION_TITLE:
                OverviewSectionTitle overviewSectionTitle = convertView == null
                        ? new OverviewSectionTitle(context)
                        : (OverviewSectionTitle) convertView;
                overviewSectionTitle.setContent(item);
                return overviewSectionTitle;
        }

        return convertView;
    }
}
