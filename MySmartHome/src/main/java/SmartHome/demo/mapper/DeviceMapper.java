package SmartHome.demo.mapper;

import SmartHome.demo.dto.DeviceDto;
import SmartHome.demo.entity.Device;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceMapper {

    public static DeviceDto domainToDto(Device device, DeviceDto deviceDto){
        deviceDto.setDeviceName(device.getDeviceName());
        deviceDto.setDeviceType(device.getDeviceType());

        return deviceDto;
    }

    public static Device dtoToDomain(DeviceDto deviceDto, Device device){
        device.setDeviceName(deviceDto.getDeviceName());
        device.setDeviceType(deviceDto.getDeviceType());

        return device;
    }

    public static List<DeviceDto> domainToDto(List<Device> devices){
        return devices.stream().
                map(device -> domainToDto(device, new DeviceDto())).
                collect(Collectors.toList());
    }

    public static List<Device> dtoToDomain(List<DeviceDto> deviceDtos){
        return deviceDtos.stream().
                map(deviceDto -> dtoToDomain(deviceDto, new Device())).
                collect(Collectors.toList());
    }
}
