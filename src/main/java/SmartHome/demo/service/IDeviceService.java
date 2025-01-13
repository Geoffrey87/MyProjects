package SmartHome.demo.service;

import SmartHome.demo.dto.DeviceDto;
import SmartHome.demo.entity.Device;

import java.util.List;

public interface IDeviceService {

    void createDevice(DeviceDto deviceDto);

    DeviceDto getDeviceById(int deviceId);

    List<DeviceDto> getAllDevicesByRoomId(int roomId);

}
