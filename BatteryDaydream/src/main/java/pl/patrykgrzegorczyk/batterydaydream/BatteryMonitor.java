package pl.patrykgrzegorczyk.batterydaydream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * Monitors battery state. Start monitoring process by calling {@link #startListening()} and
 * stop by calling {@link #stopListening()}. {@link BatteryStateListener} is noticed about battery
 * state changes.
 */
public class BatteryMonitor extends BroadcastReceiver {

    private static final String TAG = "BatteryMonitor";

    private BatteryStateListener mBatteryStateListener;
    private WeakReference<Context> mContext;

    public BatteryMonitor(Context context) {
        mContext = new WeakReference<Context>(context);
    }

    public void startListening() {
        Context context = mContext.get();
        if(context == null) {
            return;
        }
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void stopListening() {
        Context context = mContext.get();
        if(context == null) {
            return;
        }
        context.unregisterReceiver(this);
    }

    @Nullable public BatteryStateListener getBatteryStateListener() {
        return mBatteryStateListener;
    }

    public void setBatteryStateListener(@Nullable BatteryStateListener batteryStateListener) {
        mBatteryStateListener = batteryStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "onReceive() " + intent);
        }

        if(!intent.hasExtra(BatteryManager.EXTRA_LEVEL)) {
            //Only interested in intents with battery level
            return;
        }

        BatteryState batteryState = new BatteryState(intent.getExtras());

        if(mBatteryStateListener == null) {
            return;
        }

        //Notify listener
        mBatteryStateListener.onBatteryLevelChanged(batteryState);
    }

    /**
     * Interface definition for a callback to be invoked when a battery state changed
     */
    public interface BatteryStateListener {
        /**
         * Called when a battery level has changed
         * @param batteryState
         */
        void onBatteryLevelChanged(BatteryState batteryState);
    }

    /**
     * Represents battery state.
     */
    public static class BatteryState {
        private int mLevel;
        private int mScale;
        private int mTemperature;
        private int mVoltage;
        private int mStatus;

        /**
         * Creates an {@link BatteryState}
         * @param args Extras from {@link Intent#ACTION_BATTERY_CHANGED} intent.
         * @see BatteryManager
         */
        public BatteryState(Bundle args) {
            mLevel = args.getInt(BatteryManager.EXTRA_LEVEL, 0);
            mScale = args.getInt(BatteryManager.EXTRA_SCALE, 100);
            mTemperature = args.getInt(BatteryManager.EXTRA_TEMPERATURE, 0);
            mVoltage = args.getInt(BatteryManager.EXTRA_VOLTAGE, 0);
            mStatus = args.getInt(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
        }

        public int getLevel() {
            return mLevel;
        }

        public int getScale() {
            return mScale;
        }

        public int getTemperature() {
            return mTemperature;
        }

        public int getVoltage() {
            return mVoltage;
        }

        public boolean isFull() {
            return mStatus == BatteryManager.BATTERY_STATUS_FULL;
        }

        public boolean isCharging() {
            return mStatus == BatteryManager.BATTERY_STATUS_CHARGING;
        }

        public boolean isDischarging() {
            return mStatus == BatteryManager.BATTERY_STATUS_DISCHARGING;
        }

        public boolean isNotCharging() {
            return mStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING;
        }
    }


}
