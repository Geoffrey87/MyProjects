package SmartHome.demo.repository;

import SmartHome.demo.entity.AbstractSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepo extends JpaRepository<AbstractSensor, Integer> {

    List<AbstractSensor> findAllByDeviceId(int deviceId);
}
