package joss.jacobo.lol.lcs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.api.model.Replays.Replay;
import joss.jacobo.lol.lcs.api.model.Replays.Replays;
import joss.jacobo.lol.lcs.provider.replays.ReplaysColumns;
import joss.jacobo.lol.lcs.provider.replays.ReplaysCursor;
import joss.jacobo.lol.lcs.utils.GGson;
import joss.jacobo.lol.lcs.views.ReplayItem;

/**
 * Created by Joss on 9/1/2014
 */
public class ReplaysFragment extends BaseListFragment {

    private static final int REPLAYS_CALLBACK = 0;
    private static final int NUM_OF_ITEMS = 5;

    private LocalBroadcastManager broadcastManager;
    private ApiReceiver apiReceiver;

    ReplayAdapter adapter;
    int count = 0;
    boolean fetching = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);

        listener.onSetActionBarTitle("Replays", null);

        showLoading();

        setupListViewWithLoadingItemView();
        showLoading();
        listView.setSelector(R.drawable.lcs_item_selector);
        adapter = new ReplayAdapter(new ArrayList<Replay>());
        setAdapter(adapter);

        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        apiReceiver = new ApiReceiver();

        getLoaderManager().initLoader(REPLAYS_CALLBACK, null, new ReplayLoaderCallBacks());
    }

    @Override
    public void onResume(){
        super.onResume();

        broadcastManager.registerReceiver(apiReceiver, new IntentFilter(ApiService.BROADCAST));

        if(count == 0 || adapter.getCount() == 0){

            ApiService.getReplays(getActivity(), NUM_OF_ITEMS, count);
            fetching = true;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        broadcastManager.unregisterReceiver(apiReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Replay replay = adapter.replays.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + replay.id));
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        if(adapter != null && !fetching && adapter.getCount() > 0){
            fetching = true;
            count = adapter.getCount();
            ApiService.getReplays(getActivity(), NUM_OF_ITEMS, count);
        }
    }

    private class ReplayLoaderCallBacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), ReplaysColumns.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            ReplaysCursor replaysCursor = new ReplaysCursor(data);
            List<Replay> replays = replaysCursor.getList();
            adapter.setReplays(replays);

            if(replays.size() > 0){
                showContent();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }

    }

    public class ReplayAdapter extends BaseAdapter {

        public List<Replay> replays;

        public ReplayAdapter(List<Replay> items){
            this.replays = items;
        }

        public void setReplays(List<Replay> replays) {
            this.replays = replays;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return replays == null ? 0 : replays.size();
        }

        @Override
        public Object getItem(int position) {
            return replays.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ReplayItem item = convertView == null ? new ReplayItem(getActivity()) : (ReplayItem) convertView;
            item.setContent(replays.get(position));
            item.showDivider(position != (replays.size() - 1));

            return item;
        }
    }

    private class ApiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int api_type = intent.getIntExtra(ApiService.API_TYPE, -1);
            int status = intent.getExtras().getInt(ApiService.STATUS);

            switch (api_type){
                case ApiService.TYPE_GET_REPLAYS:

                    switch (status){
                        case ApiService.SUCCESS:
                            fetching = false;

                            break;
                        case ApiService.ERROR:
                            showContent();
                            removeLoadingItem();
                            fetching = true;
                            break;
                        default:

                    }
                    break;
            }
        }
    }
}
