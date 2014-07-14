package com.roomra.roomerAndroid.roomerandroid;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;




public class RoomerFrontPage extends FragmentActivity {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    RoomerFragmentAdapter mDemoCollectionPagerAdapter;
    ActionBar actionBar;
    ViewPager viewPager;
    private String[] tabs = { "Top Rated", "Games", "Movies", "Dev" };



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_page_roomer);
        actionBar = getActionBar();
        actionBar.hide();
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new RoomerFragmentAdapter(
                        getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(mDemoCollectionPagerAdapter);
    }
}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
