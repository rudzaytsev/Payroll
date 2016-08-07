package domain;


import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CommissionPaid implements PaymentStrategy {

  private static final Integer PAY_DAY = 28;
  private int monthlySalary = 0;

  private int commissionRate = 0;

  public CommissionPaid(int monthlySalary, int commissionRate) {
    this.monthlySalary = monthlySalary;
    this.commissionRate = commissionRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CommissionPaid that = (CommissionPaid) o;

    if (commissionRate != that.commissionRate) return false;
    if (monthlySalary != that.monthlySalary) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = monthlySalary;
    result = 31 * result + commissionRate;
    return result;
  }

  @Override
  public Cheque calculatePay(Employee e, LocalDate paymentDate) {
    boolean isPayDay = PAY_DAY.equals(paymentDate.getDayOfMonth());
    if (!isPayDay)
      new Cheque(0);

    List<SalesReceipt> notPaidSales = e.salesReceipts.stream().
                                      filter(sale -> paymentDate.isAfter(sale.date) && !sale.paid).
                                      collect(toList());

    int totalSalesAmount = notPaidSales.stream().mapToInt(sale -> sale.amount).sum();
    return new Cheque( (int) Math.round(monthlySalary + totalSalesAmount * 0.01 * commissionRate) );

  }
}
