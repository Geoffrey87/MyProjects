package SmartHome.demo.repository;

import SmartHome.demo.entity.House;
import SmartHome.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseRepo extends JpaRepository<House, Integer> {

    boolean thereShouldBeOnlyOneHouse();

    Optional<House> getTheHouse();
}
