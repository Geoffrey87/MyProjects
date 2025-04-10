package com.paymentsAlert.paymentsAlert.service.impl;

import com.paymentsAlert.paymentsAlert.dto.PaymentInputDto;
import com.paymentsAlert.paymentsAlert.dto.PaymentOutputDto;
import com.paymentsAlert.paymentsAlert.entity.Payment;
import com.paymentsAlert.paymentsAlert.entity.ServiceType;
import com.paymentsAlert.paymentsAlert.entity.User;
import com.paymentsAlert.paymentsAlert.mapper.PaymentMapper;
import com.paymentsAlert.paymentsAlert.repository.PaymentRepo;
import com.paymentsAlert.paymentsAlert.repository.ServiceTypeRepo;
import com.paymentsAlert.paymentsAlert.repository.UserRepo;
import com.paymentsAlert.paymentsAlert.service.IPayment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService implements IPayment {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;
    private final ServiceTypeRepo serviceTypeRepo;

    public PaymentService(PaymentRepo paymentRepo, UserRepo userRepo, ServiceTypeRepo serviceTypeRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
        this.serviceTypeRepo = serviceTypeRepo;
    }

    @Override
    public PaymentOutputDto createPayment(PaymentInputDto inputDto) {
        User user = userRepo.findById(inputDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceType serviceType = serviceTypeRepo.findById(inputDto.getServiceTypeId())
                .orElseThrow(() -> new RuntimeException("Service Type not found"));

        Payment payment = new Payment();
        PaymentMapper.dtoToDomain(inputDto, payment);
        payment.setUser(user);
        payment.setServiceType(serviceType);

        Payment saved = paymentRepo.save(payment);
        return PaymentMapper.domainToDto(saved, new PaymentOutputDto());
    }

    @Override
    public PaymentOutputDto getPaymentById(Long id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return PaymentMapper.domainToDto(payment, new PaymentOutputDto());
    }

    @Override
    public PaymentOutputDto updatePayment(Long id, PaymentInputDto inputDto) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        User user = userRepo.findById(inputDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceType serviceType = serviceTypeRepo.findById(inputDto.getServiceTypeId())
                .orElseThrow(() -> new RuntimeException("Service Type not found"));

        PaymentMapper.dtoToDomain(inputDto, payment);
        payment.setUser(user);
        payment.setServiceType(serviceType);

        Payment updated = paymentRepo.save(payment);
        return PaymentMapper.domainToDto(updated, new PaymentOutputDto());
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepo.existsById(id)) {
            throw new RuntimeException("Payment not found");
        }
        paymentRepo.deleteById(id);
    }

    @Override
    public List<PaymentOutputDto> getPaymentsByUserId(Long userId) {
        List<Payment> payments = paymentRepo.findByUserId(userId);
        List<PaymentOutputDto> output = new ArrayList<>();
        for (Payment payment : payments) {
            output.add(PaymentMapper.domainToDto(payment, new PaymentOutputDto()));
        }
        return output;
    }

    @Override
    public void markAsPaid(Long id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaid(true);
        paymentRepo.save(payment);
    }
}

