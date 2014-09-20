package joss.jacobo.lol.lcs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.api.model.Liveticker.Event;
import joss.jacobo.lol.lcs.api.model.Liveticker.Liveticker;
import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.model.TeamDetailsModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.provider.matches.MatchesSelection;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsCursor;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsSelection;
import joss.jacobo.lol.lcs.utils.DateTimeFormatter;
import joss.jacobo.lol.lcs.utils.GGson;
import joss.jacobo.lol.lcs.views.ActionBarCustomCenteredTitle;
import joss.jacobo.lol.lcs.views.LivetickerBottomDrawerHeader;
import joss.jacobo.lol.lcs.views.LivetickerBottomDrawerMatchup;
import joss.jacobo.lol.lcs.views.LivetickerBottomDrawerPickBans;
import joss.jacobo.lol.lcs.views.LivetickerBottomDrawerScores;
import joss.jacobo.lol.lcs.views.LivetickerEventItem;

/**
 * Created by Joss on 9/6/2014
 */
public class LivetickerActivity extends BaseActivity implements SlidingUpPanelLayout.PanelSlideListener, AdapterView.OnItemClickListener {

    private static final String TAG = "LivetickerActivity";

    private static final int TIMER = 0;
    private static final int TOGGLE = 1;
    private static final int FETCH_DATA = 2;

    private static final int ONE_SECOND = 1000;
    private static final int TOGGLE_DELAY = ONE_SECOND * 10;
    private static final int FETCH_DATA_DELAY = ONE_SECOND * 30;

    @InjectView(R.id.liveticker_sliding_up_panel)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @InjectView(R.id.listview)
    ListView listView;
    @InjectView(R.id.emptyView)
    TextView emptyView;
    @InjectView(R.id.loadingView)
    LinearLayout loadingView;

    @InjectView(R.id.lt_bottom_drawer)
    LinearLayout bottomDrawer;
    @InjectView(R.id.lt_scroll_view)
    ScrollView bottomDrawerScrollView;
    @InjectView(R.id.lt_scroll_view_container)
    LinearLayout bottomDrawerScrollViewContainer;

    LivetickerBottomDrawerHeader btHeader;
    LivetickerBottomDrawerScores btScores;
    LivetickerBottomDrawerMatchup btMatchUp;
    LivetickerBottomDrawerPickBans btPickBans;

    private LocalBroadcastManager broadcastManager;
    private ApiReceiver apiReceiver;

    LivetickerAdapter adapter;
    Liveticker liveticker;
    boolean timerStarted = false;

    ActionBarCustomCenteredTitle customTitle;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case TIMER:
                    setTime(liveticker);
                    sendTimerMessage();
                    break;

                case TOGGLE:
                    customTitle.toggle();
                    sendToggleTitleMessage();
                    break;

                case FETCH_DATA:
                    ApiService.getLivetickerEvents(LivetickerActivity.this, datastore.getSelectedTournament());
                    sendFetchDataMessage(FETCH_DATA_DELAY);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveticker);
        ButterKnife.inject(this);

        AdView mAdView = (AdView) findViewById(R.id.ads);
        mAdView.setVisibility(View.GONE);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        slidingUpPanelLayout.setPanelSlideListener(this);

        setupActionBar("Liveticker");
        setupListView();
        setupDrawer();
        showLoading();

        broadcastManager = LocalBroadcastManager.getInstance(this);
        apiReceiver = new ApiReceiver();

//        getLoaderManager().initLoader(TWEETS_CALLBACK, null, new TweetsCallBack());
//        ApiService.getLCSTweets(this);

        bottomDrawer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bottomDrawerScrollView.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        bottomDrawerScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(bottomDrawerScrollView.getScrollY() != 0);
                return false;
            }
        });
    }

    private void setupDrawer() {
        btHeader = new LivetickerBottomDrawerHeader(this);
        bottomDrawer.addView(btHeader, 0);

        btScores = new LivetickerBottomDrawerScores(this);
        bottomDrawerScrollViewContainer.addView(btScores);

        btMatchUp = new LivetickerBottomDrawerMatchup(this);
        bottomDrawerScrollViewContainer.addView(btMatchUp);

        btPickBans = new LivetickerBottomDrawerPickBans(this);
        bottomDrawerScrollViewContainer.addView(btPickBans);
    }

    @Override
    public void onResume(){
        super.onResume();
        broadcastManager.registerReceiver(apiReceiver, new IntentFilter(ApiService.BROADCAST));

        // Start all Messages
        startTitleToggle();
        startFetchingData();
        if(liveticker != null){
            startTimer();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        broadcastManager.unregisterReceiver(apiReceiver);

        // Stop timers and Remove all pending messages
        stopTimer();
        handler.removeMessages(TOGGLE);
        handler.removeMessages(FETCH_DATA);
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
    public void onBackPressed(){
        if(slidingUpPanelLayout.isPanelExpanded() || slidingUpPanelLayout.isPanelAnchored()){
            slidingUpPanelLayout.collapsePanel();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onPanelSlide(View view, float v) {
        if(v > 0.70f && getSupportActionBar().isShowing()){
            getSupportActionBar().hide();
        }else if(v < 0.70f && !getSupportActionBar().isShowing()){
            getSupportActionBar().show();
        }
    }

    @Override
    public void onPanelCollapsed(View view) {
    }

    @Override
    public void onPanelExpanded(View view) {
    }

    @Override
    public void onPanelAnchored(View view) {

    }

    @Override
    public void onPanelHidden(View view) {

    }

    private void setupListView() {
        emptyView.setText("No Live Events at the moment");
        listView.setEmptyView(emptyView);
        adapter = new LivetickerAdapter(this, new ArrayList<Event>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        listView.setPadding(0, getActionBarHeight(), 0, 0);
        listView.setClipToPadding(false);
    }

    private int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private class ApiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int api_type = intent.getIntExtra(ApiService.API_TYPE, -1);
            int status = intent.getExtras().getInt(ApiService.STATUS);

            switch (api_type){
                case ApiService.TYPE_GET_LIVETICKER_EVENTS:
                    switch (status){
                        case ApiService.SUCCESS:
                            liveticker = GGson.fromJson(intent.getStringExtra(ApiService.LIVETICKER_EVENTS), Liveticker.class);
                            adapter.setItems(liveticker.events);
                            setContent(liveticker);
                            showContent();
                            break;
                        case ApiService.ERROR:
                            emptyView.setText(intent.getStringExtra(ApiService.MESSAGE));
                            adapter.setItems(new ArrayList<Event>());
                            showContent();
                            break;
                    }
                    break;
            }

        }

    }

    private void setContent(Liveticker liveticker) {
        btHeader.setContent(liveticker.matchDetails);
        btScores.setContent(liveticker.matchDetails);
        btMatchUp.setContent(liveticker.matchDetails);
        btPickBans.setContent(liveticker.matchDetails);

        MatchesSelection matchWhere = new MatchesSelection();
        matchWhere.matchId(liveticker.matchDetails.matchId);
        MatchesCursor matchesCursor = matchWhere.query(getContentResolver());
        MatchesModel match = matchesCursor.moveToFirst() ? new MatchesModel(matchesCursor) : null;

        if(match != null){
            customTitle.setMatch(getTeamAbrv(match.team1, match.team1Id),
                    getTeamAbrv(match.team2, match.team2Id));
        }
        if(!timerStarted)
            startTimer();
    }

    private String getTeamAbrv(String teamName, int teamId){
        TeamDetailsSelection where = new TeamDetailsSelection();
        where.teamId(teamId);
        TeamDetailsCursor cursor = where.query(getContentResolver());

        String teamAbrev = null;
        if(cursor.moveToFirst()){
            teamAbrev = cursor.getAbrev();
        }
        cursor.close();

        return teamAbrev == null ? teamName : teamAbrev;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public class LivetickerAdapter extends BaseAdapter{

        public Context context;
        public List<Event> items;

        public LivetickerAdapter(Context context, List<Event> items){
            this.context = context;
            this.items = items;
        }

        public void setItems(List<Event> items){
            this.items = items;
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
        public View getView(int position, View convertView, ViewGroup parent) {

            Event event = items.get(position);

            LivetickerEventItem eventItem = convertView == null
                    ? new LivetickerEventItem(context)
                    : (LivetickerEventItem) convertView;

            eventItem.setContent(event);
            eventItem.showDivider(position != items.size() - 1);

            return eventItem;
        }
    }

    public void setupActionBar(String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        customTitle = new ActionBarCustomCenteredTitle(this);
        customTitle.setContent(title, null, null);
        getSupportActionBar().setCustomView(customTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    private void showLoading(){
        loadingView.setVisibility(View.VISIBLE);
    }

    private void showContent(){
        loadingView.setVisibility(View.GONE);
    }

    private void startTitleToggle(){
        handler.removeMessages(TOGGLE);
        sendToggleTitleMessage();
    }

    private void sendToggleTitleMessage(){
        Message message = new Message();
        message.what = TOGGLE;
        handler.sendMessageDelayed(message, TOGGLE_DELAY);
    }

    private void startTimer(){
        timerStarted = true;
        handler.removeMessages(TIMER);
        sendTimerMessage();
    }

    private void stopTimer() {
        timerStarted = false;
        handler.removeMessages(TIMER);
    }

    private void sendTimerMessage(){
        Message message = new Message();
        message.what = TIMER;
        handler.sendMessageDelayed(message, ONE_SECOND);
    }

    private void setTime(Liveticker liveticker) {
        if(liveticker != null){
            Date startingTime = DateTimeFormatter.getDate(liveticker.matchDetails.matchStartedDatetime);
            if(startingTime != null){
                Date now = Calendar.getInstance().getTime();
                Date timeElapsed = new Date(now.getTime() - startingTime.getTime());

                long millis = timeElapsed.getTime();
                customTitle.setTitle(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            }
        }
    }

    private void startFetchingData(){
        handler.removeMessages(FETCH_DATA);
        sendFetchDataMessage(0);
    }

    private void sendFetchDataMessage(int delay) {
        Message message = new Message();
        message.what = FETCH_DATA;
        handler.sendMessageDelayed(message, delay);
    }

}
