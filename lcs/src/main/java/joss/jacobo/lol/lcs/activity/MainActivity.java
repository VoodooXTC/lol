package joss.jacobo.lol.lcs.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.fragment.OverviewFragment;
import joss.jacobo.lol.lcs.items.DrawerItem;
import joss.jacobo.lol.lcs.views.DrawerItemSectionTitle;


public class MainActivity extends BaseActivity{

    private static final String FRAGMENT_TAG = "fragment_tag";
    private static final String CURRENT_FRAG = "current_frag";

    public FrameLayout contentView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private MenuListAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private int currentFrag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = (FrameLayout) findViewById(R.id.content_container);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list_view);

        setUpDrawerLayout();
        setupActionBar();

        if (savedInstanceState != null)
            currentFrag = savedInstanceState.getInt(CURRENT_FRAG);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            selectFragment(R.id.fragment_overview, 4);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen((View) mDrawerList.getParent());
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.isDrawerIndicatorEnabled()) {
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setUpDrawerLayout() {
        adapter = new MenuListAdapter(this, getDrawerItems());
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private List<DrawerItem> getDrawerItems() {
        List<DrawerItem> items = new ArrayList<DrawerItem>();

        items.add(new DrawerItem(DrawerItem.TYPE_SECTION_TITLE, 0, "LIVE"));
        items.add(new DrawerItem(DrawerItem.TYPE_LIVESTREAM, 0, "Livesteam"));
        items.add(new DrawerItem(DrawerItem.TYPE_LIVETICKER, 0, "Liveticker"));

        items.add(new DrawerItem(DrawerItem.TYPE_SECTION_TITLE, 0, "GENERAL"));
        items.add(new DrawerItem(DrawerItem.TYPE_OVERVIEW, 0, "Overview"));
        items.add(new DrawerItem(DrawerItem.TYPE_NEWS, 0, "News"));
        items.add(new DrawerItem(DrawerItem.TYPE_SCHEDULE_RESULTS, 0, "Schedule & Results"));
        items.add(new DrawerItem(DrawerItem.TYPE_STANDINGS, 0, "Standings"));

        items.add(new DrawerItem(DrawerItem.TYPE_SECTION_TITLE, 0, "Teams"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 9, "Cloud 9"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 10, "Counter Logic Gaming"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 11, "Curse"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 12, "Evil Geniuses"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 13, "Complexity Black"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 14, "Team Dignitas"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 15, "Team Solomid"));
        items.add(new DrawerItem(DrawerItem.TYPE_TEAM, 16, "LMQ"));

        return items;
    }

    public class MenuListAdapter extends BaseAdapter {

        private Context context;
        private List<DrawerItem> items;
        private int hintPosition;

        public MenuListAdapter(Context c, List<DrawerItem> i) {
            this.context = c;
            this.items = i;
        }

        public void setItems(List<DrawerItem> items){
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position){
            return items.get(position).type;
        }

        @Override
        public int getViewTypeCount(){
            return DrawerItem.TYPE_MAX;
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void setHint(int position) {
            hintPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            DrawerItem item = items.get(i);

            switch(item.type){
                case DrawerItem.TYPE_SECTION_TITLE:
                    DrawerItemSectionTitle drawerItemSectionTitle = view == null ? new DrawerItemSectionTitle(context) : (DrawerItemSectionTitle) view;
                    drawerItemSectionTitle.setContent(item);
                    return drawerItemSectionTitle;
                default:
                    joss.jacobo.lol.lcs.views.DrawerItem drawerItem = view == null ? new joss.jacobo.lol.lcs.views.DrawerItem(context) : (joss.jacobo.lol.lcs.views.DrawerItem) view;
                    drawerItem.setContent(item);
                    drawerItem.title.setSelected(i == hintPosition);
                    return drawerItem;
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {

                //Compensate for headerView in position 0
                DrawerItem clicked = adapter.items.get(position);

                switch (clicked.type){
                    case DrawerItem.TYPE_LIVESTREAM:

                        break;

                    case DrawerItem.TYPE_LIVETICKER:

                        break;

                    case DrawerItem.TYPE_OVERVIEW:

                        break;

                    case DrawerItem.TYPE_NEWS:

                        break;

                    case DrawerItem.TYPE_SCHEDULE_RESULTS:

                        break;

                    case DrawerItem.TYPE_STANDINGS:

                        break;

                    case DrawerItem.TYPE_TEAM:

                        break;
                }
            }
        }
    }

    public void selectFragment(int nextFrag, int position) {
        if (nextFrag != currentFrag) {
            switch (nextFrag) {
                case R.id.fragment_livestream:
                    currentFrag = R.id.fragment_livestream;
                    break;
                case R.id.fragment_liveticker:
                    currentFrag = R.id.fragment_liveticker;
                    break;
                case R.id.fragment_overview:
                    currentFrag = R.id.fragment_overview;
                    break;
                case R.id.fragment_news:
                    currentFrag = R.id.fragment_news;
                    break;
                case R.id.fragment_schedule_results:
                    currentFrag = R.id.fragment_schedule_results;
                    break;
                case R.id.fragment_standings:
                    currentFrag = R.id.fragment_standings;
                    break;
                case R.id.fragment_team:
                    currentFrag = R.id.fragment_team;
                    break;
                case R.id.fragment_feedback:

//                    closeDrawer();
//
//                    String query = Saguaro.getSendFeedbackIntent(this).getData().toString();
//                    query += Uri.encode("\n");
//                    query += Uri.encode(Device.getFullDeviceName());
//                    query += Uri.encode("\n");
//                    query += Uri.encode(Device.getOSVersion());
//
//                    Intent feedBackIntent = new Intent(Intent.ACTION_SENDTO);
//                    feedBackIntent.setData(Uri.parse(query));
//                    startActivity(feedBackIntent);

                    return;
            }
            adapter.setHint(position);
            replaceFragment();
        } else {
            closeDrawer();
        }
    }

    private void replaceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment frag = new OverviewFragment();
        switch (currentFrag) {
            case R.id.fragment_livestream:
//                frag = new LocationFragment();
                break;
            case R.id.fragment_liveticker:
//                frag = new SentaraTodayFragment();
                break;
            case R.id.fragment_overview:
//                frag = new AboutFragment();
                break;
            case R.id.fragment_news:
                break;
            case R.id.fragment_schedule_results:
                break;
            case R.id.fragment_standings:
                break;
            case R.id.fragment_team:
                break;
        }

        ft.setCustomAnimations(R.anim.slide_in_from_right, R.anim.fade_out);
        ft.replace(contentView.getId(), frag, FRAGMENT_TAG);
        ft.commit();
        closeDrawer();
    }


    public void closeDrawer() {
        if (mDrawerLayout.isShown()) {
            mDrawerLayout.closeDrawers();
        }
    }

}
