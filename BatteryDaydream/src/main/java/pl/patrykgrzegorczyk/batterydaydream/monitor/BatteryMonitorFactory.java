package pl.patrykgrzegorczyk.batterydaydream.monitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import pl.patrykgrzegorczyk.batterydaydream.fragment.DebugPreferenceFragment;

/**
 * Creates BatteryMonitor instances
 */
public class BatteryMonitorFactory {

    private static final String TAG = "BatteryMonitorFactory";

    private static final int DEFAULT_INITIAL_BATTERY_LEVEL = 50;

    /**
     * Creates {@link BatteryMonitor} basing on preferences
     * @param context
     * @return instance of {@link BatteryMonitor}
     */
    public static BatteryMonitor getMonitor(Context context) {
        //Check if fake battery monitor is enabled in settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(preferences.getBoolean(DebugPreferenceFragment.KEY_USE_FAKE_BATTERY_MONITOR, false)) {
            //Read Fake battery settings
            FakeBatteryMonitor.Mode mode = preferences.getBoolean(DebugPreferenceFragment.KEY_WORK_IN_CONTINUOUS_MODE, false) ? FakeBatteryMonitor.Mode.CONTINUOUS : FakeBatteryMonitor.Mode.CONSTANT;
            int initialBatteryLevel = DEFAULT_INITIAL_BATTERY_LEVEL;
            try {
                initialBatteryLevel = Integer.parseInt(preferences.getString(DebugPreferenceFragment.KEY_INITIAL_BATTERY_LEVEL, String.valueOf(DEFAULT_INITIAL_BATTERY_LEVEL)));
            } catch (NumberFormatException e) {
                Log.e(TAG, "Couldn't parse initial battery level", e);
            }
            return new FakeBatteryMonitor(context,mode, initialBatteryLevel);
        }

        //Return real battery monitor
        return new BatteryManagerMonitor(context);
    }
}
