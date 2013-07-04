package pl.patrykgrzegorczyk.batterydaydream.activity;

import android.app.Activity;
import android.os.Bundle;

import pl.patrykgrzegorczyk.batterydaydream.fragment.DebugPreferenceFragment;

/**
 * Settings activity containing debug settings
 */
public class DebugSettingsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DebugPreferenceFragment()).commit();
    }
}