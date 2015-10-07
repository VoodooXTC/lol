package joss.jacobo.lol.lcs.activity;

import android.animation.Animator;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.adapters.DropDownAdapter;
import joss.jacobo.lol.lcs.adapters.TweetsAdapter;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.api.model.LiveStreams.Video;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsCursor;
import joss.jacobo.lol.lcs.provider.tweets.TweetsSelection;
import joss.jacobo.lol.lcs.utils.GGson;
import joss.jacobo.lol.lcs.views.ActionBarDropDownItem;
import joss.jacobo.lol.lcs.views.ToolbarTitle;

/**
 * Created by Joss on 8/2/2014
 */
public class LiveStreamingActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, AdapterView.OnItemClickListener,
        YouTubePlayer.OnFullscreenListener, YouTubePlayer.PlaybackEventListener,
        YouTubePlayer.PlayerStateChangeListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String API_KEY = "AIzaSyBXyu6ZqTxhkWybwxPtwmWrEbadtxF1m4A";
    private static final String VIDEO_ID = "eREuD2_43Zo";

    private static final int TWEETS_CALLBACK = 0;
    private static final int GET_HASHTAG_TWEETS = 1;

    private static final int DELAY = 1000 * 30;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.live_streaming_youtube_player)
    YouTubePlayerView youTubePlayerView;
    @InjectView(R.id.listview)
    ListView listView;
    @InjectView(R.id.emptyView)
    TextView emptyView;
    @InjectView(R.id.loadingView)
    LinearLayout loadingView;

    ToolbarTitle toolbarTitle;

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

    Spinner spinner;
    DropDownAdapter dropDownAdapter;
    String currentVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_streaming);
        ButterKnife.inject(this);

        setupActionBar(getString(R.string.live_streaming_live));
        setupListView();
        showLoading();

        youTubePlayerView.initialize(API_KEY, this);

        broadcastManager = LocalBroadcastManager.getInstance(this);
        apiReceiver = new ApiReceiver();

        getLoaderManager().initLoader(TWEETS_CALLBACK, null, new TweetsCallBack());
        ApiService.getLCSTweets(this);
        ApiService.getLiveStreamVideos(this);
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
        youTubePlayer = null;
    }

    private void setupListView() {
        listView.setEmptyView(emptyView);
        adapter = new TweetsAdapter(this, new ArrayList<TweetsModel>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void setupActionBar(String title) {
        dropDownAdapter = new DropDownAdapter(this, new ArrayList<Video>());
        toolbarTitle = (ToolbarTitle) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setContent(title);

        spinner = (Spinner) toolbar.findViewById(R.id.toolbar_dropdown);
        spinner.setAdapter(dropDownAdapter);
        spinner.setOnItemSelectedListener(this);

        View back = toolbar.findViewById(R.id.toolbar_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        this.youTubePlayer = youTubePlayer;

        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);
        youTubePlayer.setOnFullscreenListener(this);

        /** Start buffering **/
        if (!wasRestored) {
            cueVideo(null);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, getString(R.string.live_streaming_error_starting_stream), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;

        switch (orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                if(youTubePlayer != null)
                    youTubePlayer.setFullscreen(true);
                hideSystemUI();
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                if(youTubePlayer != null)
                    youTubePlayer.setFullscreen(false);
                showSystemUI();
                break;

            case Configuration.ORIENTATION_UNDEFINED:
                if(youTubePlayer != null)
                    youTubePlayer.setFullscreen(false);
                showSystemUI();
                break;
        }

    }

    @Override
    public void onFullscreen(boolean b) {
        fullscreen = b;
        if(fullscreen) {
            hideSystemUI();
        }else{
            showSystemUI();
        }
    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        Toast.makeText(this,
                errorReason.toString(),
                Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TweetsModel tweet = adapter.items.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + tweet.twitterHandle + "/status/" + tweet.tweetId));
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Video video = ((ActionBarDropDownItem) view).getVideo();
        cueVideo(video.id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.toolbar_back:
                onBackPressed();
                break;
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

                case ApiService.TYPE_GET_LIVESTREAM_VIDEOS:
                    switch (status){
                        case ApiService.SUCCESS:
                            List<Video> videos = GGson.fromJson(intent.getStringExtra(ApiService.LIVESTREAM_VIDEOS), new TypeToken<List<Video>>(){}.getType());
                            if(videos != null && videos.size() > 0){
                                dropDownAdapter.setVideos(videos);
                                cueVideo(videos.get(0).id);

                                if(toolbarTitle != null) {
                                    toolbarTitle.setVisibility(View.GONE);
                                }
                            }else{
                                // TODO ERROR
                                setupActionBar(getString(R.string.live_streaming_no_events_found));
                            }
                            break;

                        case ApiService.ERROR:

                            break;
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed(){
        if(fullscreen){
            if(youTubePlayer != null)
                youTubePlayer.setFullscreen(false);
            fullscreen = false;
            showSystemUI();
            return;
        }

        super.onBackPressed();
    }

    private void showLoading(){
        loadingView.setVisibility(View.VISIBLE);
    }

    private void showContent(){
        loadingView.setVisibility(View.GONE);
    }

    private void cueVideo(String videoId){
        if(videoId != null){
            currentVideo = videoId;
        }
        if(youTubePlayer != null && videoId != null){
            youTubePlayer.cueVideo(currentVideo);
        }
    }

    private void hideSystemUI() {
        int LAYOUT_FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        if(Build.VERSION.SDK_INT >= 19)
            LAYOUT_FLAGS |= View.SYSTEM_UI_FLAG_IMMERSIVE;

        getWindow().getDecorView().setSystemUiVisibility(LAYOUT_FLAGS);

        toolbar.setVisibility(View.GONE);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(0);

        toolbar.setVisibility(View.VISIBLE);
    }
}
