package com.wangng.pindu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wangng.pindu.R;
import com.wangng.pindu.ui.bookmark.BookmarkFragment;
import com.wangng.pindu.ui.gestoslife.GestosLifeFragment;
import com.wangng.pindu.util.UIHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_MAIN_FRAGMENT = "mainFragment";
    private static final String TAG_BOOKMARK_FRAGMENT = "bookmarkFragment";
    private static final String TAG_GESTOSLIFE_FRAGMENT = "gestoslifeFragment";

    private Context mContext;
    private Toolbar toolbar;
    private NavigationView navigationView;
//    private MainFragment mMainFragment;
    private BookmarkFragment mBookmarkFragment;
    private GestosLifeFragment mGestosLifeFragment;
    private DrawerLayout drawer;
    private int lastSelectPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        if (savedInstanceState != null) {
//            mMainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, TAG_MAIN_FRAGMENT);
            mBookmarkFragment = (BookmarkFragment) getSupportFragmentManager().getFragment(savedInstanceState, TAG_BOOKMARK_FRAGMENT);
            mGestosLifeFragment = (GestosLifeFragment) getSupportFragmentManager().getFragment(savedInstanceState, TAG_GESTOSLIFE_FRAGMENT);
        } else {
//            mMainFragment = new MainFragment();
            mBookmarkFragment = new BookmarkFragment();
            mGestosLifeFragment = new GestosLifeFragment();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setMainFragment(mGestosLifeFragment, "mGestosLifeFragment");
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            switchFragment(mGestosLifeFragment, lastSelectPosition);
            navigationView.setCheckedItem(R.id.nav_home);
            lastSelectPosition = 0;
        } else if (id == R.id.nav_bookmark) {
            switchFragment(mBookmarkFragment, lastSelectPosition);
            navigationView.setCheckedItem(R.id.nav_bookmark);
            lastSelectPosition = 1;
        } else if (id == R.id.nav_setting) {
            UIHelper.showSetting(mContext);
        } else if (id == R.id.nav_about) {
            UIHelper.showAbout(mContext);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mBookmarkFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, TAG_BOOKMARK_FRAGMENT, mBookmarkFragment);
        }
        if (mGestosLifeFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState, TAG_GESTOSLIFE_FRAGMENT, mGestosLifeFragment);
        }
    }

    private void switchFragment(Fragment showFragment, int mLastSelectPosition) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Fragment hideFragment = null;
        switch (mLastSelectPosition) {
            case 0:
                hideFragment = mGestosLifeFragment;
                break;
            case 1:
                hideFragment = mBookmarkFragment;
                break;
        }
        if (!showFragment.isAdded()) {
            ft.hide(hideFragment).add(R.id.container, showFragment).commitAllowingStateLoss();
        } else {
            ft.hide(hideFragment).show(showFragment).commitAllowingStateLoss();
        }
    }

    public void setMainFragment(Fragment fragment, String tag) {
        if (!fragment.isAdded()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.container, fragment, tag);
            ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commitAllowingStateLoss();
        }
    }

}
