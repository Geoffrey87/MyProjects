package SmartHome.demo.entity.actuator;

import SmartHome.demo.entity.AbstractActuator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class BlindRollerActuator extends AbstractActuator {

    @Min(0)
    @Max(100)
    private byte value;

    public void setValue(byte value) {
        this.value = value;
    }
}
