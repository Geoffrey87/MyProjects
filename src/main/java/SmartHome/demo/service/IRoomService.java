package SmartHome.demo.service;

import SmartHome.demo.dto.RoomDto;

public interface IRoomService {

    void createRoom(RoomDto roomDto);

    RoomDto getRoomById(int roomId);

}
