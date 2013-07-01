package pl.patrykgrzegorczyk.batterydaydream.monitor;

import android.content.Context;

import org.jetbrains.annotations.Nullable;

/**
 * Monitors battery state. Start monitoring process by calling {@link #startListening()} and
 * stop by calling {@link #stopListening()}. {@link BatteryStateListener} is noticed about battery
 * state changes.
 */
public interface BatteryMonitor {
    @Nullable Context getContext();

    void setContext(@Nullable Context context);

    void startListening();

    void stopListening();

    @Nullable BatteryStateListener getBatteryStateListener();

    void setBatteryStateListener(@Nullable BatteryStateListener batteryStateListener);

    /**
     * Interface definition for a callback to be invoked when a battery state changed
     */
    interface BatteryStateListener {
        /**
         * Called when a battery state has changed
         * @param batteryState actual battery state
         */
        void onBatteryStateChanged(BatteryState batteryState);
    }
}
