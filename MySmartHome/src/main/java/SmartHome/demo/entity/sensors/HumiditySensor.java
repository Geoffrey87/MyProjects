package SmartHome.demo.entity.sensors;

import SmartHome.demo.entity.AbstractSensor;
import lombok.Getter;

@Getter
public class HumiditySensor extends AbstractSensor {

    private final double humidity;

    public HumiditySensor(double humidity) {
        this.humidity = humidity;
    }

}
