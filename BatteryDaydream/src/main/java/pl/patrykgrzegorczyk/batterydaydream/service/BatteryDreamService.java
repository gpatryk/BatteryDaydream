package pl.patrykgrzegorczyk.batterydaydream.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.service.dreams.DreamService;
import android.util.Log;
import android.widget.TextView;

import pl.patrykgrzegorczyk.batterydaydream.R;

/**
 * Main DayDream service
 */
public class BatteryDreamService extends DreamService {

    private static final String TAG = "BatteryDreamService";

    private BatteryBroadcastReceiver mBatteryBroadcastReceiver = new BatteryBroadcastReceiver();
    private TextView mBatteryLevelView;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "onAttachedToWindow()");
        }

        setInteractive(false);
        setFullscreen(true);
        setScreenBright(false);
        setContentView(R.layout.battery_daydream);

        mBatteryLevelView = (TextView) findViewById(R.id.battery_level);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        registerReceiver(mBatteryBroadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

        unregisterReceiver(mBatteryBroadcastReceiver);
    }

    private void onBatteryLevelChanged(int batteryLevel) {
        //Update battery level info
        mBatteryLevelView.setText(String.valueOf(batteryLevel));
    }

    private class BatteryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(!intent.hasExtra(BatteryManager.EXTRA_LEVEL)) {
                //Only interested in intents with battery level
                return;
            }

            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            onBatteryLevelChanged(batteryLevel);
        }
    }
}
