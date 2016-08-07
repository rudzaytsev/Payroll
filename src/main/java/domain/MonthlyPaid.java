package domain;


import java.time.LocalDate;

public class MonthlyPaid implements PaymentStrategy {

  public static final Integer PAY_DAY = 28;
  private int monthlySalary = 0;

  public MonthlyPaid(int monthlySalary) {
    this.monthlySalary = monthlySalary;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MonthlyPaid that = (MonthlyPaid) o;

    if (monthlySalary != that.monthlySalary) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return monthlySalary;
  }

  @Override
  public Cheque calculatePay(Employee e, LocalDate paymentDate) {
    boolean isPayDay = PAY_DAY.equals(paymentDate.getDayOfMonth());
    if (isPayDay) {
      return new Cheque(monthlySalary);
    }
    return new Cheque(0);
  }
}
