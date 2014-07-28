package joss.jacobo.lol.lcs.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.Gson;

import java.util.List;

import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.model.TeamsModel;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsCursor;
import joss.jacobo.lol.lcs.provider.tweets.TweetsSelection;
import joss.jacobo.lol.lcs.views.TweetItem;

/**
 * Created by Joss on 7/27/2014
 */
public class TeamOverviewFragment extends BaseListFragment {

    private static final int TWEETS_CALLBACK = 0;

    TeamsModel team;
    TeamDetailsModel teamDetail;

    TweetsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        team = gson.fromJson(getArguments().getString(TeamsFragment.TEAM), TeamsModel.class);
        teamDetail = getTeamDetails(team.teamId);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);
        setupListView();
        showLoading();
        adapter = new TweetsAdapter(null);
        setAdapter(adapter);

        getLoaderManager().initLoader(TWEETS_CALLBACK, null, new TweetsCallBack());
        ApiService.getTweets(getActivity(), teamDetail.twitterHandle);
    }

    public class TweetsAdapter extends BaseAdapter {

        public List<TweetsModel> items;

        public TweetsAdapter(List<TweetsModel>items){
            this.items = items;
        }

        public void setItems(List<TweetsModel> newItems) {
            this.items = newItems;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
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

            TweetsModel tweet = items.get(position);

            TweetItem tweetItem = convertView == null
                    ? new TweetItem(getActivity())
                    : (TweetItem) convertView;
            tweetItem.setContent(tweet);

            return tweetItem;
        }
    }

    private class TweetsCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            TweetsSelection where = new TweetsSelection();
            where.twitterHandle(teamDetail.twitterHandle);
            return new CursorLoader(getActivity(), TweetsColumns.CONTENT_URI, null, where.sel(), where.args(), null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                TweetsCursor tweetsCursor = new TweetsCursor(data);
                List<TweetsModel> tweets = tweetsCursor.getTweets();
                adapter.setItems(tweets);
                if(tweets != null && tweets.size() > 0)
                    showContent();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }

    private TeamDetailsModel getTeamDetails(Integer teamId) {
        TeamDetailsSelection where = new TeamDetailsSelection();
        where.teamId(teamId);
        TeamDetailsCursor cursor = where.query(getActivity().getContentResolver());
        if(cursor.moveToFirst()){
            return new TeamDetailsModel(cursor);
        }
        return null;
    }
}
