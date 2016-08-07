package cmds;

import domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Month;
import java.util.List;

import static java.time.Month.*;
import static org.junit.Assert.*;
import static utils.DateUtils.date;

public class PaydayCmdTest {

  @After
  public void tearDown() throws Exception {
    DB db = DB.getInstance();
    db.clearDB();
  }

  @Before
  public void setUp() throws Exception {
    DB db = DB.getInstance();
    db.clearDB();
    Employee employee = new Employee(1, "Alan", "London, England");
    employee.paymentStrategy = new HourlyPaid(800);
    employee.addTimeCard(new TimeCard(date(2016, MARCH, 1), 8));
    employee.addTimeCard(new TimeCard(date(2016, MARCH, 2), 10));
    db.save(employee);

    Employee otherEmployee = new Employee(2, "Sam", "London, England");
    otherEmployee.paymentStrategy = new MonthlyPaid(1000);
    db.save(otherEmployee);

    Employee anotherEmployee = new Employee(3, "Bob", "NYC, USA");
    anotherEmployee.paymentStrategy = new CommissionPaid(500, 20);
    anotherEmployee.addSalesReceipt(new SalesReceipt(date(2016, MARCH, 2),2000));
    anotherEmployee.addSalesReceipt(new SalesReceipt(date(2016, MARCH, 4),5000));
    db.save(anotherEmployee);
  }

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void invalidCommandName() throws Exception {
    String commandStr = "PayDay 20.02.2016";
    Command command = new PaydayCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Incorrect command name");
    command.validate();

  }

  @Test
  public void invalidDate() throws Exception {
    String commandStr = "Payday 30.02.2016";
    Command command = new PaydayCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Invalid date");
    command.validate();
  }


  @Test
  public void testExecute() throws Exception {
    String commandStr = "Payday 28.03.2016";
    PaydayCmd command = new PaydayCmd(commandStr);
    command.validate();
    command.execute();
    List<Cheque> chequeList = command.getResults();

    assertEquals(3, chequeList.size());
    assertEquals(14400, chequeList.get(0).total);
    assertEquals(1000, chequeList.get(1).total);
    assertEquals(1900, chequeList.get(2).total);
  }

}