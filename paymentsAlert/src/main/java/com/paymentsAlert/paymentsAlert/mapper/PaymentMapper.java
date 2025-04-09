package com.paymentsAlert.paymentsAlert.mapper;

import com.paymentsAlert.paymentsAlert.dto.PaymentInputDto;
import com.paymentsAlert.paymentsAlert.dto.PaymentOutputDto;
import com.paymentsAlert.paymentsAlert.entity.Payment;
import com.paymentsAlert.paymentsAlert.entity.RecurrencePeriod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public static PaymentOutputDto domainToDto(Payment payment, PaymentOutputDto paymentOutputDto) {
        paymentOutputDto.setAmount(payment.getAmount());
        paymentOutputDto.setDueDate(payment.getDueDate().toString());
        paymentOutputDto.setPaid(payment.isPaid());
        paymentOutputDto.setRecurrencePeriod(payment.getRecurrencePeriod().toString());
        return paymentOutputDto;
    }

    public static Payment dtoToDomain(PaymentInputDto paymentInputDto, Payment payment) {
        payment.setAmount(paymentInputDto.getAmount());
        payment.setDueDate(paymentInputDto.getDueDate());
        payment.setPaid(false); // it's always false when creating a new payment;
        payment.setRecurrencePeriod(RecurrencePeriod.valueOf(paymentInputDto.getRecurrencePeriod()));
        return payment;
    }
}
