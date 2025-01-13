package SmartHome.demo.HouseTest;
import SmartHome.demo.entity.House;
import SmartHome.demo.entity.Room;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class HouseTest {

    @Test
    public void shouldInstantiateHouseWhenGivenValidParameters() {
        //Arrange
        int houseId = 1;
        String address = "Rua";
        String zipCode = "3000-000";
        double latitude = 12.001;
        double longitude = -18.023;
        Set<Room> rooms = mock(Set.class);

        //Act
        House house = new House(houseId,address,zipCode,latitude,longitude,rooms);

        //Assert
        assertNotNull(house);
    }

    @Test
    public void GetHouseShouldWork_WhenGivenValidParameters() {
        // Arrange
        int houseId = 1;
        String address = "Rua";
        String zipCode = "3000-000";
        double latitude = 12.001;
        double longitude = -18.023;
        Set<Room> rooms = new HashSet<>();

        // Act
        House house = new House(houseId, address, zipCode, latitude, longitude, rooms);

        // Assert
        assertEquals(houseId, house.getHouseId());
        assertEquals(address, house.getAddress());
        assertEquals(zipCode, house.getZipCode());
        assertEquals(latitude, house.getLatitude(), 0.001);
        assertEquals(longitude, house.getLongitude(), 0.001);
        assertEquals(rooms, house.getRooms());
    }

    @Test
    public void SetterHouseShouldWork_WhenGivenValidParameters() {
        // Arrange
        String address = "Rua";
        String zipCode = "3000-000";
        double latitude = 12.001;
        double longitude = -18.023;
        Set<Room> rooms = new HashSet<>();

        House house = new House();

        // Act
        house.setAddress(address);
        house.setZipCode(zipCode);
        house.setLatitude(latitude);
        house.setLongitude(longitude);
        house.setRooms(rooms);

        // Assert
        assertEquals(address,house.getAddress());
        assertEquals(zipCode, house.getZipCode());
        assertEquals(latitude, house.getLatitude(), 0.001);
        assertEquals(longitude, house.getLongitude(), 0.001);
        assertEquals(rooms, house.getRooms());
    }
}


