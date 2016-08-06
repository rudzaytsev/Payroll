package domain;


import java.time.LocalDate;

public class SalesReceipt {
  public LocalDate date;
  public Integer amount;

  public SalesReceipt(LocalDate date, Integer amount) {
    this.amount = amount;
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SalesReceipt that = (SalesReceipt) o;

    if (!amount.equals(that.amount)) return false;
    if (!date.equals(that.date)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = date.hashCode();
    result = 31 * result + amount.hashCode();
    return result;
  }
}
