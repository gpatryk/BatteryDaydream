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

    /**
     * Creates an {@link BatteryState}
     */
    public BatteryState() {
        mStatus = BatteryManager.BATTERY_STATUS_UNKNOWN;
        mScale = 100;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getScale() {
        return mScale;
    }

    public void setScale(int scale) {
        mScale = scale;
    }

    public int getTemperature() {
        return mTemperature;
    }

    public void setTemperature(int temperature) {
        mTemperature = temperature;
    }

    public int getVoltage() {
        return mVoltage;
    }

    public void setVoltage(int voltage) {
        mVoltage = voltage;
    }

    public void setStatus(int status) {
        mStatus = status;
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
