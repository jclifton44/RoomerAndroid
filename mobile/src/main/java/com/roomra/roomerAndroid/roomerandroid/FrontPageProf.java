package com.roomra.roomerAndroid.roomerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FrontPageProf extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.front_page_prof, container, false);
        ImageView image = (ImageView) rootView.findViewById(R.id.seal);
        List<ModularStory> stories = new ArrayList<ModularStory>();
        for (int i = 0; i < 15; i++) {
            ModularStory singleStory = new ModularStory("Despite their infrequent occurence in common conversation, the Appalacian mountin range is the oldest mountain range in the world.", "Mountains", R.drawable.mr_seal);
            stories.add(singleStory);
        }

        ListView postView = (ListView) rootView.findViewById(R.id.storyView);
        ImageView settings = (ImageView) rootView.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PreferencesPanel.class);
                startActivity(intent);
            }
        });
        ModularStoryArrayAdapter adapter = new ModularStoryArrayAdapter(this.getActivity(),
                R.layout.story_item, stories);
        postView.setAdapter(adapter);
        adapter.setupAdapterClickListener(postView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("SWIPE", "onRefresh called from SwipeRefreshLayout");

                //initiateRefresh();
            }
        });
    }
}