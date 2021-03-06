package domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee {


  public Integer id;

  public String name;

  public String address;

  public PaymentStrategy paymentStrategy;

  public List<TimeCard> timeCards = new ArrayList<>();
  public List<SalesReceipt> salesReceipts = new ArrayList<>();

  public Employee(Integer id, String name, String address) {
    this.address = address;
    this.id = id;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Employee)) return false;

    Employee employee = (Employee) o;

    return address.equals(employee.address) &&
           id.equals(employee.id) &&
           name.equals(employee.name);

  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + address.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Employee{" +
      "address='" + address + '\'' +
      ", id=" + id +
      ", name='" + name + '\'' +
      ", paymentStrategy='" + paymentStrategy + '\'' +
      '}';
  }

  public boolean isChargedHourly() {
    return paymentStrategy != null && paymentStrategy instanceof HourlyPaid;
  }


  public void addTimeCard(TimeCard timeCard) {
    timeCards.add(timeCard);
  }

  public boolean isChargedCommission() {
    return paymentStrategy != null && paymentStrategy instanceof CommissionPaid;
  }

  public void addSalesReceipt(SalesReceipt salesReceipt) {
    salesReceipts.add(salesReceipt);
  }

  public Cheque calculatePayment(LocalDate paymentDate) {
    return paymentStrategy.calculatePay(this, paymentDate);
  }
}
