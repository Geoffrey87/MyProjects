package SmartHome.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RoomDto {

    @NotBlank(message="Room Name must not be blank")
    @Size(min=3 , message="Room Name must be at least 3 characters long")
    private String roomName;


    private byte floor;

    private float length;

    private float width;

    private float height;

    private int houseId;

    private Set<Integer> deviceIds;
}
