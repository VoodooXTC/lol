package joss.jacobo.lol.lcs.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;

import java.util.List;

import joss.jacobo.lol.lcs.adapters.TweetsAdapter;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.model.TeamsModel;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsCursor;
import joss.jacobo.lol.lcs.provider.tweets.TweetsSelection;

/**
 * Created by Joss on 7/27/2014
 */
public class TeamSocialFragment extends BaseListFragment {

    private static final int TWEETS_CALLBACK = 0;

    TeamsModel team;
    TeamDetailsModel teamDetail;

    TweetsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        team = gson.fromJson(getArguments().getString(TeamsFragment.TEAM), TeamsModel.class);
        teamDetail = TeamDetailsSelection.getTeamDetails(getActivity().getContentResolver(), team.teamId);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState){
        super.onViewCreated(view, savedState);
        setupListView();
        showLoading();
        adapter = new TweetsAdapter(getActivity(), null);
        setAdapter(adapter);

        getLoaderManager().initLoader(TWEETS_CALLBACK, null, new TweetsCallBack());
        ApiService.getTweets(getActivity(), teamDetail.twitterHandle);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TweetsModel tweet = adapter.items.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + tweet.twitterHandle + "/status/" + tweet.tweetId));
        startActivity(intent);
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
}
