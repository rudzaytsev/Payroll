package cmds;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class TimeCardCmdTest {

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
  public void testExecute() throws Exception {

  }
}