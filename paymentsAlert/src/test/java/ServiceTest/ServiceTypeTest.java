package ServiceTest;

import com.paymentsAlert.paymentsAlert.dto.ServiceTypeInputDto;
import com.paymentsAlert.paymentsAlert.dto.ServiceTypeOutputDto;
import com.paymentsAlert.paymentsAlert.entity.ServiceType;
import com.paymentsAlert.paymentsAlert.repository.ServiceTypeRepo;
import com.paymentsAlert.paymentsAlert.service.impl.ServiceTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceTypeTest {

    @Mock
    private ServiceTypeRepo serviceTypeRepo;

    @InjectMocks
    private ServiceTypeService serviceTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createServiceType_ShouldCreateSuccessfully() {
        ServiceTypeInputDto inputDto = new ServiceTypeInputDto(1L, "Internet");
        ServiceType savedEntity = new ServiceType(1L, "Internet", null);

        when(serviceTypeRepo.existsByName("Internet")).thenReturn(false);
        when(serviceTypeRepo.save(any(ServiceType.class))).thenReturn(savedEntity);

        ServiceTypeOutputDto result = serviceTypeService.createServiceType(inputDto);

        assertNotNull(result);
        assertEquals("Internet", result.getName());
    }

    @Test
    void createServiceType_ShouldThrow_WhenNameExists() {
        ServiceTypeInputDto inputDto = new ServiceTypeInputDto(1L, "Internet");
        when(serviceTypeRepo.existsByName("Internet")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> serviceTypeService.createServiceType(inputDto));
    }

    @Test
    void getServiceTypeById_ShouldReturnDto_WhenFound() {
        ServiceType serviceType = new ServiceType(1L, "Internet", null);
        when(serviceTypeRepo.findById(1L)).thenReturn(Optional.of(serviceType));

        ServiceTypeOutputDto dto = serviceTypeService.getServiceTypeById(1L);

        assertEquals("Internet", dto.getName());
    }

    @Test
    void getServiceTypeById_ShouldThrow_WhenNotFound() {
        when(serviceTypeRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceTypeService.getServiceTypeById(1L));
    }

    @Test
    void updateServiceType_ShouldUpdateSuccessfully() {
        ServiceTypeInputDto inputDto = new ServiceTypeInputDto(1L, "TV");
        ServiceType existing = new ServiceType(1L, "Internet", null);

        when(serviceTypeRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(serviceTypeRepo.save(existing)).thenReturn(existing);

        ServiceTypeOutputDto result = serviceTypeService.updateServiceType(1L, inputDto);

        assertNotNull(result);
        assertEquals("TV", result.getName());
    }

    @Test
    void deleteServiceType_ShouldDelete_WhenExists() {
        ServiceType existing = new ServiceType(1L, "TV", null);
        when(serviceTypeRepo.findById(1L)).thenReturn(Optional.of(existing));

        serviceTypeService.deleteServiceType(1L);

        verify(serviceTypeRepo, times(1)).delete(existing);
    }

    @Test
    void deleteServiceType_ShouldThrow_WhenNotFound() {
        when(serviceTypeRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceTypeService.deleteServiceType(1L));
    }

    @Test
    void existsByName_ShouldReturnTrueOrFalse() {
        when(serviceTypeRepo.existsByName("TV")).thenReturn(true);
        assertTrue(serviceTypeService.existsByName("TV"));

        when(serviceTypeRepo.existsByName("Internet")).thenReturn(false);
        assertFalse(serviceTypeService.existsByName("Internet"));
    }
}

