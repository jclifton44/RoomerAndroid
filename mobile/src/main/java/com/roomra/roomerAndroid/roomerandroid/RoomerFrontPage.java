package com.roomra.roomerAndroid.roomerandroid;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RoomerFrontPage extends FragmentActivity {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    RoomerFragmentAdapter mDemoCollectionPagerAdapter;
    public static FragmentManager fragmentManager;
    public static DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    ActionBar actionBar;
    ViewPager viewPager;
    private String[] tabs = { "Top Rated", "Games", "Movies" };



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page_roomer);
        actionBar = getActionBar();
        actionBar.hide();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, tabs));
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        fragmentManager = getSupportFragmentManager();

        mDemoCollectionPagerAdapter =
                new RoomerFragmentAdapter(
                        getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        //viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mDemoCollectionPagerAdapter);

        viewPager.setCurrentItem(1);
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
