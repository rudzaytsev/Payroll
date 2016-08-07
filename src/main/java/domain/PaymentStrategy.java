package domain;


import java.time.LocalDate;

public interface PaymentStrategy {
  Cheque calculatePay(Employee e, LocalDate paymentDate);
}
