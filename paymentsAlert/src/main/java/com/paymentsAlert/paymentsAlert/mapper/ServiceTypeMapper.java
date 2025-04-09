package com.paymentsAlert.paymentsAlert.mapper;

import com.paymentsAlert.paymentsAlert.dto.ServiceTypeInputDto;
import com.paymentsAlert.paymentsAlert.dto.ServiceTypeOutputDto;
import com.paymentsAlert.paymentsAlert.entity.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeMapper {

    public static ServiceTypeOutputDto domainToDto(ServiceType serviceType, ServiceTypeOutputDto serviceTypeOutputDto) {
        serviceTypeOutputDto.setName(serviceType.getName());
        return serviceTypeOutputDto;
    }

    public static ServiceType dtoToDomain(ServiceTypeInputDto serviceTypeInputDto, ServiceType serviceType) {
        serviceType.setName(serviceTypeInputDto.getName());
        return serviceType;
    }
}
