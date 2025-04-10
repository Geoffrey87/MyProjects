package com.paymentsAlert.paymentsAlert.service.impl;

import com.paymentsAlert.paymentsAlert.dto.ServiceTypeInputDto;
import com.paymentsAlert.paymentsAlert.dto.ServiceTypeOutputDto;
import com.paymentsAlert.paymentsAlert.entity.ServiceType;
import com.paymentsAlert.paymentsAlert.mapper.ServiceTypeMapper;
import com.paymentsAlert.paymentsAlert.repository.ServiceTypeRepo;
import com.paymentsAlert.paymentsAlert.service.IServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService implements IServiceType {

    private final ServiceTypeRepo serviceTypeRepo;

    @Autowired
    public ServiceTypeService(ServiceTypeRepo serviceTypeRepo) {
        this.serviceTypeRepo = serviceTypeRepo;
    }

    @Override
    public ServiceTypeOutputDto createServiceType(ServiceTypeInputDto serviceTypeInputDto) {
        // Check if a ServiceType with the same name already exists
        if (serviceTypeRepo.existsByName(serviceTypeInputDto.getName())) {
            throw new RuntimeException("ServiceType with this name already exists.");
        }

        // Map the input DTO to the domain entity
        ServiceType serviceType = ServiceTypeMapper.dtoToDomain(serviceTypeInputDto, new ServiceType());

        // Save the new ServiceType in the repository
        ServiceType savedServiceType = serviceTypeRepo.save(serviceType);

        // Map the saved entity back to the output DTO
        return ServiceTypeMapper.domainToDto(savedServiceType, new ServiceTypeOutputDto());
    }

    @Override
    public ServiceTypeOutputDto getServiceTypeById(Long id) {
        // Retrieve the ServiceType by ID
        ServiceType serviceType = serviceTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceType not found with id: " + id));

        // Map the entity to the DTO
        return ServiceTypeMapper.domainToDto(serviceType, new ServiceTypeOutputDto());
    }

    @Override
    public ServiceTypeOutputDto updateServiceType(Long id, ServiceTypeInputDto serviceTypeInputDto) {
        // Retrieve the existing ServiceType by ID
        ServiceType serviceType = serviceTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceType not found with id: " + id));

        // Map the updated fields from DTO to the existing entity
        ServiceTypeMapper.dtoToDomain(serviceTypeInputDto, serviceType);

        // Save the updated ServiceType
        ServiceType updatedServiceType = serviceTypeRepo.save(serviceType);

        // Map the updated entity to the output DTO
        return ServiceTypeMapper.domainToDto(updatedServiceType, new ServiceTypeOutputDto());
    }

    @Override
    public void deleteServiceType(Long id) {
        // Retrieve the ServiceType by ID and check if it exists
        ServiceType serviceType = serviceTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceType not found with id: " + id));

        // Delete the ServiceType
        serviceTypeRepo.delete(serviceType);
    }

    @Override
    public boolean existsByName(String name) {
        // Check if a ServiceType with the given name exists
        return serviceTypeRepo.existsByName(name);
    }
}

