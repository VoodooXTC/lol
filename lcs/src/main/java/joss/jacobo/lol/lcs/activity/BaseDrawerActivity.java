package joss.jacobo.lol.lcs.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.adapters.MenuListAdapter;
import joss.jacobo.lol.lcs.interfaces.BaseFragmentListener;
import joss.jacobo.lol.lcs.interfaces.DrawerListener;
import joss.jacobo.lol.lcs.items.DrawerItem;
import joss.jacobo.lol.lcs.views.DrawerHeader;

/**
 * Created by: jossayjacobo
 * Date: 2/12/15
 * Time: 1:32 PM
 */
public abstract class BaseDrawerActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, DrawerListener, BaseFragmentListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.drawer_list_view)
    ListView drawerListView;
    @InjectView(R.id.content_container)
    FrameLayout contentView;

    public DrawerHeader drawerHeader;
    public MenuListAdapter adapter;
    public DrawerToggle drawerToggle;

    private String currentTitle;
    private String currentSubtitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setupDrawerLayout();
        setupActionBar(toolbar);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(GravityCompat.START);
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isEnabled()) {
                menu.getItem(i).setVisible(!drawerOpen);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.isDrawerIndicatorEnabled()) {
            if (drawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerLayout() {
        drawerHeader = new DrawerHeader(this);
        drawerListView.addHeaderView(drawerHeader);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        adapter = new MenuListAdapter(this, new ArrayList<DrawerItem>());
        drawerListView.setAdapter(adapter);
        drawerToggle = new DrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onDrawerItemClicked(view, position);
    }

    @Override
    public void onSetActionBarTitle(String title, String subtitle) {
        setToolbarTitleAndSubtitle(title, subtitle);
    }

    @Override
    public void setToolbarTitleAndSubtitle(String title, String subtitle) {
        super.setToolbarTitleAndSubtitle(title, subtitle);
        currentTitle = title;
        currentSubtitle = subtitle;
    }

    private class DrawerToggle extends ActionBarDrawerToggle {

        public DrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        /** Called when a drawer has settled in a completely closed state. */
        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            setToolbarTitleAndSubtitle(currentTitle, currentSubtitle);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        /** Called when a drawer has settled in a completely open state. */
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            setTitle(getString(R.string.app_name), null);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }

    public void closeDrawer() {
        if (drawerLayout.isShown()) {
            drawerLayout.closeDrawers();
        }
    }

}
