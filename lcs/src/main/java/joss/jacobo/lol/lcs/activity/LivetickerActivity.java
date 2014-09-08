package joss.jacobo.lol.lcs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
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

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.api.ApiService;
import joss.jacobo.lol.lcs.api.model.Liveticker.Event;
import joss.jacobo.lol.lcs.api.model.Liveticker.Liveticker;
import joss.jacobo.lol.lcs.model.MatchesModel;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import joss.jacobo.lol.lcs.provider.matches.MatchesSelection;
import joss.jacobo.lol.lcs.utils.CustomTypefaceSpan;
import joss.jacobo.lol.lcs.utils.GGson;
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

    boolean fetching = false;
    private LocalBroadcastManager broadcastManager;
    private ApiReceiver apiReceiver;

    LivetickerAdapter adapter;
    Liveticker liveticker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveticker);
        ButterKnife.inject(this);

        slidingUpPanelLayout.setPanelSlideListener(this);

        setupActionBar("Liveticker");
        setupListView();
        setupDrawer();
        showLoading();

        broadcastManager = LocalBroadcastManager.getInstance(this);
        apiReceiver = new ApiReceiver();

//        getLoaderManager().initLoader(TWEETS_CALLBACK, null, new TweetsCallBack());
//        ApiService.getLCSTweets(this);
        ApiService.getLivetickerEvents(this, "");
        fetching = true;

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

//        Message message = new Message();
//        message.what = GET_HASHTAG_TWEETS;
//        tweetHandler.sendMessageDelayed(message, DELAY);

    }

    @Override
    public void onPause(){
        super.onPause();
        broadcastManager.unregisterReceiver(apiReceiver);
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
        if(v > 0.8f && getSupportActionBar().isShowing()){
            getSupportActionBar().hide();
        }else if(v < 0.8f && !getSupportActionBar().isShowing()){
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
                            fetching = false;
                            showContent();
                            break;
                        case ApiService.ERROR:
                            adapter.setItems(new ArrayList<Event>());
                            fetching = false;
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
            onSetActionBarTitle("Liveticker", match.team1 + " vs. " + match.team2);
        }
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
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + getString(R.string.font_gothic_regular));
        CustomTypefaceSpan light = new CustomTypefaceSpan("",font);
        light.setColor(getResources().getColor(R.color.white));
        SpannableStringBuilder sb = new SpannableStringBuilder(title);
        sb.setSpan(light,0,title.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(sb);
    }

    private void showLoading(){
        loadingView.setVisibility(View.VISIBLE);
    }

    private void showContent(){
        loadingView.setVisibility(View.GONE);
    }

}
