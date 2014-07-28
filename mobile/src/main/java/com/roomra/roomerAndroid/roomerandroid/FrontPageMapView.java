package com.roomra.roomerAndroid.roomerandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Field;

public class FrontPageMapView extends Fragment {
    private static GoogleMap mMap;
    public static View updater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.front_page_map_view, container, false);

        return rootView;
    }
    /***** Sets up the map if it is possible to do so *****/
    public static void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) RoomerFrontPage.fragmentManager
                    .findFragmentById(R.id.mapview)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(30.284719, -97.734164);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        // For dropping a marker at a point on the Map
        //mMap.addMarker(new MarkerOptions().position(new LatLng(30.0,30.0)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.0,30.0), 12.0f));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        updater = view;
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) RoomerFrontPage.fragmentManager
                    .findFragmentById(R.id.mapview)).getMap();
            mMap.setOnMapClickListener(new OnMapClickListener() {
                @Override
                public void onMapClick(LatLng arg0) {
                    RelativeLayout mapStory = (RelativeLayout) updater.findViewById(R.id.mapStory);
                    ((ViewManager)mapStory.getParent()).removeView(mapStory);

                }
            });
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}