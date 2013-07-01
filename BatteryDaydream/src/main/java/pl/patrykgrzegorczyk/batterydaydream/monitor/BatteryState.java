package pl.patrykgrzegorczyk.batterydaydream.monitor;

import android.os.BatteryManager;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

/**
 * Represents battery state.
 */
public class BatteryState {
    private int mLevel;
    private int mScale;
    private int mTemperature;
    private int mVoltage;
    private int mStatus;

    /**
     * Creates an {@link BatteryState}
     * @param args Extras from {@link android.content.Intent#ACTION_BATTERY_CHANGED} intent.
     * @see android.os.BatteryManager
     */
    public BatteryState(@NotNull Bundle args) {
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
