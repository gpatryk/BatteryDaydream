package pl.patrykgrzegorczyk.batterydaydream.monitor;

import android.content.Context;

/**
 * Creates BatteryMonitor instances
 */
public class BatteryMonitorFactory {

    /**
     * Returns instance of {@link BatteryMonitor}
     * @param context
     * @return
     */
    public static BatteryMonitor getMonitor(Context context) {
        return new BatteryManagerMonitor(context);
        //return new FakeBatteryMonitor(context, FakeBatteryMonitor.Mode.CONSTANT, 9);
    }
}
