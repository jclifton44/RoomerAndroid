package com.roomra.roomerAndroid.roomerandroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by jeremyclifton on 7/30/14.
 */
public class PreferencesPanel extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_headers);
    }
}

