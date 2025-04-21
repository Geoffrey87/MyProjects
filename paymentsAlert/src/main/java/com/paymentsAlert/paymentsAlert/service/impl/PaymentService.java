package com.paymentsAlert.paymentsAlert.service.impl;

import com.paymentsAlert.paymentsAlert.dto.PaymentInputDto;
import com.paymentsAlert.paymentsAlert.dto.PaymentOutputDto;
import com.paymentsAlert.paymentsAlert.entity.Payment;
import com.paymentsAlert.paymentsAlert.entity.CustomUser;
import com.paymentsAlert.paymentsAlert.mapper.PaymentMapper;
import com.paymentsAlert.paymentsAlert.repository.PaymentRepo;
import com.paymentsAlert.paymentsAlert.repository.UserRepo;
import com.paymentsAlert.paymentsAlert.service.IPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for managing payments.
 * Handles creation, retrieval, updating, deletion, and filtering of payments.
 */
@Service
public class PaymentService implements IPayment {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;

    @Autowired
    public PaymentService(PaymentRepo paymentRepo, UserRepo userRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
    }

    /**
     * Creates one or more payments based on the recurrence specified.
     *
     * @param inputDto The payment input data including amount, due date, user ID, and recurrence.
     * @return The output DTO of the first created payment.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public PaymentOutputDto createPayment(PaymentInputDto inputDto) {
        CustomUser user = userRepo.findById(inputDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Payment> paymentsToSave = new ArrayList<>();

        LocalDate dueDate = inputDto.getDueDate();
        int occurrences = switch (inputDto.getRecurrencePeriod()) {
            case "MONTHLY" -> 12;
            case "QUARTERLY" -> 4;
            case "YEARLY" -> 1;
            default -> 1; // NONE
        };

        int monthsToAdd = switch (inputDto.getRecurrencePeriod()) {
            case "MONTHLY" -> 1;
            case "QUARTERLY" -> 3;
            case "YEARLY" -> 12;
            default -> 0;
        };

        for (int i = 0; i < occurrences; i++) {
            Payment payment = new Payment();
            PaymentMapper.dtoToDomain(inputDto, payment);
            payment.setUser(user);
            payment.setDueDate(dueDate.plusMonths((long) i * monthsToAdd));
            paymentsToSave.add(payment);
        }

        List<Payment> savedPayments = paymentRepo.saveAll(paymentsToSave);


        return PaymentMapper.domainToDto(savedPayments.getFirst(), new PaymentOutputDto());
    }

    /**
     * Retrieves a payment by its ID.
     *
     * @param id The ID of the payment.
     * @return The corresponding payment as an output DTO.
     * @throws RuntimeException if the payment is not found.
     */
    @Override
    public PaymentOutputDto getPaymentById(Long id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return PaymentMapper.domainToDto(payment, new PaymentOutputDto());
    }

    /**
     * Updates an existing payment with new information.
     *
     * @param id       The ID of the payment to update.
     * @param inputDto The new data for the payment.
     * @return The updated payment as an output DTO.
     * @throws RuntimeException if the payment or user is not found.
     */
    @Override
    public PaymentOutputDto updatePayment(Long id, PaymentInputDto inputDto) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        CustomUser user = userRepo.findById(inputDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        PaymentMapper.dtoToDomain(inputDto, payment);
        payment.setUser(user);

        Payment updated = paymentRepo.save(payment);
        return PaymentMapper.domainToDto(updated, new PaymentOutputDto());
    }

    /**
     * Deletes a payment by its ID.
     *
     * @param id The ID of the payment.
     * @throws RuntimeException if the payment is not found.
     */
    @Override
    public void deletePayment(Long id) {
        if (!paymentRepo.existsById(id)) {
            throw new RuntimeException("Payment not found");
        }
        paymentRepo.deleteById(id);
    }

    /**
     * Retrieves all payments for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of payment output DTOs.
     */
    @Override
    public List<PaymentOutputDto> getPaymentsByUserId(Long userId) {
        List<Payment> payments = paymentRepo.findByUserId(userId);
        List<PaymentOutputDto> output = new ArrayList<>();
        for (Payment payment : payments) {
            output.add(PaymentMapper.domainToDto(payment, new PaymentOutputDto()));
        }
        return output;
    }
    /**
     * Updates the paid status of a payment.
     *
     * @param id   The ID of the payment.
     * @param paid The new paid status.
     * @throws RuntimeException if the payment is not found.
     */
    @Override
    public void setPaidStatus(Long id, boolean paid) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaid(paid);
        paymentRepo.save(payment);
    }

    /**
     * Calculates the total amount of payments for a given user in a specific month.
     *
     * @param userId The ID of the user.
     * @param year   The year to filter.
     * @param month  The month to filter.
     * @return The total payment amount for that month.
     */
    @Override
    public double getMonthlyTotalAmount(Long userId, int year, int month) {
        List<Payment> payments = paymentRepo.findByUserId(userId);

        double total = 0.0;

        for (Payment payment : payments) {
            LocalDate paymentDate = payment.getDueDate();

            if (paymentDate.getYear() == year && paymentDate.getMonthValue() == month) {
                total += payment.getAmount();
            }
        }

        return total;
    }

    /**
     * Calculates the total amount of payments for a given user in a specific year.
     *
     * @param userId The ID of the user.
     * @param year   The year to filter.
     * @return The total payment amount for that year.
     */
    @Override
    public double getYearlyTotalAmount(Long userId, int year) {
        List<Payment> payments = paymentRepo.findByUserId(userId);

        double total = 0.0;

        for (Payment payment : payments) {
            LocalDate paymentDate = payment.getDueDate();

            if (paymentDate.getYear() == year) {
                total += payment.getAmount();
            }
        }

        return total;
    }

    /**
     * Retrieves all payments for a user on a specific date.
     *
     * @param userId The ID of the user.
     * @param date   The date to filter by.
     * @return A list of payment output DTOs matching the date.
     */
    @Override
    public List<PaymentOutputDto> getPaymentsByUserAndDate(Long userId, LocalDate date) {
        List<Payment> payments = paymentRepo.findByUserIdAndDueDate(userId, date);
        List<PaymentOutputDto> result = new ArrayList<>();

        for (Payment payment : payments) {
            PaymentOutputDto dto = new PaymentOutputDto();
            PaymentMapper.domainToDto(payment, dto);
            result.add(dto);
        }

        return result;
    }




}

