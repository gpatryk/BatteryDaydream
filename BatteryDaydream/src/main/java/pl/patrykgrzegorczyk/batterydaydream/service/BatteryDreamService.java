package pl.patrykgrzegorczyk.batterydaydream.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import pl.patrykgrzegorczyk.batterydaydream.R;
import pl.patrykgrzegorczyk.batterydaydream.fragment.DefaultPreferenceFragment;
import pl.patrykgrzegorczyk.batterydaydream.monitor.BatteryMonitor;
import pl.patrykgrzegorczyk.batterydaydream.monitor.BatteryMonitorFactory;
import pl.patrykgrzegorczyk.batterydaydream.monitor.BatteryState;
import pl.patrykgrzegorczyk.batterydaydream.widget.BatteryLevel;
import pl.patrykgrzegorczyk.batterydaydream.widget.ChildAnimatingLayout;
import pl.patrykgrzegorczyk.batterydaydream.widget.ViewAnimatorProviderFactory;

/**
 * Main DayDream service
 */
public class BatteryDreamService extends DreamService implements BatteryMonitor.BatteryStateListener {

    private static final String TAG = "BatteryDreamService";

    private BatteryMonitor mBatteryMonitor;
    private BatteryLevel mBatteryLevel;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onAttachedToWindow()");
        }

        mBatteryMonitor = BatteryMonitorFactory.getMonitor(this);
        mBatteryMonitor.setBatteryStateListener(this);

        setInteractive(false);
        setFullscreen(true);
        setScreenBright(false);
        setContentView(R.layout.battery_daydream);

        ChildAnimatingLayout animatingLayout = (ChildAnimatingLayout) findViewById(R.id.animating_layout);
        animatingLayout.setViewAnimationProvider(ViewAnimatorProviderFactory.getViewAnimatorProvider(this));

        mBatteryLevel = (BatteryLevel) findViewById(R.id.battery_level);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setScreenBright(preferences.getBoolean(DefaultPreferenceFragment.KEY_NORMAL_BRIGHTNESS_MODE, false));

        boolean nightMode = preferences.getBoolean(DefaultPreferenceFragment.KEY_NIGHT_MODE, true);
        //In night mode decrease alpha to 0.3
        animatingLayout.setAlpha(nightMode ? 0.3f : 1.0f);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDetachedFromWindow()");
        }

        mBatteryMonitor.setBatteryStateListener(null);
        mBatteryMonitor.setContext(null);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDreamingStarted()");
        }

        mBatteryMonitor.startListening();
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDreamingStopped()");
        }

        mBatteryMonitor.stopListening();
    }

    @Override
    public void onBatteryStateChanged(@NotNull BatteryState batteryState) {
        //Update battery level info
        mBatteryLevel.setScale(batteryState.getScale());
        mBatteryLevel.setLevel(batteryState.getLevel());

    }
}
