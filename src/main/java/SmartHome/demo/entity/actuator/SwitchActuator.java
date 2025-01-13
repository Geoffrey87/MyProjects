package SmartHome.demo.entity.actuator;

import SmartHome.demo.entity.AbstractActuator;

public class SwitchActuator extends AbstractActuator {

    private boolean on_off;

    public void setSwitch(boolean value) {
        this.on_off = value;
    }
}
