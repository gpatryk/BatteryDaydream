package pl.patrykgrzegorczyk.batterydaydream.fragment;

import android.os.Bundle;

import pl.patrykgrzegorczyk.batterydaydream.R;

/**
 * Fragment with preferences
 */
public class DefaultPreferenceFragment extends android.preference.PreferenceFragment {

    public static final String KEY_NORMAL_BRIGHTNESS_MODE = "normal_brightness_mode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
