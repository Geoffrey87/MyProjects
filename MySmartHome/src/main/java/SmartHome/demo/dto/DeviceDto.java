package SmartHome.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceDto {


    @NotBlank(message="Device Name must not be blank")
    private String deviceName;

    @NotBlank(message="Device Type must not be blank")
    private String deviceType;


    private int roomId;


}
