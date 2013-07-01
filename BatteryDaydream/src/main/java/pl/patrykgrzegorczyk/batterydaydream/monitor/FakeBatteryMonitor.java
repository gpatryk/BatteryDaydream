package pl.patrykgrzegorczyk.batterydaydream.monitor;

import android.content.Context;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * Fake battery monitor
 */
public class FakeBatteryMonitor implements BatteryMonitor {

    private final Mode mMode;
    private WeakReference<Context> mContext;
    private BatteryStateListener mBatteryStateListener;
    private int mLevel;

    public FakeBatteryMonitor(Context context, Mode mode, int initalLevel) {
        mContext = new WeakReference<Context>(context);
        mMode = mode;
        mLevel = initalLevel;
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
        switch (mMode) {
            case CONSTANT:
                if(mBatteryStateListener == null) {
                    return;
                }

                mBatteryStateListener.onBatteryStateChanged(getBatteryState());
                return;
            case CONTINOUS:
                return;
        }
    }

    @Override
    public void stopListening() {

    }

    @Override
    @Nullable public BatteryStateListener getBatteryStateListener() {
        return mBatteryStateListener;
    }

    @Override
    public void setBatteryStateListener(@Nullable BatteryStateListener batteryStateListener) {
        mBatteryStateListener = batteryStateListener;
    }

    public BatteryState getBatteryState() {
        BatteryState batteryState = new BatteryState();
        batteryState.setLevel(mLevel);

        return batteryState;
    }

    public enum Mode {
        CONSTANT,
        CONTINOUS
    }
}
