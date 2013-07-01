package pl.patrykgrzegorczyk.batterydaydream.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * Monitors BatteryManager
 */
public class BatteryManagerMonitor extends BroadcastReceiver implements BatteryMonitor {

    private static final String TAG = "BatteryManagerMonitor";

    private BatteryStateListener mBatteryStateListener;
    private WeakReference<Context> mContext;

    public BatteryManagerMonitor(Context context) {
        mContext = new WeakReference<Context>(context);
    }

    @Override
    @Nullable public Context getContext() {
        return mContext.get();
    }

    @Override
    public void setContext(@Nullable Context context) {
        mContext = new WeakReference<Context>(context);
    }

    @Override
    public void startListening() {
        Context context = mContext.get();
        if(context == null) {
            return;
        }
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void stopListening() {
        Context context = mContext.get();
        if(context == null) {
            return;
        }
        context.unregisterReceiver(this);
    }

    @Override
    @Nullable public BatteryStateListener getBatteryStateListener() {
        return mBatteryStateListener;
    }

    @Override
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
        mBatteryStateListener.onBatteryStateChanged(batteryState);
    }


}
