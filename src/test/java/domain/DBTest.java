package domain;


import static java.time.Month.*;
import static org.junit.Assert.*;
import static utils.DateUtils.date;

import cmds.TimeCard;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;


public class DBTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @After
  public void tearDown() throws Exception {
    DB.getInstance().clearDB();
  }

  @Test
  public void saveEmployeeTest() throws Exception {
    DB db = DB.getInstance();
    Employee employee = new Employee(1, "David Cameron", "Downing Street 5, London, England");
    db.save(employee);
    Employee storedEmployee = db.findBy(employee.id);
    assertEquals(employee, storedEmployee);
  }

  @Test
  public void deleteEmployeeTest() throws Exception {
    DB db = DB.getInstance();
    Employee employee = new Employee(25, "Donald Trump", "USA");
    db.save(employee);
    db.delete(25);
    Employee deletedEmployee = db.findBy(25);
    assertNull(deletedEmployee);
  }

  @Test
  public void employeeNotDeleted() throws Exception {
    DB db = DB.getInstance();
    Employee employee = new Employee(25, "Donald Trump", "USA");
    db.save(employee);
    expectedException.expect(DataBaseException.class);
    expectedException.expectMessage("No Entity with id = 111 to delete");
    db.delete(111);
  }

  @Test
  public void saveTimeCard() throws Exception {
    DB db = DB.getInstance();
    TimeCard timeCard = new TimeCard(date(2016, JANUARY, 22), 10);
    Employee employee = new Employee(35, "Jack Callback", "NYC, USA");
    employee.paymentStrategy = "H";
    employee.addTimeCard(timeCard);
    db.save(employee);
    Employee foundEmployee = db.findBy(35);
    assertNotNull(foundEmployee);
    assertEquals(1, foundEmployee.timeCards.size());
    assertEquals(new TimeCard(date(2016, JANUARY, 22), 10), foundEmployee.timeCards.get(0));
  }
}
