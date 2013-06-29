package pl.patrykgrzegorczyk.batterydaydream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * Monitors battery state. Start monitoring process by calling {@link #startListening()} and
 * stop by calling {@link #stopListening()}. {@link BatteryInfoListener} is noticed about battery
 * state changes.
 */
public class BatteryMonitor extends BroadcastReceiver {

    private BatteryInfoListener mBatteryInfoListener;
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

    @Nullable public BatteryInfoListener getBatteryInfoListener() {
        return mBatteryInfoListener;
    }

    public void setBatteryInfoListener(@Nullable BatteryInfoListener batteryInfoListener) {
        mBatteryInfoListener = batteryInfoListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!intent.hasExtra(BatteryManager.EXTRA_LEVEL)) {
            //Only interested in intents with battery level
            return;
        }

        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        if(mBatteryInfoListener == null) {
            return;
        }

        //Notify listener
        mBatteryInfoListener.onBatteryLevelChanged(batteryLevel);
    }

    /**
     * Interface definition for a callback to be invoked when a battery state changed
     */
    public interface BatteryInfoListener {
        /**
         * Called when a battery level has changed
         * @param batteryLevel actual battery level
         */
        void onBatteryLevelChanged(int batteryLevel);
    }

}
