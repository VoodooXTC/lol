package joss.jacobo.lol.lcs.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.items.OverviewItem;
import joss.jacobo.lol.lcs.model.PlayersModel;
import joss.jacobo.lol.lcs.model.TeamsModel;
import joss.jacobo.lol.lcs.provider.players.PlayersColumns;
import joss.jacobo.lol.lcs.provider.players.PlayersCursor;
import joss.jacobo.lol.lcs.provider.players.PlayersSelection;
import joss.jacobo.lol.lcs.views.OverviewSectionTitle;
import joss.jacobo.lol.lcs.views.PlayerItem;

/**
 * Created by Joss on 7/27/2014
 */
public class TeamRosterFragment extends BaseListFragment {

    private static final int PLAYER_CALLBACK = 123;

    List<PlayersModel> players;
    TeamsModel team;
    PlayersAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        team = gson.fromJson(getArguments().getString(TeamsFragment.TEAM), TeamsModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);
        setupListView();
        showLoading();
        adapter = new PlayersAdapter(new ArrayList<PlayersModel>());
        setAdapter(adapter);

        getLoaderManager().initLoader(PLAYER_CALLBACK, null, new PlayersCallBack());
        ApiService.getPlayers(getActivity(), team.teamId);
    }

    public class PlayersAdapter extends BaseAdapter {

        public List<PlayersModel> items;

        public PlayersAdapter(List<PlayersModel>items){
            this.items = items;
        }

        public void setItems(List<PlayersModel> newItems) {
            this.items = newItems;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public int getItemViewType(int position){
            return items.get(position).type;
        }

        @Override
        public int getViewTypeCount(){
            return PlayersModel.TYPE_MAX;
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

            PlayersModel player = items.get(position);

            switch (player.type){
                case PlayersModel.TYPE_PLAYER:
                    PlayerItem playerItem = convertView == null
                            ? new PlayerItem(getActivity())
                            : (PlayerItem) convertView;
                    playerItem.setContent(player);

                    return playerItem;

                case PlayersModel.TYPE_TITLE:
                    OverviewSectionTitle overviewSectionTitle = convertView == null
                            ? new OverviewSectionTitle(getActivity())
                            : (OverviewSectionTitle) convertView;
                    overviewSectionTitle.setContent(player);
                    return overviewSectionTitle;
            }

            return convertView;
        }
    }

    private class PlayersCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            PlayersSelection where = new PlayersSelection();
            where.teamId(team.teamId);
            return new CursorLoader(getActivity(), PlayersColumns.CONTENT_URI, null, where.sel(), where.args(), null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                PlayersCursor cursor = new PlayersCursor(data);
                players = cursor.getList();
                adapter.setItems(getItems());
                showContent();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private List<PlayersModel> getItems() {
        List<PlayersModel> items = new ArrayList<PlayersModel>();
        if(players.size() > 0){
            items.add(new PlayersModel(PlayersModel.TYPE_TITLE, "PRO", "PLAYERS"));
            items.addAll(players);

            // Remove the last divider
            items.get(items.size() - 1).showDivider = false;
        }

        return items;
    }
}
