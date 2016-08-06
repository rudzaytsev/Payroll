package cmds;

import domain.DB;
import domain.Employee;
import domain.SalesReceipt;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utils.DateUtils;



import static java.time.Month.*;
import static org.junit.Assert.*;

public class SalesReceiptCmdTest {

  @After
  public void tearDown() throws Exception {
    DB.getInstance().clearDB();
  }

  @Before
  public void setUp() throws Exception {
    DB db = DB.getInstance();
    db.clearDB();
    Employee employee = new Employee(22, "Sam Black", "London, England");
    employee.paymentStrategy = "H:700";
    db.save(employee);

    Employee otherEmployee = new Employee(26, "Boris Johnson", "London, England");
    otherEmployee.paymentStrategy = "C:100:20";
    db.save(otherEmployee);
  }

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void incorrectCommandName() throws Exception {
    String commandStr = "XSalesReceipt 22 02.04.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Unknown command");
    command.validate();
  }

  @Test
  public void employeeIdShouldHaveIntegerType() throws Exception {
    String commandStr = "SalesReceipt 2V2 02.04.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should have Integer type");
    command.validate();
  }

  @Test
  public void employeeIdShouldBePositive() throws Exception {
    String commandStr = "SalesReceipt -22 02.04.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should be positive");
    command.validate();
  }

  @Test
  public void invalidDate() throws Exception {
    String commandStr = "SalesReceipt 22 31.02.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Invalid date");
    command.validate();
  }

  @Test
  public void validDate() throws Exception {
    String commandStr = "SalesReceipt 22 04.02.2016 30";
    Command command = new SalesReceiptCmd(commandStr);
    command.validate();
  }

  @Test
  public void amountShouldNotBeNegative() throws Exception {
    String commandStr = "SalesReceipt 22 22.02.2016 -8";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Sales Receipt amount should not be negative");
    command.validate();
  }

  @Test
  public void amountShouldHaveIntegerType() throws Exception {
    String commandStr = "SalesReceipt 22 22.02.2016 -V8";
    Command command = new SalesReceiptCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Sales Receipt amount should have Integer type");
    command.validate();
  }


  @Test
  public void testExecute() throws Exception {
    String commandStr = "SalesReceipt 22 22.02.2016 100";
    Command command = new SalesReceiptCmd(commandStr);
    command.validate();
    expectedException.expect(CommandExecutionException.class);
    expectedException.expectMessage("Employee with id = 22 is not charged commission");
    command.execute();
  }


  @Test
  public void salesReceiptForNotExistedEmployee() throws Exception {
    String commandStr = "SalesReceipt 35 22.02.2016 100";
    Command command = new SalesReceiptCmd(commandStr);
    command.validate();
    expectedException.expect(CommandExecutionException.class);
    expectedException.expectMessage("Employee with id = 35 doesn't exist");
    command.execute();
  }

  @Test
  public void salesReceiptWellDone() throws Exception {
    String commandStr = "SalesReceipt 26 22.02.2016 100";
    Command command = new SalesReceiptCmd(commandStr);
    command.validate();
    command.execute();
    DB db = DB.getInstance();
    Employee employee = db.findBy(26);
    SalesReceipt salesReceipt = new SalesReceipt(DateUtils.date(2016, FEBRUARY, 22), 100);
    assertFalse(employee.salesReceipts.isEmpty());
    assertEquals(salesReceipt, employee.salesReceipts.get(0));
  }
}