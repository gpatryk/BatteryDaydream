package pl.patrykgrzegorczyk.batterydaydream.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import pl.patrykgrzegorczyk.batterydaydream.R;

/**
 * Visual indicator of battery level. Level can be set by calling {@link #setLevel(int)} and
 * scale by calling {@link #setScale(int)}
 */
public class BatteryLevel extends LinearLayout {

    private ProgressBar mProgressBar;
    private TextView mMajorTextView;
    private TextView mMinorTextView;

    private int mScale = 100;
    private int mLevel = 0;

    public BatteryLevel(Context context) {
        super(context);
    }

    public BatteryLevel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BatteryLevel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater.from(getContext()).inflate(R.layout.widget_battery_level, this);

        mProgressBar = (ProgressBar) findViewById(R.id.battery_progress);
        mMajorTextView = (TextView) findViewById(R.id.battery_level_major);
        mMinorTextView = (TextView) findViewById(R.id.battery_level_minor);
    }

    /**
     * Returns battery scale
     * @return a battery scale
     */
    public int getScale() {
        return mScale;
    }

    /**
     * Sets battery scale
     * @param scale battery scale
     */
    public void setScale(int scale) {
        mScale = scale;

        if(mProgressBar == null) {
            return;
        }

        mProgressBar.setMax(mScale);

        updateView(mLevel, mScale);
    }

    /**
     * Returns battery level
     * @return a battery level
     */
    public int getLevel() {
        return mLevel;
    }

    /**
     * Sets battery level
     * @param level battery level
     */
    public void setLevel(int level) {
        mLevel = level;

        if(mProgressBar == null) {
            return;
        }

        if(mMajorTextView == null) {
            return;
        }

        if(mMinorTextView == null) {
            return;
        }

        updateView(mLevel, mScale);
    }

    private void updateView(int batteryLevel, int scale) {
        String batteryLevelText = String.valueOf(batteryLevel);

        //First digit of battery progress
        String batteryLevelMajor = batteryLevelText.substring(0, 1);
        //Rest of the digits
        String batteryLevelMinor = "";

        if (batteryLevelText.length() > 1) {
            //battery percentage have more than 1 digit
            batteryLevelMinor = batteryLevelText.substring(1);
        }

        mMajorTextView.setText(batteryLevelMajor);

        mMinorTextView.setText(batteryLevelMinor);

        if (batteryLevel <= 10) {
            mMajorTextView.setTextColor(getResources().getColor(R.color.battery_low));
            mMinorTextView.setTextColor(getResources().getColor(R.color.battery_low));
        } else {
            mMajorTextView.setTextColor(getResources().getColor(R.color.battery_normal));
            mMinorTextView.setTextColor(getResources().getColor(R.color.battery_normal));
        }

        //reset full progress
        mProgressBar.setProgress(0);

        if (batteryLevel == scale) {
            mMajorTextView.setTextColor(getResources().getColor(R.color.battery_fully_charged));
            mMinorTextView.setTextColor(getResources().getColor(R.color.battery_fully_charged));
            mProgressBar.setProgress(batteryLevel);
            return;
        }

        mProgressBar.setSecondaryProgress(batteryLevel);
    }
}
