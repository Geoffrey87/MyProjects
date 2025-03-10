package SmartHome.demo.repository;

import SmartHome.demo.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Integer> {

   List<Device> findAllByRoom_RoomId(int roomId);
}
