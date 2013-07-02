package pl.patrykgrzegorczyk.batterydaydream.monitor;

import android.content.Context;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * Fake battery monitor
 */
public class FakeBatteryMonitor implements BatteryMonitor {

    private static final int DELAY_MILLIS = 100;

    private final Mode mMode;
    private WeakReference<Context> mContext;
    private BatteryStateListener mBatteryStateListener;
    private int mLevel;
    private boolean mIncrement = true;
    private final Handler mLevelChangeHandler = new Handler();
    private final Runnable mLevelChangeRunnable = new Runnable() {
        @Override
        public void run() {
            if(mLevel == 0 || mLevel == 100) {
                //Change progress direction
                mIncrement = !mIncrement;
            }

            mLevel += mIncrement ? 1 : -1;
            if(mBatteryStateListener != null) {
                //notify listener
                mBatteryStateListener.onBatteryStateChanged(getBatteryState());
            }
            mLevelChangeHandler.postDelayed(this, DELAY_MILLIS);
        }
    };

    public FakeBatteryMonitor(Context context, Mode mode, int initialLevel) {
        mContext = new WeakReference<Context>(context);
        mMode = mode;
        mLevel = initialLevel;
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
        if(mBatteryStateListener == null) {
            return;
        }

        mBatteryStateListener.onBatteryStateChanged(getBatteryState());

        switch (mMode) {
            case CONTINUOUS:
                mLevelChangeHandler.postDelayed(mLevelChangeRunnable, 100);
                break;
        }

    }

    @Override
    public void stopListening() {
        mLevelChangeHandler.removeCallbacks(mLevelChangeRunnable);
    }

    @Override
    @Nullable public BatteryStateListener getBatteryStateListener() {
        return mBatteryStateListener;
    }

    @Override
    public void setBatteryStateListener(@Nullable BatteryStateListener batteryStateListener) {
        mBatteryStateListener = batteryStateListener;
    }

    @NotNull public BatteryState getBatteryState() {
        BatteryState batteryState = new BatteryState();
        batteryState.setLevel(mLevel);

        return batteryState;
    }

    /**
     * {@link FakeBatteryMonitor} working mode
     */
    public enum Mode {
        /** Notify with constant (initial) value */
        CONSTANT,
        /** Continuously notify listener with changed value */
        CONTINUOUS
    }
}
