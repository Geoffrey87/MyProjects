package com.paymentsAlert.paymentsAlert.service;

import com.paymentsAlert.paymentsAlert.dto.ServiceTypeInputDto;
import com.paymentsAlert.paymentsAlert.dto.ServiceTypeOutputDto;

import java.util.Optional;

public interface IServiceType {

    ServiceTypeOutputDto createServiceType(ServiceTypeInputDto serviceTypeInputDto);

    ServiceTypeOutputDto getServiceTypeById(Long id);

    ServiceTypeOutputDto updateServiceType(Long id, ServiceTypeInputDto serviceTypeInputDto);

    void deleteServiceType(Long id);

    boolean existsByName(String name);
}

