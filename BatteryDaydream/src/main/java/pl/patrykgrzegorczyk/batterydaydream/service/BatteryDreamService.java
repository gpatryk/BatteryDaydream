package pl.patrykgrzegorczyk.batterydaydream.service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import pl.patrykgrzegorczyk.batterydaydream.R;
import pl.patrykgrzegorczyk.batterydaydream.fragment.DefaultPreferenceFragment;
import pl.patrykgrzegorczyk.batterydaydream.monitor.BatteryMonitor;
import pl.patrykgrzegorczyk.batterydaydream.monitor.BatteryMonitorFactory;
import pl.patrykgrzegorczyk.batterydaydream.monitor.BatteryState;
import pl.patrykgrzegorczyk.batterydaydream.widget.ChildAnimatingLayout;
import pl.patrykgrzegorczyk.batterydaydream.widget.ViewAnimatorProviderFactory;

/**
 * Main DayDream service
 */
public class BatteryDreamService extends DreamService implements BatteryMonitor.BatteryStateListener {

    private static final String TAG = "BatteryDreamService";

    private BatteryMonitor mBatteryMonitor;
    private TextView mBatteryLevelMajorView;
    private TextView mBatteryLevelMinorView;
    private ProgressBar mBatteryProgressBar;

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

        mBatteryLevelMajorView = (TextView) findViewById(R.id.battery_level_major);
        mBatteryLevelMinorView = (TextView) findViewById(R.id.battery_level_minor);
        mBatteryProgressBar = (ProgressBar) findViewById(R.id.battery_progress);

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
        int batteryLevel = batteryState.getLevel();
        String batteryLevelText = String.valueOf(batteryLevel);

        //First digit of battery progress
        String batteryLevelMajor = batteryLevelText.substring(0, 1);
        //Rest of the digits
        String batteryLevelMinor = "";

        if (batteryLevelText.length() > 1) {
            //battery percentage have more than 1 digit
            batteryLevelMinor = batteryLevelText.substring(1);
        }

        mBatteryLevelMajorView.setText(batteryLevelMajor);

        mBatteryLevelMinorView.setText(batteryLevelMinor);

        if (batteryLevel <= 10) {
            mBatteryLevelMajorView.setTextColor(getResources().getColor(R.color.battery_low));
            mBatteryLevelMinorView.setTextColor(getResources().getColor(R.color.battery_low));
        } else {
            mBatteryLevelMajorView.setTextColor(getResources().getColor(R.color.battery_normal));
            mBatteryLevelMinorView.setTextColor(getResources().getColor(R.color.battery_normal));
        }

        //reset full progress
        mBatteryProgressBar.setProgress(0);

        if (batteryLevel == batteryState.getScale()) {
            mBatteryLevelMajorView.setTextColor(getResources().getColor(R.color.battery_fully_charged));
            mBatteryLevelMinorView.setTextColor(getResources().getColor(R.color.battery_fully_charged));
            mBatteryProgressBar.setProgress(batteryLevel);
            return;
        }

        mBatteryProgressBar.setSecondaryProgress(batteryLevel);
    }
}
