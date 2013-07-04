package pl.patrykgrzegorczyk.batterydaydream.test;

import android.os.BatteryManager;
import android.test.AndroidTestCase;

import pl.patrykgrzegorczyk.batterydaydream.monitor.BatteryState;

public class BatteryStateTest extends AndroidTestCase {

    private BatteryState mBatteryState;

    @Override
    protected void setUp() throws Exception {
        mBatteryState = new BatteryState();
    }

    public void testShouldHave0AsDefaultLevel() {
        assertEquals(0, mBatteryState.getLevel());
    }

    public void testShouldHave100AsDefaultScale() {
        assertEquals(100, mBatteryState.getScale());
    }

    public void testShouldBeFull() {
        mBatteryState.setStatus(BatteryManager.BATTERY_STATUS_FULL);
        assertTrue(mBatteryState.isFull());
    }

    public void testShouldBeCharging() {
        mBatteryState.setStatus(BatteryManager.BATTERY_STATUS_CHARGING);
        assertTrue(mBatteryState.isCharging());
    }

    public void testShouldBeDischarging() {
        mBatteryState.setStatus(BatteryManager.BATTERY_STATUS_DISCHARGING);
        assertTrue(mBatteryState.isDischarging());
    }

    public void testShouldNotBeCharging() {
        mBatteryState.setStatus(BatteryManager.BATTERY_STATUS_NOT_CHARGING);
        assertTrue(mBatteryState.isNotCharging());
    }
}
