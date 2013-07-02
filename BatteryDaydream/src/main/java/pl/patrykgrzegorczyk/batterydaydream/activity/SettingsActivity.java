package pl.patrykgrzegorczyk.batterydaydream.activity;

import android.app.Activity;
import android.os.Bundle;

import pl.patrykgrzegorczyk.batterydaydream.fragment.DefaultPreferenceFragment;

public class SettingsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new DefaultPreferenceFragment()).commit();
    }
}