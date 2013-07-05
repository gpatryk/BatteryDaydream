package pl.patrykgrzegorczyk.batterydaydream.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import pl.patrykgrzegorczyk.batterydaydream.R;

/**
 * Fragment with preferences
 */
public class DefaultPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    //This constants should be in sync with preferences.xml
    public static final String KEY_NORMAL_BRIGHTNESS_MODE = "normal_brightness_mode";
    public static final String KEY_ANIMATION_TYPE = "animation_type";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        ListPreference animationType = (ListPreference) findPreference(KEY_ANIMATION_TYPE);
        animationType.setOnPreferenceChangeListener(this);

        updatePreferenceSummary(KEY_ANIMATION_TYPE, animationType.getValue());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if(key.equals(KEY_ANIMATION_TYPE)) {
            updatePreferenceSummary(key, newValue.toString());
        }
        return true;
    }
    private void updatePreferenceSummary(String key, String value) {
        findPreference(key).setSummary(value);
    }
}
