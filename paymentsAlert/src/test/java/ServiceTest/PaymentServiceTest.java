package ServiceTest;

import com.paymentsAlert.paymentsAlert.dto.PaymentInputDto;
import com.paymentsAlert.paymentsAlert.dto.PaymentOutputDto;
import com.paymentsAlert.paymentsAlert.entity.Payment;
import com.paymentsAlert.paymentsAlert.entity.RecurrencePeriod;
import com.paymentsAlert.paymentsAlert.entity.User;
import com.paymentsAlert.paymentsAlert.repository.PaymentRepo;
import com.paymentsAlert.paymentsAlert.repository.UserRepo;
import com.paymentsAlert.paymentsAlert.service.impl.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepo paymentRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ServiceTypeRepo serviceTypeRepo;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPayment_shouldReturnPaymentOutputDto() {
        PaymentInputDto inputDto = new PaymentInputDto(1L, 2L, 50.0, LocalDate.of(2025, 4, 15), "MONTHLY");
        User user = new User();
        ServiceType serviceType = new ServiceType();
        Payment saved = new Payment();
        saved.setAmount(50.0);
        saved.setDueDate(LocalDate.of(2025, 4, 15));
        saved.setPaid(false);
        saved.setRecurrencePeriod(com.paymentsAlert.paymentsAlert.entity.RecurrencePeriod.MONTHLY);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(serviceTypeRepo.findById(2L)).thenReturn(Optional.of(serviceType));
        when(paymentRepo.save(any(Payment.class))).thenReturn(saved);

        PaymentOutputDto result = paymentService.createPayment(inputDto);

        assertNotNull(result);
        assertEquals(50.0, result.getAmount());
        assertEquals("2025-04-15", result.getDueDate());
        assertFalse(result.isPaid());
        assertEquals("MONTHLY", result.getRecurrencePeriod());
    }

    @Test
    void getPaymentById_shouldReturnDto() {
        Payment payment = new Payment();
        payment.setAmount(100.0);
        payment.setDueDate(LocalDate.of(2025, 5, 10));
        payment.setPaid(true);
        payment.setRecurrencePeriod(RecurrencePeriod.ANNUALLY);

        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));

        PaymentOutputDto result = paymentService.getPaymentById(1L);

        assertEquals(100.0, result.getAmount());
        assertTrue(result.isPaid());
        assertEquals("ANNUALLY", result.getRecurrencePeriod());
    }

    @Test
    void updatePayment_shouldUpdateFields() {
        Payment existing = new Payment();
        User user = new User();
        ServiceType serviceType = new ServiceType();
        PaymentInputDto input = new PaymentInputDto(1L, 2L, 60.0, LocalDate.of(2025, 6, 1), "QUARTERLY");

        when(paymentRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(serviceTypeRepo.findById(2L)).thenReturn(Optional.of(serviceType));
        when(paymentRepo.save(any(Payment.class))).thenReturn(existing);

        PaymentOutputDto result = paymentService.updatePayment(1L, input);

        assertEquals(60.0, result.getAmount());
        assertEquals("2025-06-01", result.getDueDate());
        assertEquals("QUARTERLY", result.getRecurrencePeriod());
    }

    @Test
    void deletePayment_shouldCallDeleteById() {
        when(paymentRepo.existsById(1L)).thenReturn(true);

        paymentService.deletePayment(1L);

        verify(paymentRepo, times(1)).deleteById(1L);
    }

    @Test
    void getPaymentsByUserId_shouldReturnList() {
        Payment p1 = new Payment();
        p1.setAmount(25);
        p1.setDueDate(LocalDate.now());
        p1.setPaid(false);
        p1.setRecurrencePeriod(com.paymentsAlert.paymentsAlert.entity.RecurrencePeriod.MONTHLY);

        when(paymentRepo.findByUserId(1L)).thenReturn(List.of(p1));

        List<PaymentOutputDto> result = paymentService.getPaymentsByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(25, result.get(0).getAmount());
    }

    @Test
    void markAsPaid_shouldUpdatePaidStatus() {
        Payment payment = new Payment();
        payment.setPaid(false);
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));

        paymentService.markAsPaid(1L);

        assertTrue(payment.isPaid());
        verify(paymentRepo).save(payment);
    }
}

