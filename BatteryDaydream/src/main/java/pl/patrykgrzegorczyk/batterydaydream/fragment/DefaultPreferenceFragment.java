package pl.patrykgrzegorczyk.batterydaydream.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import pl.patrykgrzegorczyk.batterydaydream.R;

/**
 * Fragment with preferences
 */
public class DefaultPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    //This constants should be in sync with preferences.xml
    public static final String KEY_NORMAL_BRIGHTNESS_MODE = "normal_brightness_mode";
    public static final String KEY_ANIMATION_TYPE = "animation_type";
    public static final String KEY_NIGHT_MODE = "night_mode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(KEY_ANIMATION_TYPE)) {
            //We have to update summary manually because of https://code.google.com/p/android/issues/detail?id=27867
            ListPreference preference = (ListPreference) findPreference(key);
            preference.setSummary(preference.getEntry());
        }
    }
}
