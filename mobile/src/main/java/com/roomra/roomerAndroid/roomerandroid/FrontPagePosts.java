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

public class FrontPagePosts extends Fragment {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.front_page_posts, container, false);
        List<ModularPost> posts = new ArrayList<ModularPost>();
        for (int i = 0; i < 15; i++) {
            ModularPost singlePost = new ModularPost("Despite their infrequent occurence in common conversation, the Appalacian mountin range is the oldest mountain range in the world.", "This A. Name", "Mountains", R.drawable.bobby, R.drawable.mr_seal);
            posts.add(singlePost);
        }

        ListView postView = (ListView) rootView.findViewById(R.id.postLists);
        ModularPostArrayAdapter adapter = new ModularPostArrayAdapter(this.getActivity(),
                R.layout.list_item, posts);
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