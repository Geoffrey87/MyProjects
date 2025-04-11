package com.paymentsAlert.paymentsAlert.service;

import com.paymentsAlert.paymentsAlert.dto.PaymentInputDto;
import com.paymentsAlert.paymentsAlert.dto.PaymentOutputDto;

import java.util.List;

public interface IPayment {


    PaymentOutputDto createPayment(PaymentInputDto paymentInputDto);

    PaymentOutputDto getPaymentById(Long id);

    PaymentOutputDto updatePayment(Long id, PaymentInputDto paymentInputDto);

    void deletePayment(Long id);

    List<PaymentOutputDto> getPaymentsByUserId(Long userId);

    void markAsPaid(Long id);
    double getMonthlyTotalAmount(Long userId, int year, int month);

    double getYearlyTotalAmount(Long userId, int year);
}

