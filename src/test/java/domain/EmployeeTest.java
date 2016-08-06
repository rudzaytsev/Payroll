package domain;

import org.junit.Test;

import static java.time.Month.JANUARY;
import static java.time.Month.MAY;
import static org.junit.Assert.*;
import static utils.DateUtils.date;


public class EmployeeTest {

  Employee employee = new Employee(33, "Sam Bolton", "NYC, USA");

  @Test
  public void isChargedHourlyTest() throws Exception {
    employee.paymentStrategy = "H:500";
    assertTrue(employee.isChargedHourly());
  }


  @Test
  public void isNotChargedHourly() throws Exception {
    employee.paymentStrategy = "S:900";
    assertFalse(employee.isChargedHourly());
    employee.paymentStrategy = "C:200:80";
    assertFalse(employee.isChargedHourly());
    employee.paymentStrategy = null;
    assertFalse(employee.isChargedHourly());
  }

  @Test
  public void testAddTimeCard() throws Exception {
    employee.addTimeCard(new TimeCard(date(2016, MAY, 10),8));
    assertEquals(1, employee.timeCards.size());
  }

  @Test
  public void isChargedCommissionTest() throws Exception {
    employee.paymentStrategy = "C:500:20";
    assertTrue(employee.isChargedCommission());
  }

  @Test
  public void isNotChargedCommission() throws Exception {
    employee.paymentStrategy = "S:900";
    assertFalse(employee.isChargedCommission());
    employee.paymentStrategy = "H:1900";
    assertFalse(employee.isChargedCommission());
    employee.paymentStrategy = null;
    assertFalse(employee.isChargedCommission());
  }

  @Test
  public void addSalesReceiptTest() throws Exception {
    employee.addSalesReceipt(new SalesReceipt(date(1999, JANUARY, 7), 88));
    assertEquals(1, employee.salesReceipts.size());
  }
}