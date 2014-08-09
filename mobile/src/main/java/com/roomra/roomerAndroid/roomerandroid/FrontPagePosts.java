package com.roomra.roomerAndroid.roomerandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
public class FrontPagePosts extends Fragment implements OnDragListener {

    public static TextView radiusNumber;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static Context context;
    public static SharedPreferencesEditor spe;
    public static ArrayList<BasicNameValuePair> pv = new ArrayList<BasicNameValuePair>();
    public static Task task;
    public static AsyncConnection ac;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setupView(inflater,container);

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
        view.setOnDragListener(this);
    }
    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        Boolean containsDragable = true;
        int dragAction = dragEvent.getAction();
        View dragView = (View) dragEvent.getLocalState();
        if (dragAction == DragEvent.ACTION_DRAG_EXITED) {
            containsDragable = false;
        } else if (dragAction == DragEvent.ACTION_DRAG_ENTERED) {
            containsDragable = true ;
        } else if (dragAction == DragEvent.ACTION_DRAG_ENDED) {
            dragView.setVisibility(View.VISIBLE);
        } else if (dragAction == DragEvent.ACTION_DROP && containsDragable) {

            dragView.setVisibility(View.VISIBLE);

        } else if (dragAction == DragEvent.ACTION_DRAG_LOCATION) {
            ((TextView) radiusNumber).setText(dragEvent.getY()+"");

        }
        return true;
    }
    public View setupView(LayoutInflater inflater, ViewGroup container) {
        this.spe = new SharedPreferencesEditor(getActivity(), RoomerConstants.PREFS_FILE);
        View rootView = inflater.inflate(R.layout.front_page_posts, container, false);
        ListView postView = (ListView) rootView.findViewById(R.id.postView);
        List<ModularPost> posts = new ArrayList<ModularPost>();

        for (int i = 0; i < 15; i++) {
            ModularPost singlePost = new ModularPost("Despite their infrequent occurence in common conversation, the Appalacian mountin range is the oldest mountain range in the world.", "This A. Name", "Mountains", R.drawable.bobby, R.drawable.mr_seal);
            posts.add(singlePost);
        }
        ModularPostArrayAdapter adapter = new ModularPostArrayAdapter(this.getActivity(),
                R.layout.post_item, posts);
        postView.setAdapter(adapter);
        adapter.setupAdapterClickListener(postView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        SeekBar mSeekbarItem = (SeekBar) rootView.findViewById(R.id.radii);
        radiusNumber = (TextView) rootView.findViewById(R.id.radiusNumber);
        RadiusSelector.updater = radiusNumber;
        //setup listener
        SharedPreferences.OnSharedPreferenceChangeListener sharedPreferencesListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key == "key_mark_buffer") {
                    Log.d("These are a few of the Marks", spe.getMarkBuffer());
                }
            }
        };
        getMarks();
        spe.getPref().registerOnSharedPreferenceChangeListener(sharedPreferencesListener);
        return rootView;
    }

    public boolean getMarks() {
        pv.clear();
        pv.add(new BasicNameValuePair("accessToken", this.spe.getAuthToken() ));
        task = new Task(TaskType.GETMARKS, "db/marks/get", pv);
        ac = new AsyncConnection(false, task);
        ac.connect();
        return true;
    }
}