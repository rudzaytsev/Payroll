package cmds;

import domain.DB;
import domain.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TimeCardCmdTest {

  @After
  public void tearDown() throws Exception {
    DB.getInstance().clearDB();
  }

  @Before
  public void setUp() throws Exception {
    DB db = DB.getInstance();
    db.clearDB();
    Employee employee = new Employee(27, "Sam Black", "London, England");
    employee.paymentStrategy = "H";
    db.save(employee);

    Employee otherEmployee = new Employee(30, "Boris Johnson", "London, England");
    otherEmployee.paymentStrategy = "C";
    db.save(otherEmployee);
  }

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testValidate() throws Exception {
    String commandStr = "TimeCard 22 01.02.2016 8";
    Command command = new TimeCardCmd(commandStr);
    command.validate();
  }

  @Test
  public void incorrectCommandName() throws Exception {
    String commandStr = "TimeCardZ 22 01.02.2016 8";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("unknown command");
    command.validate();

  }

  @Test
  public void incorrectNumberOfArguments() {
    String commandStr = "TimeCard 22 01.02.2016";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("TimeCard command required 3 arguments");
    command.validate();
  }

  @Test
  public void employeeIdShouldHaveIntegerTypeValue() throws Exception {
    String commandStr = "TimeCard CV 01.02.2016 8";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should have Integer type value");
    command.validate();
  }

  @Test
  public void employeeIdShouldBePositive() throws Exception {
    String commandStr = "TimeCard -1 01.02.2016 8";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should be positive");
    command.validate();
  }

  @Test
  public void invalidDateFormat() throws Exception {
    String commandStr = "TimeCard 25 01/02/2016 8";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Invalid date format, should be dd.MM.yyyy");
    command.validate();
  }

  @Test
  public void invalidNumberOfWorkingTimeHours() throws Exception {
    String commandStr = "TimeCard 25 01.02.2016 TEN";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Number of working time hours should have Integer type value");
    command.validate();
  }

  @Test
  public void numberOfWorkingTimeHoursShouldBePositive() throws Exception {
    String commandStr = "TimeCard 25 01.02.2016 -2";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Number of working time hours should be positive");
    command.validate();
  }

  @Test
  public void employeeForTimeCardNotFound() throws Exception {
    String commandStr = "TimeCard 22 01.02.2016 8";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(CommandExecutionException.class);
    expectedException.expectMessage("Employee for time card not found");
    command.validate();
    command.execute();
  }

  @Test
  public void employeeLinkedToTimeCardIsNotChargedHourly() throws Exception {
    String commandStr = "TimeCard 30 01.02.2016 8";
    Command command = new TimeCardCmd(commandStr);
    expectedException.expect(CommandExecutionException.class);
    expectedException.expectMessage("Employee linked to time card is not charged hourly");
    command.validate();
    command.execute();
  }

  @Test
  public void successExecuteTest() throws Exception {
    String commandStr = "TimeCard 27 01.02.2016 8";
    Command command = new TimeCardCmd(commandStr);
    command.validate();
    command.execute();
  }
}