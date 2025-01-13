package SmartHome.demo.controller;

import SmartHome.demo.dto.RoomDto;
import SmartHome.demo.service.impl.RoomService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomDto roomDto) {
        roomService.createRoom(roomDto);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(roomDto);
    }




}
