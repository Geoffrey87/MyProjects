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

    @Min(value = -3, message = "Floor can't be less than -3")
    @Max(value = 5, message = "Floor can't be more than 5")
    private byte floor;

    @Min(value = 0, message = "Height can't be negative")
    @Max(value = 100, message = "Height can't be greater than 100")
    private float length;

    @Min(value = 0, message = "Height can't be negative")
    @Max(value = 100, message = "Height can't be greater than 100")
    private float width;

    @Min(value = 0, message = "Height can't be negative")
    @Max(value = 100, message = "Height can't be greater than 100")
    private float height;

    private int houseId;

    private Set<Integer> deviceIds;
}
