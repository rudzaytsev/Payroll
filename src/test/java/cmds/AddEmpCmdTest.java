package cmds;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AddEmpCmdTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void testValidate() throws Exception {
    String commandStr = "AddEmp 1 \"David Cameron\" \"Downing Street 5, London, England\" H 1000";
    Command command = new AddEmpCmd(commandStr);
    command.validate();
  }

  @Test
  public void incorrectCommandName() {
    String commandStr = "Xyz 1 \"David Cameron\" \"Downing Street 5, London, England\" H 1000";
    Command command = new AddEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Unknown command");
    command.validate();
  }

  @Test
  public void incorrectEmployeeId() {
    String commandStr = "AddEmp -1 \"David Cameron\" \"Downing Street 5, London, England\" H 1000";
    Command command = new AddEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee Id should be positive");
    command.validate();
  }

  @Test
  public void employeeIdEqualsZero() {
    String commandStr = "AddEmp 0 \"David Cameron\" \"Downing Street 5, London, England\" H 1000";
    Command command = new AddEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee Id should be positive");
    command.validate();
  }



  @Test
  public void employeeNameWithoutQuotes() {
    String commandStr = "AddEmp 1 David Cameron \"Downing Street 5; London; England\" H 1000";
    Command command = new AddEmpCmd(commandStr);
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee name should be in quotes");
    command.validate();
  }

  @Test
  public void employeeAddressWithoutQuotes() {
    String commandStr = "AddEmp 1 \"David Cameron\" Downing Street 5; London; England H 1000";
    Command command = new AddEmpCmd(commandStr);
    System.out.println(Arrays.toString(((AbstractCommand) command).args.toArray()));
    expectedException.expect(ValidationException.class);
    expectedException.expectMessage("Employee address should be in quotes");
    command.validate();
  }

  /*
  @Test
  public void testExecute() throws Exception {

  }
  */
}