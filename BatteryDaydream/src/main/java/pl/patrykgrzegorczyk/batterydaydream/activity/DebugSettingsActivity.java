package pl.patrykgrzegorczyk.batterydaydream.activity;

import android.app.Activity;
import android.os.Bundle;

import pl.patrykgrzegorczyk.batterydaydream.fragment.DebugPreferenceFragment;

public class DebugSettingsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DebugPreferenceFragment()).commit();
    }
}