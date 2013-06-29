package pl.patrykgrzegorczyk.batterydaydream.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.service.dreams.DreamService;
import android.util.Log;
import android.widget.TextView;

import pl.patrykgrzegorczyk.batterydaydream.BatteryMonitor;
import pl.patrykgrzegorczyk.batterydaydream.R;

/**
 * Main DayDream service
 */
public class BatteryDreamService extends DreamService implements BatteryMonitor.BatteryInfoListener {

    private static final String TAG = "BatteryDreamService";

    private BatteryMonitor mBatteryMonitor;
    private TextView mBatteryLevelView;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "onAttachedToWindow()");
        }

        mBatteryMonitor = new BatteryMonitor(this);
        mBatteryMonitor.setBatteryInfoListener(this);

        setInteractive(false);
        setFullscreen(true);
        setScreenBright(false);
        setContentView(R.layout.battery_daydream);

        mBatteryLevelView = (TextView) findViewById(R.id.battery_level);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        mBatteryMonitor.startListening();
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

        mBatteryMonitor.stopListening();
    }

    @Override
    public void onBatteryLevelChanged(int batteryLevel) {
        //Update battery level info
        mBatteryLevelView.setText(String.valueOf(batteryLevel));
    }
}
