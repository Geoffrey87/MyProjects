package SmartHome.demo.mapper;

import SmartHome.demo.dto.RoomDto;
import SmartHome.demo.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public static RoomDto domainToDto(Room room, RoomDto roomDto){
        roomDto.setRoomName(room.getRoomName());
        roomDto.setFloor(room.getFloor());
        roomDto.setLength(room.getLength());
        roomDto.setWidth(room.getWidth());
        roomDto.setHeight(room.getHeight());
        return roomDto;
    }

    public static Room dtoToDomain(RoomDto roomDto, Room room){
        room.setRoomName(roomDto.getRoomName());
        room.setFloor(roomDto.getFloor());
        room.setLength(roomDto.getLength());
        room.setWidth(roomDto.getWidth());
        room.setHeight(roomDto.getHeight());
        return room;
    }


}
