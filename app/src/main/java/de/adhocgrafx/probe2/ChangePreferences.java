package de.adhocgrafx.probe2;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

public class ChangePreferences extends PreferenceActivity {

    /*
    @Override
    public void onBuildHeaders(List<Header> myPrefHeaders) {
        loadHeadersFromResource(R.xml.preference_headers, myPrefHeaders);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return DefaultPreferenceFragment.class.getName().equals(fragmentName);
    }
    */

    // todo entweder mit headers oder ohne
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_cluster_1);
    }
}
