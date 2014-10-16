package joss.jacobo.lol.lcs.activity;

import android.app.ActionBar;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import joss.jacobo.lol.lcs.BuildConfig;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.adapters.TweetsAdapter;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.api.model.LiveStreams.Video;
import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.provider.tweets.TweetsColumns;
import joss.jacobo.lol.lcs.provider.tweets.TweetsCursor;
import joss.jacobo.lol.lcs.provider.tweets.TweetsSelection;
import joss.jacobo.lol.lcs.utils.CustomTypefaceSpan;
import joss.jacobo.lol.lcs.utils.GGson;
import joss.jacobo.lol.lcs.views.ActionBarDropDownItem;
import joss.jacobo.lol.lcs.views.CancelableAdView;

/**
 * Created by Joss on 8/2/2014
 */
public class LiveStreamingActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, ActionBar.OnNavigationListener, AdapterView.OnItemClickListener, YouTubePlayer.OnFullscreenListener, YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener {

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
    @InjectView(R.id.cancelableAds)
    CancelableAdView cancelableAdView;

    ActionBar actionBar;

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

        cancelableAdView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                cancelableAdView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                listView.setPadding(0, 0, 0, cancelableAdView.getMeasuredHeight());
            }
        });

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

        cancelableAdView.initAds();
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
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Video video = dropDownAdapter.videos.get(itemPosition);
        cueVideo(video.id);
        return true;
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

                                if(actionBar != null) {
                                    actionBar.setDisplayShowTitleEnabled(false);
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

    public class DropDownAdapter extends BaseAdapter{

        public List<Video> videos;

        public DropDownAdapter(List<Video> videos){
            this.videos = videos;
        }

        @Override
        public int getCount() {
            return videos != null ? videos.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return videos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setVideos(List<Video> videos){
            this.videos = videos;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ActionBarDropDownItem item = convertView == null ?
                    new ActionBarDropDownItem(LiveStreamingActivity.this) :
                    (ActionBarDropDownItem) convertView;
            item.setContent(videos.get(position));

            return item;
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

    public void setupActionBar(String title) {
        actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);

            dropDownAdapter = new DropDownAdapter(new ArrayList<Video>());
            actionBar.setListNavigationCallbacks(dropDownAdapter, this);

            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_gothic_regular));
            CustomTypefaceSpan light = new CustomTypefaceSpan("",font);
            light.setColor(getResources().getColor(R.color.white));
            SpannableStringBuilder sb = new SpannableStringBuilder(title);
            sb.setSpan(light,0,title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            actionBar.setTitle(sb);
        }

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

        if (!BuildConfig.DEBUG)
            cancelableAdView.setVisibility(View.GONE);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(0);

        if (!BuildConfig.DEBUG)
            cancelableAdView.setVisibility(View.VISIBLE);
    }
}
