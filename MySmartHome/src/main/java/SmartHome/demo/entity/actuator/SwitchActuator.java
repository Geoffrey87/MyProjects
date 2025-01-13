package SmartHome.demo.entity.actuator;

import SmartHome.demo.entity.AbstractActuator;

public class SwitchActuator extends AbstractActuator {

    private boolean value;

    public void setValue(boolean value) {
        this.value = value;
    }
}
