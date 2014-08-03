package joss.jacobo.lol.lcs.activity;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.adapters.TweetsAdapter;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsCursor;
import joss.jacobo.lol.lcs.provider.tweets.TweetsSelection;
import joss.jacobo.lol.lcs.utils.CustomTypefaceSpan;

/**
 * Created by Joss on 8/2/2014
 */
public class LiveStreamingActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String API_KEY = "AIzaSyBXyu6ZqTxhkWybwxPtwmWrEbadtxF1m4A";
    private static final String VIDEO_ID = "eREuD2_43Zo";

    private static final int TWEETS_CALLBACK = 0;
    private static final int GET_HASHTAG_TWEETS = 1;

    private static final int DELAY = 1000 * 30;

    @InjectView(R.id.live_streaming_youtube_player)
    YouTubePlayerView youTubePlayerView;
    @InjectView(R.id.listview)
    ListView listView;
    @InjectView(R.id.emptyView)
    TextView emptyView;
    @InjectView(R.id.loadingView)
    LinearLayout loadingView;

    boolean fullscreen = false;
    boolean fetching = false;
    private LocalBroadcastManager broadcastManager;
    private ApiReceiver apiReceiver;

    YouTubePlayer youTubePlayer;
    TweetsAdapter adapter;
    Handler tweetHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_HASHTAG_TWEETS:
                    if(!fetching){
                        ApiService.getLCSTweets(LiveStreamingActivity.this);
                        fetching = true;
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_streaming);
        ButterKnife.inject(this);

        setupActionBar("Live");
        setupListView();
        showLoading();

        youTubePlayerView.initialize(API_KEY, this);

        broadcastManager = LocalBroadcastManager.getInstance(this);
        apiReceiver = new ApiReceiver();

        getLoaderManager().initLoader(TWEETS_CALLBACK, null, new TweetsCallBack());
        ApiService.getLCSTweets(this);
        fetching = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        broadcastManager.registerReceiver(apiReceiver, new IntentFilter(ApiService.BROADCAST));

        Message message = new Message();
        message.what = GET_HASHTAG_TWEETS;
        tweetHandler.sendMessageDelayed(message, DELAY);

    }

    @Override
    public void onPause(){
        super.onPause();
        broadcastManager.unregisterReceiver(apiReceiver);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        ApiService.deleteHashTagTweets(getContentResolver());
    }

    private void setupListView() {
        listView.setEmptyView(emptyView);
        adapter = new TweetsAdapter(this, new ArrayList<TweetsModel>());
        listView.setAdapter(adapter);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        this.youTubePlayer = youTubePlayer;

        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                fullscreen = b;
            }
        });

        /** Start buffering **/
        if (!wasRestored) {
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;

        switch (orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                youTubePlayer.setFullscreen(true);
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                youTubePlayer.setFullscreen(false);
                break;

            case Configuration.ORIENTATION_UNDEFINED:
                youTubePlayer.setFullscreen(false);
                break;
        }

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onPlaying() {

        }

        @Override
        public void onSeekTo(int arg0) {

        }

        @Override
        public void onStopped() {

        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {

        }

        @Override
        public void onLoaded(String arg0) {

        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onVideoStarted() {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TweetsCallBack implements LoaderManager.LoaderCallbacks<Cursor>{

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            TweetsSelection where = new TweetsSelection();
            where.textLike("%" + ApiService.HASH_TAG_LCS + "%");
            return new CursorLoader(LiveStreamingActivity.this, TweetsColumns.CONTENT_URI, null, where.sel(), where.args(), TweetsColumns.CREATED_AT + " DESC LIMIT 50");
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

    private class ApiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int api_type = intent.getIntExtra(ApiService.API_TYPE, -1);
            int status = intent.getExtras().getInt(ApiService.STATUS);

            switch (api_type){
                case ApiService.TYPE_GET_TWEETS_LCS:

                    Message message = new Message();
                    switch (status){
                        case ApiService.SUCCESS:
                            fetching = false;
                            message.what = GET_HASHTAG_TWEETS;
                            tweetHandler.sendMessageDelayed(message, DELAY);
                            break;
                        case ApiService.ERROR:
                            fetching = false;
                            message.what = GET_HASHTAG_TWEETS;
                            tweetHandler.sendMessageDelayed(message, DELAY);
                            break;
                        default:

                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed(){
        if(fullscreen){
            youTubePlayer.setFullscreen(false);
            fullscreen = false;
            return;
        }

        super.onBackPressed();
    }

    public void setupActionBar(String title) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowCustomEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_gothic_regular));
        CustomTypefaceSpan light = new CustomTypefaceSpan("",font);
        light.setColor(getResources().getColor(R.color.white));
        SpannableStringBuilder sb = new SpannableStringBuilder(title);
        sb.setSpan(light,0,title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getActionBar().setTitle(sb);
    }

    private void showLoading(){
        loadingView.setVisibility(View.VISIBLE);
    }

    private void showContent(){
        loadingView.setVisibility(View.GONE);
    }
}
