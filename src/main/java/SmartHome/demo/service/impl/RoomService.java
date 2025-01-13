package SmartHome.demo.service.impl;

import SmartHome.demo.dto.RoomDto;
import SmartHome.demo.entity.House;
import SmartHome.demo.entity.Room;
import SmartHome.demo.mapper.RoomMapper;
import SmartHome.demo.repository.HouseRepo;
import SmartHome.demo.repository.RoomRepo;
import SmartHome.demo.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService implements IRoomService {

    private final RoomRepo roomRepo;

    private final HouseRepo houseRepo;

    @Autowired
    public RoomService(RoomRepo roomRepo, HouseRepo houseRepo) {
        this.roomRepo = roomRepo;
        this.houseRepo = houseRepo;
    }


    @Override
    public void createRoom(RoomDto roomDto ) {
        Optional<House> houseOptional = houseRepo.findById(roomDto.getHouseId());
        if (houseOptional.isEmpty()) {
            throw new IllegalArgumentException("Configure the house before adding rooms.");
        }
        Room room = RoomMapper.dtoToDomain(roomDto, new Room());
        room.setHouse(houseOptional.get());
        roomRepo.save(room);
    }


    @Override
    public RoomDto getRoomById(int roomId) {
        Optional<Room> roomOptional = roomRepo.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new IllegalArgumentException("Room not found.");
        }
        return RoomMapper.domainToDto(roomOptional.get(), new RoomDto());
    }
}
