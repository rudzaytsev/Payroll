package cmds;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class DelEmpCmdTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testValidate() throws Exception {
    String commandStr = "DelEmp 111";
    Command command = new DelEmpCmd(commandStr);
    command.validate();
  }

  @Test
  public void incorrectCommandName() {
    String commandStr = "Del 111";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Unknown command");
    command.validate();
  }

  @Test
  public void invalidEmployeeId() {
    String commandStr = "DelEmp ZZZ";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Invalid Employee id");
    command.validate();
  }

  @Test
  public void employeeIdShouldBePositive() throws Exception {
    String commandStr = "DelEmp -1";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee id should be positive");
    command.validate();
  }

  @Test
  public void tooFewArgumentsForCommand() throws Exception {
    String commandStr = "DelEmp ";
    Command command = new DelEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Too few arguments for command DelEmp");
    command.validate();
  }

}