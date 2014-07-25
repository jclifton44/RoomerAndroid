package com.roomra.roomerAndroid.roomerandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FrontPageTopics extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.front_page_topics, container, false);
        List<ModularTopic> topics = new ArrayList<ModularTopic>();
        for (int i = 0; i < 15; i++) {
            ModularTopic singleTopic = new ModularTopic("Party at 21st");
            topics.add(singleTopic);
        }

        ListView postView = (ListView) rootView.findViewById(R.id.postLists);
        ModularTopicArrayAdapter adapter = new ModularTopicArrayAdapter(this.getActivity(),
                R.layout.topic_item, topics);
        postView.setAdapter(adapter);

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