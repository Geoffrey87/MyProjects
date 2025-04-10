package com.paymentsAlert.paymentsAlert.controller;

import com.paymentsAlert.paymentsAlert.dto.ServiceTypeInputDto;
import com.paymentsAlert.paymentsAlert.dto.ServiceTypeOutputDto;
import com.paymentsAlert.paymentsAlert.service.IServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/service-types")
public class ServiceTypeController {

    private final IServiceType serviceTypeService;

    @Autowired
    public ServiceTypeController(IServiceType serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @PostMapping
    public ResponseEntity<ServiceTypeOutputDto> createServiceType(@Valid @RequestBody ServiceTypeInputDto inputDto) {
        ServiceTypeOutputDto outputDto = serviceTypeService.createServiceType(inputDto);
        return ResponseEntity.ok(outputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTypeOutputDto> getServiceTypeById(@PathVariable Long id) {
        ServiceTypeOutputDto outputDto = serviceTypeService.getServiceTypeById(id);
        return ResponseEntity.ok(outputDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceTypeOutputDto> updateServiceType(
            @PathVariable Long id,
            @Valid @RequestBody ServiceTypeInputDto inputDto) {
        ServiceTypeOutputDto updatedDto = serviceTypeService.updateServiceType(id, inputDto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable Long id) {
        serviceTypeService.deleteServiceType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByName(@RequestParam String name) {
        boolean exists = serviceTypeService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}

