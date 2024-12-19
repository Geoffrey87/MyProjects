package SmartHome.demo.service.impl;

import SmartHome.demo.dto.DeviceDto;
import SmartHome.demo.entity.Device;
import SmartHome.demo.entity.Room;
import SmartHome.demo.mapper.DeviceMapper;
import SmartHome.demo.repository.DeviceRepo;
import SmartHome.demo.repository.RoomRepo;
import SmartHome.demo.service.IDeviceService;

import java.util.List;
import java.util.Optional;

public class DeviceService implements IDeviceService {

    private final RoomRepo roomRepo;

    private final DeviceRepo deviceRepo;


    public DeviceService(RoomRepo roomRepo, DeviceRepo deviceRepo) {
        this.roomRepo = roomRepo;
        this.deviceRepo = deviceRepo;
    }


    @Override
    public void createDevice(DeviceDto deviceDto) {
        Optional<Room> roomOptional = roomRepo.findById(deviceDto.getRoomId());
        if (roomOptional.isEmpty()) {
            throw new IllegalArgumentException("Room not found.");
        }
        Device device = new Device();
        device.setDeviceName(deviceDto.getDeviceName());
        device.setDeviceType(deviceDto.getDeviceType());
        deviceRepo.save(device);

    }

    @Override
    public DeviceDto getDeviceById(int deviceId) {
        Optional<Device> deviceOptional = deviceRepo.findById(deviceId);
        if (deviceOptional.isEmpty()) {
            throw new IllegalArgumentException("Device not found.");
        }
        return DeviceMapper.domainToDto(deviceOptional.get(), new DeviceDto());

    }


    @Override
    public List<DeviceDto> getAllDevicesByRoomId(int roomId) {
        Optional<Room> roomOptional = roomRepo.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new IllegalArgumentException("Room not found.");
        }
        List<Device> devices = deviceRepo.findAllByRoomId(roomId);

        return DeviceMapper.domainToDto(devices);


    }
}
