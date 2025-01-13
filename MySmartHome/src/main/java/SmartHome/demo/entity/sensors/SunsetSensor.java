package SmartHome.demo.entity.sensors;

import SmartHome.demo.entity.AbstractSensor;

public class SunsetSensor extends AbstractSensor {

    private final String sunsetTime;

    public SunsetSensor(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }
}
