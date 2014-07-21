package joss.jacobo.lol.lcs.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import butterknife.ButterKnife;
import joss.jacobo.lol.lcs.R;


public class MainActivity extends BaseActivity {

    private static final String FRAGMENT_TAG = "fragment_tag";
    private static final String CURRENT_FRAG = "current_frag";

    public FrameLayout contentView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
//    private MenuListAdapter adapter;
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
//            selectFragment(R.id.fragment_location_finder, 0);
        }

        Log.e("MainActivity", datastore.getVersion() + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpDrawerLayout() {
//        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
//        adapter = new MenuListAdapter(this,
//                getResources().getStringArray(R.array.drawer_titles),
//                getResources().getStringArray(R.array.drawer_subtitles),
//                getResourcesIntArray(R.array.drawer_icons));
//        mDrawerList.setAdapter(adapter);
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,
//                mDrawerLayout,
//                R.drawable.ic_navigation_drawer,
//                R.string.drawer_open,
//                R.string.drawer_close
//        ) {
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//                contentView.animate().translationX(slideOffset * contentSlideOffset * contentView.getWidth()).setDuration(0).start();
//                contentView.setScaleY(1.0f - (contentShrinkScale * slideOffset));
//                contentView.setScaleX(1.0f - (contentShrinkScale * slideOffset));
//            }
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}
