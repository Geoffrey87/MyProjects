package SmartHome.demo.entity.sensors;

import SmartHome.demo.entity.AbstractSensor;

public class TemperatureSensor extends AbstractSensor {

    private final double temperature;

    public TemperatureSensor(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }
}
