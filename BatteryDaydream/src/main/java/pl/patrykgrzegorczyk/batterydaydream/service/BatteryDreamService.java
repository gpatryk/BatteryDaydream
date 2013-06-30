package pl.patrykgrzegorczyk.batterydaydream.service;

import android.service.dreams.DreamService;
import android.util.Log;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.graphics.Color;

import pl.patrykgrzegorczyk.batterydaydream.BatteryMonitor;
import pl.patrykgrzegorczyk.batterydaydream.R;

/**
 * Main DayDream service
 */
public class BatteryDreamService extends DreamService implements BatteryMonitor.BatteryInfoListener {

    private static final String TAG = "BatteryDreamService";

    private BatteryMonitor mBatteryMonitor;
    private TextView mBatteryLevelView;
    private TextView mBatteryLevelView2;
    private ProgressBar mBatteryProgressBar;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onAttachedToWindow()");
        }

        mBatteryMonitor = new BatteryMonitor(this);
        mBatteryMonitor.setBatteryInfoListener(this);

        setInteractive(false);
        setFullscreen(true);
        setScreenBright(false);
        setContentView(R.layout.battery_daydream);

        mBatteryLevelView = (TextView) findViewById(R.id.battery_level);
        mBatteryLevelView2 = (TextView) findViewById(R.id.battery_level_2);

        mBatteryProgressBar = (ProgressBar) findViewById(R.id.battery_progress);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDreamingStarted()");
        }

        mBatteryMonitor.startListening();
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

        if(Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onDreamingStopped()");
        }

        mBatteryMonitor.stopListening();
    }

    @Override
    public void onBatteryLevelChanged(int batteryLevel) {
        //Update battery level info

        //batteryLevel = 1;

        String batteryLevelText = String.valueOf(batteryLevel);

        String firstText = batteryLevelText.substring(0, 1);
        String secondText = batteryLevelText.substring(1);

        mBatteryLevelView.setText(firstText);
        mBatteryLevelView2.setText(secondText);

        if(batteryLevel <= 10){
            mBatteryLevelView.setTextColor(Color.RED);
            mBatteryLevelView2.setTextColor(Color.RED);
        }else{
            mBatteryLevelView.setTextColor(Color.WHITE);
            mBatteryLevelView2.setTextColor(Color.WHITE);
        }

        if(batteryLevel == 100){
            mBatteryLevelView.setTextColor(Color.rgb(1,1,1));
            mBatteryLevelView2.setTextColor(Color.rgb(1,1,1));
            mBatteryProgressBar.setProgress(batteryLevel);
        }
        else{
            mBatteryProgressBar.setSecondaryProgress(batteryLevel);
        }

    }
}
