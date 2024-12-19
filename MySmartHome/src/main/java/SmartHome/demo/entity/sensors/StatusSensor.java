package SmartHome.demo.entity.sensors;

import SmartHome.demo.entity.AbstractSensor;

public class StatusSensor extends AbstractSensor {

    private final boolean status;

    public StatusSensor(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
