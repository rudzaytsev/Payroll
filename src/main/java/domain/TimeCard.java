package domain;

import java.time.LocalDate;


public class TimeCard {

  LocalDate date;
  Integer hours;

  public TimeCard(LocalDate date, Integer hours) {
    this.date = date;
    this.hours = hours;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TimeCard timeCard = (TimeCard) o;

    if (!date.equals(timeCard.date)) return false;
    return hours.equals(timeCard.hours);

  }

  @Override
  public int hashCode() {
    int result = date.hashCode();
    result = 31 * result + hours.hashCode();
    return result;
  }
}
