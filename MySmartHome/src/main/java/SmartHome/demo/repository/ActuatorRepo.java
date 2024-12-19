package SmartHome.demo.repository;

import SmartHome.demo.entity.AbstractActuator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActuatorRepo extends JpaRepository<AbstractActuator, Integer> {

    List<AbstractActuator> findAllByDeviceId(int deviceId);
}
