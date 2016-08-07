package domain;


import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class HourlyPaid implements PaymentStrategy {

  private int hourlyRate = 0;

  public HourlyPaid(int hourlyRate) {
    this.hourlyRate = hourlyRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    HourlyPaid that = (HourlyPaid) o;

    if (hourlyRate != that.hourlyRate) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return hourlyRate;
  }

  @Override
  public Cheque calculatePay(Employee e, LocalDate paymentDate) {

    List<TimeCard> notPaidTimeCards = e.timeCards.stream().
                                       filter(timeCard -> paymentDate.isAfter(timeCard.date) && !timeCard.paid)
                                       .collect(toList());

    int paidHours = notPaidTimeCards.stream().mapToInt( card -> card.hours).sum();
    notPaidTimeCards.stream().forEach(timeCard -> timeCard.paid = true);

    return new Cheque(paidHours * hourlyRate);
  }
}
