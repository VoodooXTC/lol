package joss.jacobo.lol.lcs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.activity.NewsDetailsActivity;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.model.NewsModel;
import joss.jacobo.lol.lcs.provider.news.NewsColumns;
import joss.jacobo.lol.lcs.provider.news.NewsCursor;
import joss.jacobo.lol.lcs.views.NewsItem;

/**
 * Created by Joss on 7/31/2014
 */
public class NewsFragment extends BaseListFragment {

    private static final int NEWS_CALLBACK = 0;
    private static final int NUM_OF_ITEMS = 5;

    private LocalBroadcastManager broadcastManager;
    private ApiReceiver apiReceiver;

    NewsAdapter adapter;
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

        listener.onSetActionBarTitle("News", null);

        showLoading();

        setupListViewWithLoadingItemView();
        showLoading();
        listView.setSelector(R.drawable.lcs_item_selector);
        adapter = new NewsAdapter(new ArrayList<NewsModel>());
        setAdapter(adapter);

        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        apiReceiver = new ApiReceiver();

        getLoaderManager().initLoader(NEWS_CALLBACK, null, new NewsLoaderCallBacks());
    }

    @Override
    public void onResume(){
        super.onResume();

        broadcastManager.registerReceiver(apiReceiver, new IntentFilter(ApiService.BROADCAST));

        if(count == 0 || adapter.getCount() == 0){
            ApiService.getNews(getActivity(), NUM_OF_ITEMS, count);
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
        Gson gson = new Gson();
        Intent i = new Intent(getActivity(), NewsDetailsActivity.class);
        i.putExtra(NewsDetailsActivity.NEWS_MODEL, gson.toJson(adapter.newses.get(position)));
        startActivity(i);
    }

    @Override
    public void onLoadMore() {
        if(adapter != null && !fetching && adapter.getCount() > 0){
            fetching = true;
            count = adapter.getCount();
            ApiService.getNews(getActivity(), NUM_OF_ITEMS, count);
        }
    }

    private class NewsLoaderCallBacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), NewsColumns.CONTENT_URI, null, null, null, NewsColumns.LASTUPDATED);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            NewsCursor blogPosts = new NewsCursor(data);
            List<NewsModel> news = blogPosts.getList();
            if(news != null && news.size() > 0){
                adapter.setNewNewses(news);
                showContent();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }

    }

    public class NewsAdapter extends BaseAdapter {

        public List<NewsModel> newses;

        public NewsAdapter(List<NewsModel> items){
            this.newses = items;
        }

        public void setNewNewses(List<NewsModel> newNewses) {
            this.newses = newNewses;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return newses.size();
        }

        @Override
        public Object getItem(int position) {
            return newses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            NewsItem newsItem = convertView == null ? new NewsItem(getActivity()) : (NewsItem) convertView;
            newsItem.setContent(newses.get(position));

            return newsItem;
        }
    }

    private class ApiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int api_type = intent.getIntExtra(ApiService.API_TYPE, -1);
            int status = intent.getExtras().getInt(ApiService.STATUS);

            switch (api_type){
                case ApiService.TYPE_NEWS:

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
